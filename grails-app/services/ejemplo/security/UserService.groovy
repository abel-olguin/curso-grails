package ejemplo.security

import grails.transaction.Transactional
import org.apache.commons.validator.routines.EmailValidator

import ejemplo.user.Usuario

@Transactional
class UserService {
    def mailService
    def tokenService

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

    /******************************************************
     *               Restablecer contraseña               *
     *                                                    *
     ******************************************************/

    /**
     * Valida un email si existe en base de datos
     * y si es un email valido
     * @param email
     * @return
     */
    def validate_email(email){
        def valid_email = EmailValidator.getInstance().isValid(email)
        def exist_email = Usuario.findByEmail(email)

        return [valid:valid_email, exist:exist_email]
    }

    /**
     * Crea un token encriptando el email
     * lo guarda en base de datos y lo
     * envia como email al correo indicado
     * @param email
     * @return
     */
    def send_email(email){

        def token = encrypt_email(email)//encriptar

        create_token(token)//guardar el token en db

        mailService.sendMail { //enviar el email
            to email
            subject 'Recuperar contraseña'
            html    (view:'/emails/restore_pass', //vista
                     model:[token:token])

        }
    }

    /**
     * Hace el update del password
     * @param params
     * @return
     */
    def update_pass(params){

        def valid = password_confirm(params.password,params.passwordConfirm)//valido que ambos pass sean iguales

        if(valid.valid && valid.match){ //si es valido y coinciden

            def email = decrypt_email(params.token)//desencripto el email

            use_token(params.token)//uso el token (status = true)

            def user  = User.get(Usuario.findByEmail(email).id)//busco al user a travez del email

            user.password = params.password //cambio su contraseña

            return valid << [response:user.save()] //retorno un mapa indicando si se guardo correctamente
        }

        return valid << [response:false]//retorno false y el error que se encontro
    }

    /**
     * verifica que un token exista y sea de tipo restore, ademas que no este usado
     */
    def check_token(token){
        return tokenService.check_token(token,'restore')
    }

    /**
     * crea un token de tipo restore
     */
    def private create_token(token){
        return tokenService.save(new Token(token:token,tokenType:'restore',tokenStatus:false))
    }

    /**
     * encripta un email
     */
    def private encrypt_email(email){
        return tokenService.encrypt(email)
    }

    /**
     * desencripta un email
     */
    def private decrypt_email(token){
        return tokenService.decrypt(token)
    }

    /**
     * verificar que un password sea correcto y ademas
     * coincida con el parametro confirm
     * @param password
     * @param passwordConfirmation
     * @return
     */
    def private password_confirm(String password, String passwordConfirmation) {

        //String pattern = "([a-zA-Z0-9])" //((?=.*[0-9])(?=.*[a-zA-Z]).{8,})//en caso de ser mas robustos usar regex

        return [valid:password.length()>0,match: password.equals(passwordConfirmation)]

    }

    /**
     * usa un token (status a true)
     * @param token
     * @return
     */
    def private use_token(token){
        return tokenService.use_token(token)
    }
}
