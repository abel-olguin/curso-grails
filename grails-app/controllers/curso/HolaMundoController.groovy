package curso

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class HolaMundoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond HolaMundo.list(params), model:[holaMundoCount: HolaMundo.count()]
    }

    def show(HolaMundo holaMundo) {
        respond holaMundo
    }

    def create() {
        respond new HolaMundo(params)
    }

    @Transactional
    def save(HolaMundo holaMundo) {
        if (holaMundo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (holaMundo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond holaMundo.errors, view:'create'
            return
        }

        holaMundo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'holaMundo.label', default: 'HolaMundo'), holaMundo.id])
                redirect holaMundo
            }
            '*' { respond holaMundo, [status: CREATED] }
        }
    }

    def edit(HolaMundo holaMundo) {
        respond holaMundo
    }

    @Transactional
    def update(HolaMundo holaMundo) {
        if (holaMundo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (holaMundo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond holaMundo.errors, view:'edit'
            return
        }

        holaMundo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'holaMundo.label', default: 'HolaMundo'), holaMundo.id])
                redirect holaMundo
            }
            '*'{ respond holaMundo, [status: OK] }
        }
    }

    @Transactional
    def delete(HolaMundo holaMundo) {

        if (holaMundo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        holaMundo.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'holaMundo.label', default: 'HolaMundo'), holaMundo.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'holaMundo.label', default: 'HolaMundo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
