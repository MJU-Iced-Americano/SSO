package org.mju.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class AdditionalInformation {
    private String nickname;

    private String phoneNumber;

    private String address;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Enumerated(value = EnumType.STRING)
    private UserInformationType userInformationType;

    @Embedded
    private Birth birth = new Birth();

    @Column(columnDefinition = "TEXT")
    private String profileImageUrl;

    protected AdditionalInformation() {

    }

    public AdditionalInformation(
            final String nickname,
            final String phoneNumber,
            final String address,
            final Gender gender,
            final UserInformationType userInformationType,
            final Birth birth,
            final String profileImageUrl
    ) {
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.userInformationType = userInformationType;
        this.birth = birth;
        this.profileImageUrl = profileImageUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public Gender getGender() {
        return gender;
    }

    public UserInformationType getUserInformationType() {
        return userInformationType;
    }

    public Birth getBirth() {
        return birth;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
