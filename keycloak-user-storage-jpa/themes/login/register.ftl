<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=social.displayInfo; section>
    <#if section = "header">
        SOCOA REGISTER
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
                <form id="kc-form-login" class="app-form" action="${url.registrationAction}" method="post">
                    <div>
                        <div class="login-field">
                            <label for="user.attributes.email" class="login-field"> 이메일 </label>
                        </div>
                        <div>
                            <input type="text" id="user.attributes.email" class="login-field"
                                   name="user.attributes.email"
                                   value="${(register.formData['user.attributes.email']!'')}"/>
                        </div>
                    </div>
                    <div>
                        <label>
                            <div>아이디</div>
                            <input id="username" class="login-field" type="text" name="username">
                        </label>
                    </div>
                    <div>
                        <label>
                            <div>비밀번호</div>
                            <input id="password" class="login-field" type="password" name="password">
                        </label>
                    </div>
                    <div>
                        <label>
                            <div>비밀번호 확인</div>
                            <input id="password-confirm" class="login-field" type="password" name="password-confirm"
                                   autocomplete="new-password" required>
                        </label>
                    </div>

                    <div class="${properties.kcFormGroupClass!}" >
                        <div class="mdc-text-field mdc-text-field--with-leading-icon ${properties.kcLabelClass!}">
                            <label id="user.attributes.nicknameLabel" for="user.attributes.nickname" class="mdc-floating-label ${properties.kcLabelClass!}">닉네임</label>
                            <input id="user.attributes.nickname" type="text" class="mdc-text-field__input ${properties.kcInputClass!}" name="user.attributes.nickname" value="${(register.formData['user.attributes.nickname']!'')}" />
                        </div>


                    <div>
                        <div class="login-field">
                            <label for="user.attributes.phoneNumber" class="login-field"> 전화번호 </label>
                        </div>
                        <div class="login-field">
                            <input type="text" id="user.attributes.phoneNumber" class="login-field"
                                   name="user.attributes.phoneNumber"
                                   value="${(register.formData['user.attributes.phoneNumber']!'')}"/>
                        </div>
                    </div>

                    <div>
                        <div class="login-field">
                            <label for="user.attributes.address" class="login-field"> 주소 </label>
                        </div>
                        <div class="login-field">
                            <input type="address" id="user.attributes.address" class="login-field"
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
                    <div>
                        <div class="login-field">
                            <label for="user.attributes.gender" class="login-field"> 성별 </label>
                        </div>
                        <div class="login-field">
                            <select id="user.attributes.gender" name="user.attributes.gender">
                                <option value="MALE">남성</option>
                                <option value="FEMALE">여성</option>
                            </select>
                        </div>
                    </div>
                    <div>
                        <div class="login-field">
                            <label for="user.attributes.userInformationType" class="login-field"> 유저 타입 </label>
                        </div>
                        <div class="login-field">
                            <select id="user.attributes.userInformationType" name="user.attributes.userInformationType">
                                <option value="STUDENT">학생</option>
                                <option value="TEACHER">강사</option>
                            </select>
                        </div>
                    </div>
                    <div>
                        <div>
                            <div class="login-field">
                                <label for="user.attributes.birth" class="login-field"> 생년월일 </label>
                            </div>
                            <div class="login-field">
                                <input type="date" id="user.attributes.birth" class="login-field"
                                       name="user.attributes.birth"
                                       value="${(register.formData['user.attributes.birth']!'')}"/>
                            </div>
                        </div>
                    </div>
                    <button class="submit" type="submit">회원 가입</button>
                </form>
            </div>
        </#if>
    </#if>
</@layout.registrationLayout>
