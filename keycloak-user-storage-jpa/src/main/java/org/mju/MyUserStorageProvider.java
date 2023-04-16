package org.mju;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputUpdater;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.credential.CredentialModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.cache.CachedUserModel;
import org.keycloak.models.cache.OnUserCache;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;
import org.keycloak.storage.user.UserRegistrationProvider;
import org.mju.domain.UserAdapter;
import org.mju.domain.UserEntity;

public class MyUserStorageProvider implements UserStorageProvider,
        UserLookupProvider,
        UserRegistrationProvider,
        UserQueryProvider,
        CredentialInputUpdater,
        CredentialInputValidator,
        OnUserCache {
    private static final Logger logger = Logger.getLogger(MyUserStorageProvider.class);
    public static final String PASSWORD_CACHE_KEY = UserAdapter.class.getName() + ".password";

    protected EntityManager em;
    protected ComponentModel model;
    protected KeycloakSession session;

    MyUserStorageProvider(final KeycloakSession session, final ComponentModel model) {
        this.session = session;
        this.model = model;
        em = session.getProvider(JpaConnectionProvider.class, "user-store").getEntityManager();
    }

    @Override
    public void close() {
        logger.info("close");
    }

    @Override
    public UserModel getUserById(final RealmModel realmModel, final String id) {
        logger.info("getUserById: " + id);
        String persistenceId = StorageId.externalId(id);
        UserEntity entity = em.find(UserEntity.class, persistenceId);
        if (entity == null) {
            logger.info("could not find user by id: " + id);
            return null;
        }
        return new UserAdapter(session, realmModel, model, entity);
    }

    @Override
    public UserModel getUserByUsername(final RealmModel realmModel, final String username) {
        logger.info("getUserByUsername: " + username);
        TypedQuery<UserEntity> query = em.createNamedQuery("getUserByUsername", UserEntity.class);
        query.setParameter("username", username);
        List<UserEntity> result = query.getResultList();
        if (result.isEmpty()) {
            logger.info("could not find username: " + username);
            return null;
        }
        return new UserAdapter(session, realmModel, model, result.get(0));
    }

    @Override
    public UserModel getUserByEmail(final RealmModel realmModel, final String email) {
        logger.info("getUserByEmail: " + email);
        TypedQuery<UserEntity> query = em.createNamedQuery("getUserByEmail", UserEntity.class);
        query.setParameter("email", email);
        List<UserEntity> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return new UserAdapter(session, realmModel, model, result.get(0));
    }

    @Override
    public UserModel addUser(final RealmModel realm, final String username) {
        try {
            Constructor<UserEntity> declaredConstructor = UserEntity.class.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            UserEntity entity = declaredConstructor.newInstance();
            setFieldBy(entity, "username", username);
            em.persist(entity);
            logger.info("added user: " + username);
            declaredConstructor.setAccessible(false);
            return new UserAdapter(session, realm, model, entity);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setFieldBy(final UserEntity entity, final String name, final String value) {
        try {
            logger.info("setFieldBy name: " + name + " value: " + value);
            Field field = entity.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(entity, value);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean removeUser(final RealmModel realm, final UserModel user) {
        logger.info("removeUser: " + user.getId());
        String persistenceId = StorageId.externalId(user.getId());
        UserEntity entity = em.find(UserEntity.class, persistenceId);
        if (entity == null) {
            return false;
        }
        em.remove(entity);
        return true;
    }

    @Override
    public void onCache(final RealmModel realm, final CachedUserModel user, final UserModel delegate) {
        String password = ((UserAdapter) delegate).getPassword();
        if (password != null) {
            user.getCachedWith().put(PASSWORD_CACHE_KEY, password);
        }
    }

    @Override
    public boolean supportsCredentialType(final String credentialType) {
        return CredentialModel.PASSWORD.equals(credentialType);
    }

    @Override
    public boolean updateCredential(final RealmModel realm, final UserModel user, final CredentialInput input) {
        if (!supportsCredentialType(input.getType()) || !(input instanceof UserCredentialModel)) {
            return false;
        }
        UserCredentialModel cred = (UserCredentialModel) input;
        UserAdapter adapter = getUserAdapter(user);
        adapter.setPassword(cred.getValue());
        return true;
    }

    public UserAdapter getUserAdapter(final UserModel user) {
        UserAdapter adapter = null;
        if (user instanceof CachedUserModel) {
            adapter = (UserAdapter) ((CachedUserModel) user).getDelegateForUpdate();
        } else {
            adapter = (UserAdapter) user;
        }
        return adapter;
    }

    @Override
    public void disableCredentialType(final RealmModel realm, final UserModel user, final String credentialType) {
        if (!supportsCredentialType(credentialType)) {
            return;
        }
        getUserAdapter(user).setPassword(null);
    }

    @Override
    public Stream<String> getDisableableCredentialTypesStream(final RealmModel realm, final UserModel user) {
        if (getUserAdapter(user).getPassword() != null) {
            Stream<String> set = Stream.of(CredentialModel.PASSWORD);
            return set;
        } else {
            return Stream.of();
        }
    }

    @Override
    public boolean isConfiguredFor(final RealmModel realm, final UserModel user, final String credentialType) {
        return supportsCredentialType(credentialType) && getPassword(user) != null;
    }

    @Override
    public boolean isValid(final RealmModel realm, final UserModel user, final CredentialInput input) {
        if (!supportsCredentialType(input.getType()) || !(input instanceof UserCredentialModel)) {
            return false;
        }
        UserCredentialModel cred = (UserCredentialModel) input;
        String password = getPassword(user);
        return password != null && password.equals(cred.getValue());
    }

    public String getPassword(final UserModel user) {
        String password = null;
        if (user instanceof CachedUserModel) {
            password = (String) ((CachedUserModel) user).getCachedWith().get(PASSWORD_CACHE_KEY);
        } else if (user instanceof UserAdapter) {
            password = ((UserAdapter) user).getPassword();
        }
        return password;
    }

    @Override
    public int getUsersCount(final RealmModel realm) {
        return ((Number) em.createNamedQuery("getUserCount").getSingleResult()).intValue();
    }

    @Override
    public int getUsersCount(final RealmModel realm, final Set<String> groupIds) {
        return UserQueryProvider.super.getUsersCount(realm, groupIds);
    }

    @Override
    public Stream<UserModel> searchForUserStream(
            final RealmModel realmModel,
            final String search,
            final Integer firstResult,
            final Integer maxResults
    ) {
        TypedQuery<UserEntity> query = em.createNamedQuery("searchForUser", UserEntity.class);
        query.setParameter("search", "%" + search.toLowerCase() + "%");
        if (firstResult != -1) {
            query.setFirstResult(firstResult);
        }
        if (maxResults != -1) {
            query.setMaxResults(maxResults);
        }
        List<UserEntity> results = query.getResultList();
        List<UserModel> users = new LinkedList<>();
        for (UserEntity entity : results) {
            users.add(new UserAdapter(session, realmModel, model, entity));
        }
        return users.stream();
    }

    @Override
    public Stream<UserModel> searchForUserStream(
            final RealmModel realmModel,
            final Map<String, String> map,
            final Integer firstResult,
            final Integer maxResults
    ) {
        return searchForUserStream(realmModel, "", firstResult, maxResults);
    }

    @Override
    public Stream<UserModel> getGroupMembersStream(
            final RealmModel realmModel,
            final GroupModel groupModel,
            final Integer integer,
            final Integer integer1
    ) {
        return Stream.empty();
    }

    @Override
    public Stream<UserModel> searchForUserByUserAttributeStream(
            final RealmModel realmModel,
            final String s,
            final String s1
    ) {
        return Stream.empty();
    }
}
