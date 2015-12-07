<!DOCTYPE html>
<html lang="en" class="no-js">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title><g:layoutTitle default="Grails"/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <asset:stylesheet src="application.css"/>
        <asset:javascript src="application.js"/>

        <g:layoutHead/>
    </head>
    <sec:ifLoggedIn>

        <body class="container no-padding">
            <nav class="navbar navbar-inverse">
                <div class="container-fluid">
                    <div class="navbar-header">

                        <a class="navbar-brand" href="${createLink(uri: '/', absolute: true)}">Home</a>
                    </div>

                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-6">
                        <ul class="nav navbar-nav">
                            <li class="active"><g:link controller="usuario" action="show">Perfil</g:link></li>
                            <li><g:link controller='logout' action=''>Logout</g:link></li>
                            <li><g:link controller='contact' action='index'>Contacto</g:link></li>
                            <li><g:link controller='entradas' action='index'>Contacto</g:link></li>
                        </ul>
                    </div>
                </div>
            </nav>

            <div class="col-lg-12 container-fluid no-padding">

                <div id="grailsLogo img-responsive" role="banner"><a href="http://grails.org"><asset:image src="grails_logo.png" alt="Grails"/></a></div>
                <div class="format container col-lg-12">
                    <g:layoutBody/>
                </div>
                <div class="footer" role="contentinfo"></div>
                <div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
            </div>
        </body>
    
    </sec:ifLoggedIn>
</html>
