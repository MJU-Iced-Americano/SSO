package org.mju.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.hibernate.annotations.GenericGenerator;

@NamedQueries({
        @NamedQuery(name = "getUserByUsername", query = "select u from UserEntity u where u.username = :username"),
        @NamedQuery(name = "getUserByEmail", query = "select u from UserEntity u where u.email = :email"),
        @NamedQuery(name = "getUserCount", query = "select count(u) from UserEntity u"),
        @NamedQuery(name = "getAllUsers", query = "select u from UserEntity u"),
        @NamedQuery(name = "searchForUser", query = "select u from UserEntity u where " +
                "( lower(u.username) like :search or u.email like :search ) order by u.username"),
})
@Entity
public class UserEntity {
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Id
    private String id;

    private String username;

    private String email;

    private String password;

    @Embedded
    private AdditionalInformation additionalInformation = new AdditionalInformation();

    protected UserEntity() {
    }

    public UserEntity(
            final String username,
            final String email,
            final String password,
            final AdditionalInformation additionalInformation
    ) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.additionalInformation = additionalInformation;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public AdditionalInformation getAdditionalInformation() {
        return additionalInformation;
    }
}
