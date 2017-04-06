package limia.Service

import limia.User

/**
 * Created by workstation on 05/04/2017.
 */
interface IUserService {
    fun create(name: String, email: String)

    fun read(id: String): User

    fun update(user: User)

    fun delete(id: String)
}
