import ejemplo.user.*
import ejemplo.security.*

class BootStrap {

    def init = { servletContext ->
    	if (Role.count()<1){

    		def adminRole 		= new Role('ROLE_ADMIN').save()
    		def normalRole 		= new Role('ROLE_NORMAL').save()
    		def publisherRole 	= new Role('ROLE_PUBLISHER').save()

    		def user1 = new User('admin', 'admin').save()
    		def user2 = new User('normal', 'normal').save()
    		def user3 = new User('publisher', 'publisher').save()

    		UserRole.create user1, adminRole, true
    		UserRole.create user2, normalRole, true
    		UserRole.create user3, publisherRole, true


			def usuario1 = new Usuario(nombre: 				'Abel', 
										primerApellido: 	'Olguin', 	
										segundoApellido: 	'Chavez',
										edad: 				20,
										sexo: 				'masculino',
										casado: 			false,
										email: 				'osmer19@hotmail.com',
										user: 				user1).save()

			def usuario2 = new Usuario(nombre: 				'Pedro', 
										primerApellido: 	'Perez', 	
										segundoApellido: 	'Perez',
										edad: 				20,
										sexo: 				'masculino',
										email: 				'pedro@perez.com',
										casado: 			false,
										user: 				user2).save()

			def usuario3 = new Usuario(nombre: 				'Liliana', 
										primerApellido: 	'Perez', 	
										segundoApellido: 	'bla',
										edad: 				20,
										sexo: 				'femenino',
										email: 				'liliana@seeyousoon.com',
										casado: 			false,
										user: 				user3).save()

		}
    }
    def destroy = {
    }
}
