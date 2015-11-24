package ejemplo.contact

import grails.transaction.Transactional

@Transactional
class ContactService {

    def mailService
    def grailsApplication

    /**
     * Guarda un contacto en base de datos, envia un email al administrador
     * con los datos del usuario
     * @param contact
     * @return
     */
    def save(Contact contact) {
        if(validate_contact(contact)){//validar
            contact.save(flush:true)//guardar el contacto
            send_contact(contact) //enviar el email
            return true
        }

        return false;
    }

    /**
     * Valida que un contacto no sea null, no contenga errores y sea validado correctamente
     * @param contact
     * @return
     */
    def validate_contact(Contact contact){

        if (contact == null || contact.hasErrors() || !contact.validate()) {//SI el usuario es nulo, tiene errores o no pasa la validacion

            return false
        }

        return true
    }

    /**
     * Envia un email de contacto
     * indica los datos de usuario y el mensaje
     * @param contact
     * @return
     */
    def send_contact(contact){
        def properties = grailsApplication.config.ejemplo //propiedad de application.yml

        mailService.sendMail { //enviar el email
            to      properties.email //para
            from    contact.email //de (esto no siempre funciona)
            subject contact.name+' '+contact.subject+' '+contact.email//asunto
            text    contact.message //mensaje

        }
    }
}
