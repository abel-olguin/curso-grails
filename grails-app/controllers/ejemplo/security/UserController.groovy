package ejemplo.security

import grails.converters.JSON

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UserController {

    def userService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    /************* restore pass *********/

    /**
     * Renderea la vista de restablecer contrasela
     * donde solo aparece el correo
     * @return
     */
    def restore_pass(){
        render view: '/restore/restore_pass'
    }

    /**
     * envio del email
     * @return
     */
    @Transactional(readOnly = false)
    def send_email(){
        def errors = []
        /**
         * Valido que el email exista y a demas sea un email valido
         */

        def valid_email = userService.validate_email(params.email)//Validación del email

        if(valid_email.valid && valid_email.exist){//si es valido y existe

            userService.send_email(params.email)//envio el email

            redirect action:'auth', controller:'login'//redirijo al login

            return

        }else{//sino es valido añado el error encontrado
            errors?.add(!valid_email.valid?"user.email.invalid.label":null)
            errors?.add(!valid_email.exist?"user.email.exist.label":null)
        }

        render view:'/restore/restore_pass', model:[errors:errors]//rendereo la misma vista indicando los errores
    }

    /**
     * Si el token existe, es del tipo correcto y no ha sido usado
     * muestro la vista de cambio de contraseña
     * sino muestro el error 404
     * @return
     */
    def change_pass(String token){
        if(userService.check_token(token)){
            render view: '/restore/change_pass'
        }else{
            render view: '/errors/404'
        }
    }

    /**
     * Verifico que el token exista y sea del tipo correcto
     * que no haya sido usado, si cumple hago el update del password
     * que consta de validar primero el passwor con el campo confirm
     * si todo es correcto redirijo al login
     * @return
     */
    @Transactional(readOnly = false)
    def update_pass(){

        def errors = []
        if(userService.check_token(params.token)){//verifico la integridad del token

            def update_user = userService.update_pass(params)//trato de hacer el update del password (hay validacion)

            if(update_user.response){ //si la respuesta de update_pass es true
                redirect action:'auth', controller:'login'//redirigir a login
                return
            }

            errors?.add(update_user.valid?"user.password.error":null)//sino se hizo el update añado los errores que se encuentren (Valid y match)
            errors?.add(update_user.match?"user.password.confirm.error":null)

        }else{//si trataron de alterar el token
            errors.add('user.invalid.token')//añado un error de token invalido
        }
        render view: '/restore/change_pass', model:['errors': errors]//rendereo la vista nuevamente con los errores que encuentre
    }



}
