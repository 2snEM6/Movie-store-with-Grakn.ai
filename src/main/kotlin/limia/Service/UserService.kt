package limia.Service

import limia.Dao.UserDao
import limia.Service.IUserService
import limia.User

import java.util.UUID

/**
 * Created by workstation on 05/04/2017.
 */
class UserService : IUserService {

    private val dao: UserDao? = null

    override fun create(name: String, email: String) {
        val user = User(name, email, UUID.randomUUID().toString())
        dao!!.create(user)
    }

    override fun read(id: String): User {
        return dao!!.read(id)
    }

    override fun update(user: User) {
        dao!!.update(user)
    }

    override fun delete(id: String) {
        dao!!.delete(id)
    }
}
