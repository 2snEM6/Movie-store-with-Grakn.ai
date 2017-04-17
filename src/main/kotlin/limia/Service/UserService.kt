package limia.Service

import limia.Dao.UserDao
import limia.Dto.User
import limia.Exception.EntityAlreadyExistsException
import limia.Exception.EntityNotFoundException
import limia.Service.IUserService
import java.util.*

/**
 * Created by workstation on 05/04/2017.
 */
class UserService() : IUserService {

    private var dao: UserDao? = null

    @Throws(EntityAlreadyExistsException::class)
    override fun create(name: String, email: String): User {
        val user = User(name, email, UUID.randomUUID().toString())
        try {
            dao!!.existsBy(User::class.java, "email", email)
        } catch(e: EntityNotFoundException) {
            return dao!!.create(user)
        }
        return user
    }

    @Throws(EntityNotFoundException::class)
    override fun read(id: String): User {
        return dao!!.read(id)
    }

    override fun update(user: User): User {
        return dao!!.update(user)
    }

    @Throws(EntityNotFoundException::class)
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
