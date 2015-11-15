<%--
  Created by IntelliJ IDEA.
  User: abelolguinchavez
  Date: 14/11/15
  Time: 10:21 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main_not_auth" />
    <title>Cambia tu contraseña</title>
</head>

<body>
    <g:if test="${errors}">
        <ul class="errors" role="alert">
            <g:each in="${errors}" var="error">
                <li class="error">
                    <g:message code="${error}" default="Error al procesar los datos"/>
                </li>
            </g:each>
        </ul>
    </g:if>
    <g:form action="update_pass" method="POST">
        <g:field type="password" name="password" required="required" />

        <g:field type="password" name="passwordConfirm" required="required" />

        <g:hiddenField name="token" value="${params.token}"/>
        <input class="send" type="submit" value="${message(code: 'default.button.send.label', default: 'Send')}" />
    </g:form>
</body>
</html>