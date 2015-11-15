package ejemplo.security

class Token {

    String token
    String tokenType
    Boolean tokenStatus

    static constraints = {
        token()
        tokenType inList: ['restore','other']
        tokenStatus defaultValue:false
    }
}
