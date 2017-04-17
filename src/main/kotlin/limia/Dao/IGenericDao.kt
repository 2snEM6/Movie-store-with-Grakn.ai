package limia.Dao

import limia.Exception.EntityAlreadyExistsException
import java.util.*

/**
 * Created by workstation on 05/04/2017.
 */
interface IGenericDao<T> {

    fun create(t: T): T?

    fun read(id: Any): T

    fun update(t: T): T

    fun delete(id: Any)

    fun readAll(type: Class<T>): ArrayList<T>

    fun readAllSpecificRelations(t: T): ArrayList<T>

    fun existsBy(type: Class<T>, key: String, value: String)
}
