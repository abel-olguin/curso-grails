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
   
    <sec:ifNotLoggedIn>
        <body class="container no-padding">
            <div class="col-lg-12 container-fluid no-padding">
                <div id="grailsLogo" role="banner" ><a href="http://grails.org"><asset:image src="grails_logo.png" alt="Grails" class="img-responsive"/></a></div>
                <div class="format container col-lg-12">
                    <g:layoutBody/>
                </div>
                <div class="footer" role="contentinfo"></div>
                <div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
            </div>
        </body>
    </sec:ifNotLoggedIn>
  
</html>
