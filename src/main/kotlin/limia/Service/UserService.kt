package limia.Service

import limia.Dao.UserDao
import limia.Dto.User
import limia.Service.IUserService
import java.util.*

/**
 * Created by workstation on 05/04/2017.
 */
class UserService() : IUserService {

    private var dao: UserDao? = null

    override fun create(name: String, email: String): User {
        val user = User(name, email, UUID.randomUUID().toString())
        return dao!!.create(user)
    }

    override fun read(id: String): User {
        return dao!!.read(id)
    }

    override fun update(user: User): User {
        return dao!!.update(user)
    }

    override fun delete(id: String) {
        dao!!.delete(id)
    }

    override fun readAll(): ArrayList<User> {
        return dao!!.readAll(User::class.java)
    }

    init {
        dao = UserDao()
    }
}
