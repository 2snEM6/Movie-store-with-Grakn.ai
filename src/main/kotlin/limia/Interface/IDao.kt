package limia.Interface

/**
 * Created by workstation on 05/04/2017.
 */
interface IDao<T> {

    fun create(t: T): T

    fun read(id: Any): T

    fun update(t: T): T

    fun delete(id: Any)
}
