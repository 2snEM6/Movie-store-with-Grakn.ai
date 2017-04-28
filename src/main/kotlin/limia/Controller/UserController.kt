package limia.Controller

import limia.Dto.User
import limia.Exception.EntityAlreadyExistsException
import limia.Exception.EntityNotFoundException
import limia.Exception.InvalidParametersException
import limia.Service.UserService
import spark.Request
import java.util.*

/**
 * Created by macbook on 8/4/17.
 */
class UserController {

    private val userService: UserService = UserService()

    fun areOnCreateParametersCorrect(request: Request): Boolean {
        return request.queryParams().contains("email") && request.queryParams().contains("name")
    }


    @Throws(EntityAlreadyExistsException::class, InvalidParametersException::class)
    fun createUser(request: Request): User? {
        if (!areOnCreateParametersCorrect(request))
            throw InvalidParametersException()
        val name = request.queryParams("name")
        val email = request.queryParams("email")
        return userService.create(name, email)
    }

    @Throws(EntityNotFoundException::class)
    fun findUser(request: Request): User {
        return userService.read(request.params(":id"))
    }

    @Throws(EntityNotFoundException::class)
    fun deleteUser(request: Request) {
        userService.delete(request.params(":id"))
    }

    fun updateUser(request: Request) {
        val user = User()
        if (request.queryParams().contains("name")) user.name = request.queryParams("name")
        if (request.queryParams().contains("email")) user.email = request.queryParams("email")
        user.identifier = request.params(":id")
        userService.update(user)
    }

    fun readAllUsers(): ArrayList<User> {
        return userService.readAll()
    }

}
