package limia

import limia.Connection.Connection
import limia.Dto.User
import limia.Service.UserService

/**
 * Created by workstation on 07/04/2017.
 */
object Main {

    @JvmStatic fun main(args: Array<String>) {
        Connection.getInstance().open()
        val userService : UserService = UserService()
        userService.create("Daniel Limia Aspas", "limiaspasdaniel@gmail.com")
    }

}
