<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=social.displayInfo; section>
    <#if section = "header">
        <script>
            window.onload = function() {
                document.getElementById("header-container").focus();
            };
        </script>
        <style>
            .header-container {
                margin: 0;
                display: flex;
                justify-content: center;
                align-items: center;
            }
        </style>
        <div class="header-container">
            SOCOA REGISTER
        </div>
    <#elseif section = "header">
        <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <#elseif section = "message">
    <#--  type종류: success, warning, error, info  -->
        <div class="alert alert-${message.type}">
            <span class="message-text">${message.summary?no_esc}</span>
        </div>
    <#elseif section = "form">
        <#if realm.password>
            <div class="app-form-wrapper">
                <script>

                    document.addEventListener("DOMContentLoaded", function() {
                        // 입력 필드의 값이 변경될 때 라벨의 가시성을 설정하는 함수
                        function toggleLabelVisibility(inputId) {
                            var inputField = document.getElementById(inputId);
                            var label = document.getElementById(inputId + "Label");

                            if (inputField.value !== "") {
                                label.style.display = "none"; // 값이 있을 때 라벨을 숨김
                            } else {
                                label.style.display = "block"; // 값이 없을 때 라벨을 보임
                            }
                        }

                        // 모든 입력 필드와 select 태그에 대한 이벤트 리스너를 추가
                        var inputFields = document.querySelectorAll("input");

                        inputFields.forEach(function(inputField) {
                            inputField.addEventListener("input", function() {
                                toggleLabelVisibility(inputField.id);
                            });

                            // 초기 라벨의 가시성 설정
                            toggleLabelVisibility(inputField.id);
                        });

                    });
                </script>
                <form id="kc-form-login" class="app-form ${properties.kcFormClass!}" action="${url.registrationAction}" method="post">
                    <div class="${properties.kcFormGroupClass!}">
                        <div class="alwaysVisible">
                            <label > 이메일 </label>
                        </div>
                        <div class="mdc-text-field mdc-text-field--with-leading-icon ${properties.kcLabelClass!}">
                            <label id="user.attributes.emailLabel" for="user.attributes.email" class="mdc-floating-label ${properties.kcLabelClass!}"> 이메일 </label>
                            <input type="text" id="user.attributes.email"
                                   class="mdc-text-field__input ${properties.kcInputClass!}"
                                   name="user.attributes.email"
                                   value="${(register.formData['user.attributes.email']!'')}"/>
                        </div>
                    </div>

                    <div class="${properties.kcFormGroupClass!}">
                        <div class="alwaysVisible">
                            <label > 아이디 </label>
                        </div>
                        <div class="mdc-text-field mdc-text-field--with-leading-icon ${properties.kcLabelClass!}">
                            <label id="usernameLabel" class="mdc-floating-label ${properties.kcLabelClass!}">아이디</label>
                            <input tabindex="0" type="text" id="username"
                                   class="login-field mdc-text-field__input ${properties.kcInputClass!}"
                                   name="username">
                        </div>
                    </div>

                    <div class="${properties.kcFormGroupClass!}">
                        <div class="alwaysVisible">
                            <label > 비밀번호 </label>
                        </div>
                        <div class="mdc-text-field mdc-text-field--with-leading-icon ${properties.kcLabelClass!}">
                            <label id="passwordLabel" for="password" class="mdc-floating-label ${properties.kcLabelClass!}"> 비밀번호 </label>
                            <input id="password" class="mdc-text-field__input ${properties.kcInputClass!}" type="password" name="password">
                        </div>
                    </div>

                    <div class="${properties.kcFormGroupClass!}">
                        <div class="alwaysVisible">
                            <label > 비밀번호 확인 </label>
                        </div>
                        <div class="mdc-text-field mdc-text-field--with-leading-icon ${properties.kcLabelClass!}">
                            <label id="password-confirmLabel" for="password-confirm" class="mdc-floating-label ${properties.kcLabelClass!}"> 비밀번호 확인 </label>
                            <input id="password-confirm"
                                   class="mdc-text-field__input ${properties.kcInputClass!}"
                                   type="password" name="password-confirm"
                                   autocomplete="new-password" required>
                        </div>
                    </div>

                    <div class="${properties.kcFormGroupClass!}">
                        <div class="alwaysVisible">
                            <label >  닉네임 </label>
                        </div>
                        <div class="mdc-text-field mdc-text-field--with-leading-icon ${properties.kcLabelClass!}">
                            <label id="user.attributes.nicknameLabel" for="user.attributes.nickname" class="mdc-floating-label ${properties.kcLabelClass!}">닉네임</label>
                            <input id="user.attributes.nickname"
                                   class="mdc-text-field__input ${properties.kcInputClass!}"
                                   type="text" name="user.attributes.nickname"
                                   value="${(register.formData['user.attributes.nickname']!'')}" />
                        </div>
                    </div>

                    <div class="${properties.kcFormGroupClass!}">
                        <div class="alwaysVisible">
                            <label >  전화번호 </label>
                        </div>
                        <div class="mdc-text-field mdc-text-field--with-leading-icon ${properties.kcLabelClass!}">
                            <label id="user.attributes.phoneNumberLabel" class="mdc-floating-label ${properties.kcLabelClass!}" for="user.attributes.phoneNumber" class="login-field"> 전화번호 </label>
                            <input type="text" id="user.attributes.phoneNumber"
                                   class="mdc-text-field__input ${properties.kcInputClass!}"
                                   name="user.attributes.phoneNumber"
                                   value="${(register.formData['user.attributes.phoneNumber']!'')}"/>
                        </div>
                    </div>

                    <div class="${properties.kcFormGroupClass!}">
                        <div class="alwaysVisible">
                            <label >  주소 </label>
                        </div>
                        <div class="mdc-text-field mdc-text-field--with-leading-icon ${properties.kcLabelClass!}">
                            <label id="user.attrfibutes.addressLabel" class="mdc-floating-label ${properties.kcLabelClass!}" for="user.attributes.address" class="login-field"> 주소 </label>
                            <input
                                id="user.attrfibutes.address"
                                type="address" id="user.attributes.address"
                                class="mdc-text-field__input ${properties.kcInputClass!}"
                                name="user.attributes.address"
                                value="${(register.formData['user.attributes.address']!'')}"/>
                        </div>
                    </div>

                    <div style="display: none">
                        <div class="login-field">
                            <label for="user.attributes.profileImageUrl" class="login-field"> 이미지 URL </label>
                        </div>
                        <div class="login-field">
                            <input type="text" id="user.attributes.profileImageUrl" class="login-field"
                                   name="user.attributes.profileImageUrl"
                                   value="null"/>
                        </div>
                    </div>

                    <div class="${properties.kcFormGroupClass!}">
                        <div class="alwaysVisible">
                            <label >  성별 </label>
                        </div>
                        <div class="mdc-text-field mdc-text-field--with-leading-icon ${properties.kcLabelClass!}">
                            <select tabindex="0" class="mdc-select ${properties.kcInputClass!}" id="user.attributes.gender" name="user.attributes.gender">
                                <option value="MALE">남성</option>
                                <option value="FEMALE">여성</option>
                            </select>
                        </div>
                    </div>

                    <div class="${properties.kcFormGroupClass!}">
                        <div class="alwaysVisible">
                            <label >  유저 타입 </label>
                        </div>
                        <div class="mdc-text-field mdc-text-field--with-leading-icon ${properties.kcLabelClass!}">
                            <select id="user.attributes.userInformationType" tabindex="0" class="mdc-select ${properties.kcInputClass!}" name="user.attributes.userInformationType">
                                <option value="STUDENT">학생</option>
                                <option value="TEACHER">강사</option>
                                <option value="MANAGER">관리자</option>
                            </select>
                        </div>
                    </div>

                    <div class="${properties.kcFormGroupClass!}">
                        <div class="alwaysVisible">
                            <label >  생년월일 </label>
                        </div>
                        <div class="mdc-text-field mdc-text-field--with-leading-icon ${properties.kcLabelClass!}">
                                <input type="date" id="user.attributes.birth"
                                       class="mdc-text-field__input ${properties.kcInputClass!}"
                                       name="user.attributes.birth"
                                       value="${(register.formData['user.attributes.birth']!'')}"
                                       style="font-size: 0.8rem;"
                                />
                        </div>
                    </div>
                    <div style="text-align: center">
                        <button class="submit mdc-button mdc-button--raised mdc-card__action" type="submit" style="margin-top: 10px;">회원 가입</button>
                    </div>
                </form>

            </div>
        </#if>
    </#if>
</@layout.registrationLayout>
