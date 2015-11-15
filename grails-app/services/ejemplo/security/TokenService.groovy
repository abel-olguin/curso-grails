package ejemplo.security

import grails.transaction.Transactional
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor

@Transactional
class TokenService {

    def grailsApplication //Variable global de la aplicacion

    /**
     * guardar un token
     * @param token
     * @return
     */
    def save(Token token) {
        token.save()
    }

    /**
     * Encriptar una cadena
     * @param string
     * @return
     */
    def encrypt(string){
        def jasyptConfig    = grailsApplication.config.jasypt

        def stringEncryptor = new StandardPBEStringEncryptor(jasyptConfig)


        return stringEncryptor.encrypt(string)
    }

    /**
     * desencriptar una cadena
     * @param token
     * @return
     */
    def decrypt(token){

        def jasyptConfig    = grailsApplication.config.jasypt

        def stringEncryptor = new StandardPBEStringEncryptor(jasyptConfig)

        return stringEncryptor.decrypt(token)
    }

    /**
     * Verificar que un token exista, sea de un tipo especifico
     * y que no haya sido usado
     * @param token
     * @param type
     * @return
     */
    def check_token(token,type){
        return Token.findByTokenAndTokenTypeAndTokenStatus(token,type,false)
    }

    /**
     * Busca un token y cambia su status a true
     * @param token
     * @return
     */
    def use_token(token){
        def find_token = Token.findByToken(token)

        find_token.tokenStatus = true

        return find_token.save()
    }

}
