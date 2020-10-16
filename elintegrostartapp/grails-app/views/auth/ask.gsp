<%@ page import="grails.plugin.springsecurity.SpringSecurityUtils" %>
<head>
    <g:set var="layoutName" value="${SpringSecurityUtils.securityConfig.getProperty('oauth2.view.layout')}"/>
    <meta name="layout" content="${layoutName ?: 'main'}"/>
    <title>Create or Link Account ${layoutName}</title>
    <style type="text/css">
    fieldset {
        border: 1px solid green;
        padding: 1em;
        font: 80%/1 sans-serif;
    }

    fieldset legend {
        padding: 0.2em 0.5em;
        border: 1px solid green;
        color: green;
        font-weight: bold;
        font-size: 90%;
        text-align: right;
    }

    fieldset label {
        float: left;
        width: 25%;
        margin-top: 5px;
        margin-right: 0.5em;
        padding-top: 0.2em;
        text-align: right;
        font-weight: bold;
    }

    fieldset input[type="submit"] {
        float: right;
        background: #F0F0F0;
        cursor: pointer;
    }

    fieldset br {
        margin-top: 10px;
    }
    </style>
</head>

<body>

<div class='body' style="padding: 15px;">
    <g:if test='${flash.error}'>
        <div class="errors">${flash.error}</div>
    </g:if>

<!--
    <h4><g:message code="springSecurity.oauth.registration.link.not.exists"
                   default="No user was found with this account."
                   args="[session.springSecurityOAuthToken?.providerName]"/></h4>
-->

    <g:hasErrors bean="${linkAccountCommand}">
        <div class="errors">
            <g:renderErrors bean="${linkAccountCommand}" as="list"/>
        </div>
    </g:hasErrors>

    <g:form action="/springSecurityOAuth2/linkAccount " method="post" autocomplete="off">
        <fieldset>
            <legend><g:message code="springSecurity.oauth.login.googleSignIn"
                               default="Link to elintegro account"/></legend>

            <div class="fieldcontain ${hasErrors(bean: linkAccountCommand, field: 'username', 'error')} ">
                <label for='username'><g:message code="OAuthLinkAccountCommand.username.label"
                                                 default="Username"/>:</label>
                <g:textField name='username' value='${linkAccountCommand?.username}'/>
            </div>

            <div class="fieldcontain ${hasErrors(bean: linkAccountCommand, field: 'password', 'error')} ">
                <label for='password'><g:message code="OAuthLinkAccountCommand.password.label"
                                                 default="Password"/>:</label>
                <g:passwordField name='password' value='${linkAccountCommand?.password}'/>
            </div>
            <input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me'/>
            <g:submitButton
                    name="${message(code: 'springSecurity.oauth.registration.login.button', default: 'Login')}"/>
        </fieldset>
    </g:form>

    <br/>

    <g:link controller="login" action="auth"><g:message code="springSecurity.oauth.registration.back"
                                                        default="Back to login page"/></g:link>
</div>

</body>
