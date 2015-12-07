package ejemplo.user

class Entradas {

    String title
    String body
    Date created

    static constraints = {
        title()
        body()
        created()
    }
}
