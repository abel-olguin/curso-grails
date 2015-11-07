<!DOCTYPE html>

<html>
    <head>
        <meta name="layout" content="main_not_auth" />
        <g:set var="entityName" value="${message(code: 'usuario.label', default: 'Usuario')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#create-usuario" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="create-usuario" class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.usuario}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.usuario}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>

            <g:form action="save">

                <fieldset class="form">

                    <f:field property="nombre" bean="usuario" class="nombre-inp"/>
                    
                    <f:field property="primerApellido" bean="usuario"/>

                    <f:field property="segundoApellido" bean="usuario"/>

                    <f:field property="username">
                        <g:field name="username"/>
                    </f:field>

                    <f:field property="password">
                        <g:field name="password" type="password"/>
                    </f:field>

                    <f:field property="edad" bean="usuario">
                        <g:select name="edad" from="${18..65}" noSelection="['':'-Elige tu edad-']" />
                    </f:field>

                    <f:field property="sexo" bean="usuario"/>

                    <f:field property="email" bean="usuario"/>

                    <f:field property="casado" bean="usuario"/>
                   
                </fieldset>


                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </fieldset>
            </g:form>
            
        </div>
    </body>
</html>
