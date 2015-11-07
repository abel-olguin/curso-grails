package ejemplo.user

import grails.transaction.Transactional
import ejemplo.security.User
import ejemplo.security.Role

import grails.validation.ValidationException

@Transactional
class UsuarioService {
    def userService
	def messageSource

    def save(usuario,user){

    	if(!validateObj(usuario)){
    		return false
    	}

    	def userSave 	= userService.save(user,Role.findByAuthority('ROLE_NORMAL'))
    	
    	if(!userSave.response){
    		
    		userSave.errors.allErrors.collect{ error->
    			usuario.errors.reject(
			        null,
			        [error.getField(), 'class User'] as Object[],
			        messageSource.getMessage(error, null))
    		}
    		
    		return false
    	}

    	usuario.user 	= userSave.user

        usuario.save()        

    }

    def validateObj(Usuario usuario){

        if (usuario == null || usuario.hasErrors() || !usuario.validate()) {

            return false
        }

        return true
    }
    
}
