package ejemplo.user

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import ejemplo.security.User

@Transactional(readOnly = true)
class UsuarioController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def usuarioService
    def springSecurityService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Usuario.list(params), model:[usuarioCount: Usuario.count()]
    }

    def show() {
        respond Usuario.findByUser(springSecurityService.currentUser)//busco dentro de usuarios al user actual (logueado)
    }

    def create() {
        respond new Usuario(params)
    }

    @Transactional
    def save(Usuario usuario) {

        def usuario_s = usuarioService.save(usuario, new User(params))//asigno el save a una variable

        if (!usuario_s) {  //si la respuesta es falsa le vuelvo a mostrar la vista create
            transactionStatus.setRollbackOnly()
            respond usuario.errors, view: 'create'
            return
        }//sino hubo errores pasa inmediatamente a la vista del login
            request.withFormat {
                form multipartForm {
                    flash.message = message(code: 'default.created.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuario.id])
                    redirect action:'auth',controller:'login'
                }
                '*' { respond usuario, [status: CREATED] }
            }

        
    }

    def edit() {
        respond Usuario.findByUser(springSecurityService.currentUser) //busco dentro de usuarios al user actual (logueado)
    }

    @Transactional
    def update() {

        /**
         * Con en fin de evitar que los usuarios editen el form o intenten alterar el
         * id del usuario hago las verificaciones pertinentes forzando a que el
         * usuario solo introduzca los nuevos valores sin saber su id y asignando
         * este ultimo en el controlador.
         */
        def usuario = new Usuario(params)
        def log_usr = Usuario.findByUser(springSecurityService.currentUser)

        if (usuario == null) { //verifico que no sea nulo
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (usuario.hasErrors()) {//hago la validadion de si es o no correcto el usuario
            transactionStatus.setRollbackOnly()
            respond usuario.errors, view:'edit'
            return
        }

        /**
         * si el usuario paso la validacion y no tiene errores
         * o es nulo asigno los parametros a las propiedades de log_usr
         * para guardarlo
         */

        log_usr.properties = params

        log_usr.save flush:true //guardo

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'usuario.label', default: 'Usuario'), log_usr.id])
                redirect action:"show" //redirecciono a show
            }
            '*'{ respond usuario, [status: OK] }
        }
    }

    @Transactional
    def delete(Usuario usuario) {

        if (usuario == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        usuario.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuario.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
