package ejemplo.contact


class Contact{

    String name
    String email
    String subject
    String message

    static constraints = {
        name blank: false, minSize: 6
        email blank: false, email:true
        subject()
        message blank: false, minSize: 20
    }
}