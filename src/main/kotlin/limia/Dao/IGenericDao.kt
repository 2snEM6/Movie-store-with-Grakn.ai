package limia.Dao

import limia.Exception.EntityAlreadyExistsException
import limia.Exception.EntityNotFoundException
import java.util.*
import kotlin.reflect.KClass

/**
 * Created by workstation on 05/04/2017.
 */
interface IGenericDao<T : Any> {

    fun create(t: T): T?
    
    fun update(t: T): T

    fun readAll(type: Class<T>): ArrayList<T>

    fun readAllSpecificRelations(t: T): ArrayList<T>

    fun <T : Any> read(type: KClass<T>, id: Any): Any?
    fun <T : Any> delete(type: KClass<T>, id: Any)
    fun <T : Any> existsBy(type: KClass<T>, key: String, value: String)
}
