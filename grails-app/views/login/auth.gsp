<html>
<head>
	<meta name="layout" content="main_not_auth" />
	<title><g:message code="springSecurity.login.title" default="Ingresar" /></title>
	
</head>

<body>

    	<div class="col-lg-4 col-lg-offset-4" id="login-block">
    		
			<form action='${postUrl}' method='POST' id='loginForm'  autocomplete='off'>

				<div class="form-group">
					<label for="j_username">${message(code: 'login.username.label', default: 'Username')}</label>
					<input type="text" id="j_username" name='j_username' class="form-control">
				</div>

				<div class="form-group">
					<label for="j_password">${message(code: 'login.password.label', default: 'Password')}</label>
					<input type="password" id="j_password" name='j_password' class="form-control">
				</div>

									
				<button type="submit" class="btn btn-info" id="submit" style="width: auto;">
					                    ${message(code: 'springSecurity.login.button', default: 'Login')}
                </button> 

			</form>

	                       	
			<div class="access-links"> 
				<g:link controller="usuario" action="create">registrar</g:link>
				<g:link controller="user" action="restore_pass">¿olvidaste tu contraseña?</g:link>
				<a href="#"></a>
			</div>      		  	   	
			       
		</div>

</body>
</html>
