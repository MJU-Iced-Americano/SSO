<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=social.displayInfo; section>
    <#if section = "title">
        커스텀 회원 정보 추가 타이틀
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
            <div class="app-form-wrapper">
                <form id="kc-form-login" class="app-form" action="${url.loginAction}" method="post">
                    <div>
                        <div>
                            <label for="user.attributes.phoneNumber">전화 번호</label>
                        </div>
                        <div>
                            <input type="tel" class="form-control" id="user.attributes.phoneNumber" name="user.attributes.phoneNumber" value="${(user.attributes.phoneNumber!'')}" />
                        </div>
                    </div>

                    <div>
                        <div>
                            <label for="user.attributes.nickname">닉네임</label>
                        </div>
                        <div>
                            <input type="text"  id="user.attributes.nickname" name="user.attributes.nickname" value="${(user.attributes.nickname!'')}" />
                        </div>
                    </div>

                    <div>
                        <div>
                            <label for="user.attributes.firstName">성</label>
                        </div>
                        <div>
                            <input type="text"  id="user.attributes.firstName" name="user.attributes.firstName" value="${(user.attributes.firstName!'')}" />
                        </div>
                    </div>

                    <div>
                        <div>
                            <label for="user.attributes.lastName">이름</label>
                        </div>
                        <div>
                            <input type="text"  id="user.attributes.lastName" name="user.attributes.lastName" value="${(user.attributes.lastName!'')}" />
                        </div>
                    </div>

                    <div>
                        <div>
                            <label for="user.attributes.email">이메일</label>
                        </div>
                        <div>
                            <input type="email"  id="user.attributes.email" name="user.attributes.email" value="${(user.attributes.email!'')}" />
                        </div>
                    </div>

                    <div>
                        <div>
                            <label for="user.attributes.lastName">주소</label>
                        </div>
                        <div>
                            <input type="address"  id="user.attributes.address" name="user.attributes.address" value="${(user.attributes.address!'')}" />
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
                             <label for="user.attributes.birth"> 생년월일 </label>
                         </div>
                         <div>
                             <input type="date" id="user.attributes.birth"
                                    name="user.attributes.birth"
                                    value="${(user.attributes.birth!'')}"/>
                         </div>
                     </div>
                    <button class="submit" type="submit">등록 버튼</button>
                </form>
            </div>
        </#if>
    </#if>
</@layout.registrationLayout>