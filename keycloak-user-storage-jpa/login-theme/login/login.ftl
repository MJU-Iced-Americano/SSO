<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=social.displayInfo; section>
    <#if section = "title">
        커스텀 로그인 타이틀
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
            <#--  <h1 class="app-name">커스텀 로그인 폼</h1>  -->
        </div>
    <#elseif section = "form">
        <#if realm.password>
            <div class="app-form-wrapper">
                <form id="kc-form-login" class="app-form" onsubmit="return true;" action="${url.loginAction}"
                      method="post">
                    <label>
                        <div>이메일 또는 아이디</div>
                        <input id="username" class="login-field" type="text" name="username">
                    </label>

                    <label>
                        <div>비밀번호</div>
                        <input id="password" class="login-field" type="password" name="password">
                    </label>

                    <button class="submit" type="submit">로그인</button>
                </form>
            </div>
            <div class="${properties.kcFormGroupClass!} ${properties.kcFormSettingClass!}">
                <div id="kc-form-options">
                    <#if realm.rememberMe>
                        <div class="checkbox">
                            <label>
                                <#if login.rememberMe??>
                                    <input tabindex="3" id="rememberMe" name="rememberMe" type="checkbox" checked> ${msg("rememberMe")}
                                <#else>
                                    <input tabindex="3" id="rememberMe" name="rememberMe" type="checkbox"> ${msg("rememberMe")}
                                </#if>
                            </label>
                        </div>
                    </#if>
                </div>
                <div class="${properties.kcFormOptionsWrapperClass!}">
                    <#if realm.resetPasswordAllowed>
                        <span><a tabindex="5" href="${url.loginResetCredentialsUrl}">${msg("doForgotPassword")}</a></span>
                    </#if>
                </div>

            </div>

            <div id="kc-form-buttons" class="${properties.kcFormGroupClass!}">
                <input type="hidden" id="id-hidden-input" name="credentialId" <#if auth.selectedCredential?has_content>value="${auth.selectedCredential}"</#if>/>
                <#if realm.password && realm.registrationAllowed && !registrationDisabled??>
                    <div id="kc-registration-container">
                        <div id="kc-registration">
                            <span><a tabindex="6" href="${url.registrationUrl}"><i class="fa fa-plus-circle"></i> ${msg("doRegister")}</a></span>
                        </div>
                    </div>
                </#if>
            </div>
        </#if>
    <#--        <#if social.providers??>-->
    <#--            <div id="social-providers">-->
    <#--                <#list social.providers as p>-->
    <#--                    <input class="social-link-style" type="button" onclick="location.href='${p.loginUrl}';"-->
    <#--                           value="${p.displayName}"/>-->
    <#--                </#list>-->
    <#--            </div>-->
    <#--        </#if>-->
    </#if>
</@layout.registrationLayout>
