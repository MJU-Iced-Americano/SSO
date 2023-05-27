<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=social.displayInfo; section>
    <#if section = "title">
        커스텀 회원 가입 타이틀
    <#elseif section = "header">
        <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <#elseif section = "message">
    <#--  type종류: success, warning, error, info  -->
        <div class="alert alert-${message.type}">
            <span class="message-text">${message.summary?no_esc}</span>
        </div>
    <#elseif section = "app-head">
        <div class="app-name-wrapper">
            <img src="https://go.dev/images/gophers/motorcycle.svg"/>
            <h1 class="app-name">회원 가입</h1>
        </div>
    <#elseif section = "form">
        <#if realm.password>
            <div class="app-form-wrapper">
                <form id="kc-form-login" class="app-form" action="${url.registrationAction}" method="post">
                    <div>
                        <div class="login-field">
                            <label for="user.attributes.email" class="login-field"> 이메일 </label>
                        </div>
                        <div class="login-field">
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
                            <input id="password-confirm" type="password" name="password-confirm"
                                   autocomplete="new-password" required>
                        </label>
                    </div>
                    <div>
                        <div>
                            <label for="user.attributes.nickname" class="login-field"> 닉네임 </label>
                        </div>
                        <div>
                            <input type="text" id="user.attributes.nickname"
                                   name="user.attributes.nickname"
                                   value="${(register.formData['user.attributes.nickname']!'')}"/>
                        </div>
                    </div>
                    <div>
                        <div>
                            <label for="user.attributes.phoneNumber" > 전화번호 </label>
                        </div>
                        <div>
                            <input type="text" id="user.attributes.phoneNumber"
                                   name="user.attributes.phoneNumber"
                                   value="${(register.formData['user.attributes.phoneNumber']!'')}"/>
                        </div>
                    </div>

                    <div>
                        <div>
                            <label for="user.attributes.address"> 주소 </label>
                        </div>
                        <div>
                            <input type="address" id="user.attributes.address"
                                   name="user.attributes.address"
                                   value="${(register.formData['user.attributes.address']!'')}"/>
                        </div>
                    </div>

                    <div style="display: none">
                        <div>
                            <label for="user.attributes.profileImageUrl"> 이미지 URL </label>
                        </div>
                        <div>
                            <input type="text" id="user.attributes.profileImageUrl"
                                   name="user.attributes.profileImageUrl"
                                   value="null"/>
                        </div>
                    </div>

                    <div>
                        <div>
                            <label for="user.attributes.gender"> 성별 </label>
                        </div>
                        <div>
                            <select id="user.attributes.gender" name="user.attributes.gender">
                                <option value="MALE">남성</option>
                                <option value="FEMALE">여성</option>
                            </select>
                        </div>
                    </div>
                    <div>
                        <div>
                            <div>
                                <label for="user.attributes.birth" class="login-field"> 생년월일 </label>
                            </div>
                            <div>
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