/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mju.domain;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jboss.logging.Logger;
import org.keycloak.common.util.MultivaluedHashMap;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;

public class UserAdapter extends AbstractUserAdapterFederatedStorage {
    private static final Logger logger = Logger.getLogger(UserAdapter.class);
    private static final String ENABLED_FIELD_NAME = "ENABLED";
    private static final String EMAIL_VERIFIED_FIELD_NAME = "EMAIL_VERIFIED";
    private static final String PASSWORD_FIELD_NAME = "password";
    private static final String USERNAME_FIELD_NAME = "username";

    protected UserEntity entity;
    protected String keycloakId;

    public UserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, UserEntity entity) {
        super(session, realm, model);
        this.entity = entity;
        keycloakId = StorageId.keycloakId(model, entity.getId());
    }

    public String getPassword() {
        return entity.getPassword();
    }

    public void setPassword(String password) {
        setFieldValueByName(PASSWORD_FIELD_NAME, password);
    }

    @Override
    public String getUsername() {
        return entity.getUsername();
    }

    @Override
    public void setUsername(String username) {
        setFieldValueByName(USERNAME_FIELD_NAME, username);
    }

    @Override
    public String getId() {
        return keycloakId;
    }

    @Override
    public void setSingleAttribute(String name, String value) {
        setFieldValueByName(name, value);
    }

    @Override
    public void setAttribute(final String name, final List<String> values) {
        super.setAttribute(name, values);
    }

    private void setFieldValueByName(final String name, final String value) {
        try {
            if (isKeycloakBasicInformationField(name)) {
                Field field = entity.getClass().getDeclaredField(name);
                setFieldBy(field, value);
                super.setSingleAttribute(name, value);
            } else if (isAdditionalInformationField(name)) {
                Field field = entity.getAdditionalInformation().getClass().getDeclaredField(name);
                setFieldBy(field, value);
            } else {
                if (name.equals(ENABLED_FIELD_NAME) || name.equals(EMAIL_VERIFIED_FIELD_NAME)) {
                    super.setSingleAttribute(name, value);
                } else {
                    logger.error("Set field Error name : " + name + " value : " + value);
                }
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isKeycloakBasicInformationField(final String name) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .anyMatch(f -> f.getName().equals(name));
    }

    private boolean isAdditionalInformationField(final String name) {
        return Arrays.stream(entity.getAdditionalInformation().getClass().getDeclaredFields())
                .anyMatch(f -> f.getName().equals(name));
    }

    private void setFieldBy(final Field field, final String value) {
        try {
            field.setAccessible(true);
            field.set(entity, value);
            field.setAccessible(false);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeAttribute(String name) {
        setFieldValueByName(name, null);
    }

    @Override
    public Map<String, List<String>> getAttributes() {
        Map<String, List<String>> attrs = super.getAttributes();
        MultivaluedHashMap<String, String> all = new MultivaluedHashMap<>();
        all.putAll(attrs);
        AdditionalInformation additionalInformation = entity.getAdditionalInformation();
        for (Field field : additionalInformation.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                String key = field.getName();
                String value = Optional.ofNullable(field.get(additionalInformation)).orElse("").toString();
                all.put(key, Collections.singletonList(value));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            field.setAccessible(false);
        }
        return all;
    }
}
