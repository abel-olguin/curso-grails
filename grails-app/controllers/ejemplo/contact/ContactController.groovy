package ejemplo.contact

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ContactController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def contactService

    /**
     * Muestra la vista de create
     * @return
     */
    def index() {
        respond new Contact(params),view:'create'
    }

    @Transactional
    def save(Contact contact) {

        def contact_s = contactService.save(contact)//asigno el save a una variable

        if (!contact_s) {  //si la respuesta es falsa le vuelvo a mostrar la vista create
            transactionStatus.setRollbackOnly()
            respond contact?.errors, view: 'create'
            return
        }//sino hubo errores pasa inmediatamente a la vista del login

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'contact.label', default: 'Contact'), contact.id])
                redirect uri:'/'
            }
            '*' { respond contact, [status: CREATED] }
        }
    }


    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'contact.label', default: 'Contact'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
