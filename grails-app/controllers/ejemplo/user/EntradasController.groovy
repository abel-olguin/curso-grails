package ejemplo.user

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class EntradasController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Entradas.list(params), model:[entradasCount: Entradas.count()]
    }

    def show(Entradas entradas) {
        respond entradas
    }

    def create() {
        respond new Entradas(params)
    }

    @Transactional
    def save(Entradas entradas) {
        if (entradas == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (entradas.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond entradas.errors, view:'create'
            return
        }

        entradas.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'entradas.label', default: 'Entradas'), entradas.id])
                redirect entradas
            }
            '*' { respond entradas, [status: CREATED] }
        }
    }

    def edit(Entradas entradas) {
        respond entradas
    }

    @Transactional
    def update(Entradas entradas) {
        if (entradas == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (entradas.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond entradas.errors, view:'edit'
            return
        }

        entradas.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'entradas.label', default: 'Entradas'), entradas.id])
                redirect entradas
            }
            '*'{ respond entradas, [status: OK] }
        }
    }

    @Transactional
    def delete(Entradas entradas) {

        if (entradas == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        entradas.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'entradas.label', default: 'Entradas'), entradas.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'entradas.label', default: 'Entradas'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
