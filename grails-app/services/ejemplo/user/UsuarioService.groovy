package ejemplo.user

import grails.transaction.Transactional
import ejemplo.security.User
import ejemplo.security.Role


@Transactional
class UsuarioService {

    def userService //importo el servicio de user
	def messageSource //para obtener los mensajes de error

	/**
	 * @param usuario
	 * @param user
     * @return
	 *
	 * Guarda un usuario
     */
    def save(usuario,user){

		/**
		 * si usuario no cumple con la validacion se retorna false inmediatamente
		 * si cumple procedo a guardar al user
		 */
    	if(!validate_usuario(usuario)){
    		return false
    	}

    	def user_save 	= userService.save(user,Role.findByAuthority('ROLE_NORMAL'))//findByAuthority la sintaxis es findByNombreDelCampoEnElDominio (convencion sobre configuracion)

		/**
		 * sino se guardo el user
		 * añado los errores de user a la variable de errores de usuario
		 * y retorno false para que se muestren dichos errores en la vista
		 */
    	if(!user_save.response){
    		
    		add_errors(user_save.errors,usuario)
    		
    		return false

    	}

		/**
		 * Si se guardo entonces lo asigno al usuario
		 * lo guardo y retorno true indicando que todo
		 * funciono
		 */

    	usuario.user 	= user_save.user //asigno el user creado al usuario

        usuario.save() //lo guardo

		return true

    }

	/**
	 * Añade los errores de user a los de usuario
	 *
	 * @param u_errors
	 * @param usuario
     * @return
     */
	def add_errors(u_errors, usuario){
		//recorreo los errores
		u_errors.allErrors.collect{ error->

			usuario.errors.reject( //añado el error a usuario
					null,
					[error.getField(), 'class User'] as Object[],//indico el campo y la clase
					messageSource.getMessage(error, null)) //indico el mensaje
		}
	}

	/**
	 * Valida que un usuario no sea null, no contenga errores y sea validado correctamente
	 * @param usuario
	 * @return
     */
    def validate_usuario(Usuario usuario){

        if (usuario == null || usuario.hasErrors() || !usuario.validate()) {//SI el usuario es nulo, tiene errores o no pasa la validacion

            return false
        }

        return true
    }
    
}
