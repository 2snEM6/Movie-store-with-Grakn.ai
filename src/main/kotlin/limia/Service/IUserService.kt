package limia.Service

import limia.Dto.User
import java.util.*

/**
 * Created by workstation on 05/04/2017.
 */
interface IUserService {
    fun create(name: String, email: String): User

    fun read(id: String): User

    fun update(user: User): User

    fun delete(id: String)

    fun readAll(): ArrayList<User>
}
