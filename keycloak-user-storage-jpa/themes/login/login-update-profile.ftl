<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=social.displayInfo; section>
    <#if section = "header">
        UPDATE PROFILE
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
            <h1 class="app-name">회원 정보 추가</h1>
        </div>
    <#elseif section = "form">
        <#if realm.password>
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

                    // 모든 입력 필드에 대한 이벤트 리스너를 추가
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
                <h1>회원 정보 추가 입력</h1>
                <form id="kc-register-form" class="${properties.kcFormClass!}" action="${url.loginAction}" method="post">
                    <div class="${properties.kcFormGroupClass!}">
                        <div class="mdc-text-field mdc-text-field--with-leading-icon ${properties.kcLabelClass!}">
                            <label id="phoneNumberLabel" for="user.attributes.phoneNumber" class="mdc-floating-label ${properties.kcLabelClass!}">전화 번호</label>
                            <input id="phoneNumber" class="mdc-text-field__input ${properties.kcInputClass!}" type="tel" class="form-control" id="user.attributes.phoneNumber" name="user.attributes.phoneNumber" value="${(user.attributes.phoneNumber!'')}" />
                        </div>
                    </div>

                    <div class="${properties.kcFormGroupClass!}" >
                        <div class="mdc-text-field mdc-text-field--with-leading-icon ${properties.kcLabelClass!}">
                            <label id="user.attributes.nicknameLabel" for="user.attributes.nickname" class="mdc-floating-label ${properties.kcLabelClass!}">닉네임</label>
                            <input id="user.attributes.nickname" type="text" class="mdc-text-field__input ${properties.kcInputClass!}" name="user.attributes.nickname" value="${(user.attributes.nickname!'')}" />
                        </div>
                    </div>

                    <div class="${properties.kcFormGroupClass!}">
                        <div class="mdc-text-field mdc-text-field--with-leading-icon ${properties.kcLabelClass!}">
                            <label class="mdc-floating-label ${properties.kcLabelClass!}" id="user.attributes.firstNameLabel" for="user.attributes.firstName">성</label>
                            <input class="mdc-text-field__input ${properties.kcInputClass!}" type="text"  id="user.attributes.firstName" name="user.attributes.firstName" value="${(user.attributes.firstName!'')}" />
                        </div>
                    </div>

                    <div class="${properties.kcFormGroupClass!}">
                        <div class="mdc-text-field mdc-text-field--with-leading-icon ${properties.kcLabelClass!}">
                            <label class="mdc-floating-label ${properties.kcLabelClass!}"  id="user.attributes.lastNameLabel" for="user.attributes.lastName">이름</label>
                            <input class="mdc-text-field__input ${properties.kcInputClass!}" type="text"  id="user.attributes.lastName" name="user.attributes.lastName" value="${(user.attributes.lastName!'')}" />
                        </div>
                    </div>

                    <div class="${properties.kcFormGroupClass!}">
                        <div class="mdc-text-field mdc-text-field--with-leading-icon ${properties.kcLabelClass!}">
                            <label class="mdc-floating-label ${properties.kcLabelClass!}" for="user.attributes.email" id="user.attributes.emailLabel">이메일</label>
                            <input class="mdc-text-field__input ${properties.kcInputClass!}" type="email"  id="user.attributes.email" name="user.attributes.email" value="${(user.attributes.email!'')}" />
                        </div>
                    </div>

                    <div class="${properties.kcFormGroupClass!}">
                        <div class="mdc-text-field mdc-text-field--with-leading-icon ${properties.kcLabelClass!}">
                            <label class="mdc-floating-label ${properties.kcLabelClass!}" id="user.attributes.addressLabel" for="user.attributes.lastName">주소</label>
                            <input class="mdc-text-field__input ${properties.kcInputClass!}" type="address"  id="user.attributes.address" name="user.attributes.address" value="${(user.attributes.address!'')}" />
                        </div>
                    </div>

                    <div class="${properties.kcFormGroupClass!}">
                        <div class="mdc-text-field mdc-text-field--with-leading-icon ${properties.kcLabelClass!}">
                            <label class="mdc-floating-label ${properties.kcLabelClass!}" id="user.attributes.genderLabel" for="user.attributes.gender"> 성별 </label>
                            <select tabindex="0" class="mdc-select ${properties.kcInputClass!}" id="user.attributes.gender" name="user.attributes.gender">
                                <option value=""></option>
                                <option value="MALE">남성</option>
                                <option value="FEMALE">여성</option>
                            </select>
                        </div>
                    </div>

                    <div class="${properties.kcFormGroupClass!}">
                        <div class="mdc-text-field mdc-text-field--with-leading-icon ${properties.kcLabelClass!}">
                            <label class="mdc-floating-label ${properties.kcLabelClass!}" id="user.attributes.userInformationTypeLabel" for="user.attributes.userInformationType"> 유저 타입 </label>
                            <select tabindex="0" class="mdc-select ${properties.kcInputClass!}" id="user.attributes.userInformationType" name="user.attributes.userInformationType">
                                <option value=""></option>
                                <option value="STUDENT">학생</option>
                                <option value="TEACHER">강사</option>
                            </select>
                        </div>
                    </div>

                    <style>
                        select{
                            border: none;
                            outline: none;
                            background-color: #f8f7f7;
                            width: 100%;
                        }
                    </style>

                    <script>
                        // select 요소 가져오기
                        var selectElement = document.getElementById('user.attributes.gender');
                        // label 요소 가져오기
                        var labelElement = document.getElementById('user.attributes.genderLabel');

                        // select 값 변경 시 이벤트 처리
                        selectElement.addEventListener('change', function() {
                            // select 값이 있으면 label 숨기기
                            if (selectElement.value !== '') {
                                labelElement.style.display = 'none';
                            } else {
                                labelElement.style.display = 'block';
                            }
                        });
                    </script>

                    <div>
                        <div class="${properties.kcFormGroupClass!} ${messagesPerField.printIfExists('birth',properties.kcFormGroupErrorClass!)}">
                            <div class="mdc-text-field mdc-text-field--with-leading-icon ${properties.kcLabelClass!}">
<#--                                <label for="user.attributes.birth"> 생년월일 </label>-->
                                <input tabindex="0" class="mdc-text-field__input ${properties.kcInputClass!}" type="date" id="user.attributes.birth"
                                       name="user.attributes.birth"
                                       value="${(user.attributes.birth!'')}"/>
                            </div>
                        </div>
                        <button  class=" mdc-button mdc-button--raised mdc-card__action" type="submit">등록 버튼</button>
                    </div>
                </form>
        </#if>
    </#if>
</@layout.registrationLayout>
