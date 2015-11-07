package ejemplo.security

import grails.transaction.Transactional


@Transactional
class UserService {

    /**
     * Guarda un user y le asigna un rol
     * @param user
     * @param role
     * @return
     */
    def save(user,role) {

        /**
         * Si el usuario no cumple con la validacion retorno
         * los errores y una respuesta indicando que algo fallo
         */
        if(!validate_user(user)){
    		return [response:false,errors:user.errors]
    	}

        /**
         * Si no tiene errores guardo el user
         * le asigno un rol (creo un nuevo campo en base de datos con el id del user y el de el rol correspondiente)
         * retorno el user y una respuesta correcta indicando el exito de la operacion
         */
        user.save()
    	def userRole = new UserRole(user:user,role:role)

        userRole.save flush:true

        return [response:true,user:user]
    }

    /**
     * Valido que el user no sea null, no tenga errores
     * y cumpla con la validacion
     * @param user
     * @return
     */
    def validate_user(User user){

        if (user == null || user.hasErrors() || !user.validate()) {

            return false
        }

        return true
    }
}
