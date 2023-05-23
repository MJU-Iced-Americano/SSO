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
                <form id="kc-form-login" class="app-form" onsubmit="return false;" action="http://localhost/user/" method="post">
                    <label>
                        <div>이메일 또는 아이디</div>
                        <input id="username" class="login-field" type="text" name="username">
                    </label>

                    <label>
                        <div>비밀번호</div>
                        <input id="password" class="login-field" type="password" name="password">
                    </label>

                    <label>
                        <div>비밀번호 확인</div>
                        <input id="password-confirm" class="login-field" type="password" name="password-confirm"
                               autocomplete="new-password" required>
                    </label>

                    <label>
                        <div>이메일</div>
                        <input id="email" class="login-field" type="email" name="email">
                    </label>

                    <label>
                        <div>닉네임</div>
                        <input id="nickname" class="login-field" type="nickname" name="nickname">
                    </label>

                    <button class="submit" type="submit">회원 가입</button>
                </form>
            </div>
        </#if>
    </#if>
</@layout.registrationLayout>
