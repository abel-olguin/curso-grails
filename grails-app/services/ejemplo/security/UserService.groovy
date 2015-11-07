package ejemplo.security

import grails.transaction.Transactional

import grails.validation.ValidationException

@Transactional
class UserService {

    def save(user,role) {

    	if(!validateObj(user)){
    		return [response:false,errors:user.errors]
    	}

    	user.save()
    	def userRole = new UserRole(user:user,role:role)

        userRole.save flush:true

        return [response:true,user:user]
    }

    def validateObj(User user){

        if (user == null || user.hasErrors() || !user.validate()) {

            return false
        }

        return true
    }
}
