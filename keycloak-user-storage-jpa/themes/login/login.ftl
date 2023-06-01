<#import "template.ftl" as layout>

    <div class="loginPage">
<@layout.registrationLayout displayInfo=social.displayInfo displayWide=(realm.password && social.providers??); section>
    <#if section = "header">
        ${msg("doLogIn")}
    <#elseif section = "form">
        <div id="kc-form" <#if realm.password && social.providers??> class="${properties.kcContentWrapperClass!}"</#if>>
            <div id="kc-form-wrapper" <#if realm.password && social.providers??>class="${properties.kcFormSocialAccountContentClass!} ${properties.kcFormSocialAccountClass!}"</#if>>
                <#if realm.password>
                    <form id="kc-form-login" onsubmit="login.disabled = true; return true;" action="${url.loginAction}" method="post">
                        <div class="${properties.kcFormGroupClass!}">

                            <div class="mdc-text-field mdc-text-field--with-leading-icon ${properties.kcLabelClass!} <#if usernameEditDisabled??>mdc-text-field--disabled</#if>">
                                <i class="material-icons mdc-text-field__icon" role="button"></i>
                                <#--유저네임 또는 이메일 입력란-->
                                <input tabindex="0" required id="username" class="mdc-text-field__input ${properties.kcInputClass!}" name="username" value="${(login.username!'')}" type="text" autofocus autocomplete="off" <#if usernameEditDisabled??>disabled</#if>>
                                <div class="mdc-line-ripple"></div>
                                <label id="usernameLabel" for="username" class="mdc-floating-label ${properties.kcLabelClass!}">
                                    <#if !realm.loginWithEmailAllowed>
                                        ${msg("username")}
                                    <#elseif !realm.registrationEmailAsUsername>
                                        ${msg("usernameOrEmail")}
                                    <#else>
                                        ${msg("email")}
                                    </#if>
                                </label>
                            </div>
                        </div>

                        <script>
                            // input 요소 가져오기
                            var usernameInput = document.getElementById('username');

                            // input 값 변경 이벤트 처리
                            usernameInput.addEventListener('input', function() {
                                // 입력 값이 있는지 확인
                                if (usernameInput.value.trim() !== '') {
                                    // label의 가시성 숨기기
                                    document.getElementById('usernameLabel').style.display = 'none';
                                } else {
                                    // label의 가시성 표시
                                    document.getElementById('usernameLabel').style.display = 'block';
                                }
                            });
                        </script>

                        <div class="${properties.kcFormGroupClass!}">
                            <div class="mdc-text-field mdc-text-field--with-leading-icon ${properties.kcLabelClass!}">
                                <#--비밀번호 입력란-->
                                <i class="material-icons mdc-text-field__icon" role="button"></i>
                                <input tabindex="0" required id="password" class="mdc-text-field__input ${properties.kcInputClass!}" name="password" type="password" autocomplete="off">
                                <div class="mdc-line-ripple"></div>
                                <label id="passwordLabel" for="password" class="mdc-floating-label ${properties.kcLabelClass!}">${msg("password")}</label>
                            </div>
                        </div>

                        <script>
                            // input 요소 가져오기
                            var passwordInput = document.getElementById('password');

                            // input 값 변경 이벤트 처리
                            passwordInput.addEventListener('input', function() {
                                // 입력 값이 있는지 확인
                                if (passwordInput.value.trim() !== '') {
                                    // label의 가시성 숨기기
                                    document.getElementById('passwordLabel').style.display = 'none';
                                } else {
                                    // label의 가시성 표시
                                    document.getElementById('passwordLabel').style.display = 'block';
                                }
                            });
                        </script>

                        <div class="${properties.kcFormGroupClass!} ${properties.kcFormSettingClass!}">
                            <div id="kc-form-options">
                                <#if realm.rememberMe && !usernameEditDisabled??>

                                    <div class="mdc-form-field">
                                        <div class="mdc-checkbox">
                                            <input tabindex="0"
                                                   id="rememberMe"
                                                   name="rememberMe"
                                                   type="checkbox"
                                                   class="mdc-checkbox__native-control"
                                                   <#if login.rememberMe??>checked</#if>
                                            />
                                            <div class="mdc-checkbox__background">
                                                <svg class="mdc-checkbox__checkmark"
                                                     viewBox="0 0 24 24">
                                                    <path class="mdc-checkbox__checkmark-path"
                                                          fill="none"
                                                          d="M1.73,12.91 8.1,19.28 22.79,4.59"/>
                                                </svg>
                                                <div class="mdc-checkbox__mixedmark"></div>
                                            </div>
                                            <div class="mdc-checkbox__ripple"></div>
                                        </div>
                                        <label for="rememberMe">${msg("rememberMe")}</label>
                                    </div>

                                </#if>
                            </div>
                            <div class="${properties.kcFormOptionsWrapperClass!}">
                                <#if realm.resetPasswordAllowed>
                                    <span><a tabindex="0" href="${url.loginResetCredentialsUrl}">${msg("doForgotPassword")}</a></span>
                                </#if>
                            </div>
                        </div>

                        <div class="mdc-card__action-icons">
                            <div class="mdc-card__action-buttons">
                                <button tabindex="0" name="login" id="kc-login" type="submit" class="mdc-button mdc-button--raised mdc-card__action">
                                    ${msg("doLogIn")}
                                </button>
                            </div>
                        </div>
                    </form>
                </#if>
            </div>
            <#if realm.password && social.providers??>
                <div id="kc-social-providers" class="${properties.kcFormSocialAccountContentClass!} ${properties.kcFormSocialAccountClass!}">
                    <div class="${properties.kcFormSocialAccountListClass!} <#if social.providers?size gt 4>${properties.kcFormSocialAccountDoubleListClass!}</#if>">
                        <#list social.providers as p>
                            <div  class="${properties.kcFormSocialAccountListLinkClass!}">
                                <a href="${p.loginUrl}" id="zocial-${p.alias}" class="zocial ${p.providerId}">
                                    <span>with ${p.displayName}</span>
                                </a>
                            </div>
                        </#list>
                    </div>
                </div>
            </#if>
        </div>

    <#elseif section = "info" >
        <#if realm.password && realm.registrationAllowed && !registrationDisabled??>

            <hr class="mdc-list-divider divider-mdc">

            <div id="kc-registration" class="registration-label-mdc">
                <span>${msg("noAccount")} <a tabindex="0" href="${url.registrationUrl}">${msg("doRegister")}</a></span>
            </div>
        </#if>
    </#if>

</@layout.registrationLayout>

    </div>