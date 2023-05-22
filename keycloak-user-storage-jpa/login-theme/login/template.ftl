
<#macro registrationLayout bodyClass="" displayInfo=false displayMessage=true>
<!DOCTYPE 
  html
  PUBLIC 
  "-//W3C//DTD XHTML 1.0 Transitional//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="robots" content="noindex, nofollow">

    <title><#nested "title"></title>
    <link href="${url.resourcesPath}/img/favicon.png" rel="icon"/>
    <#--  css파일 추가  -->
    <#if properties.styles?has_content>
        <#list properties.styles?split(' ') as style>
            <link href="${url.resourcesPath}/${style}" rel="stylesheet" />
        </#list>
    </#if>
    <#--  /css파일 추가  -->
</head>
	<body>
    <#nested "header">
    <div class="login-form-wrapper">
      <div class="login-form-inner">
        <#if displayMessage && message?has_content>
          <#nested "message">
        </#if>
        <#nested "app-head">
        <#nested "form">
      </div>
    </div>
	</body>
</html>
</#macro>