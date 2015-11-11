package ejemplo.user
import ejemplo.security.User
class Usuario {

	String nombre
	String primerApellido
	String segundoApellido
	Integer edad
	String sexo
    String email
	Boolean casado
	
    static belongsTo = [user: User]

    static constraints = {
        user nullable:true
    	nombre()
        email email:true, unique: true
    	primerApellido()
    	segundoApellido nullable:true
    	edad min:18, max:60
    	sexo inList: ['masculino', 'femenino']
    	casado()
    	
    }
}
