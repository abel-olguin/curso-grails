<html>
<head>
	
	<title><g:message code="springSecurity.login.title" default="Ingresar" /></title>
	
</head>

<body>

    	<div class="container" id="login-block">
    		
			<form action='${postUrl}' method='POST' id='loginForm'  autocomplete='off'>
								
									
				<input type='text'  name='j_username' id='username' placeholder=${message(code: 'placeholder.login.user.label', default: 'User')} />
								
									
				<input type='password' name='j_password' id='password' placeholder=${message(code: 'placeholder.login.password.label', default: 'Password')} />

				<button type="submit" class="btn btn-green" id="submit" style="width: auto;">
					                    ${message(code: 'springSecurity.login.button', default: 'Login')}
                </button> 

			</form>

	                       	
			<div class="access-links"> 
				<g:link controller="usuario" action="create">registrar</g:link>
				<a href="#">¿olvidaste tu contraseña?</a>
			</div>      		  	   	
			       
		</div>
      	
     	<footer class="footer">
     		<p id="footer-text">
     			<small>curso grails by abel olguin</small>
     		</p>       		
     	</footer>

</body>
</html>
