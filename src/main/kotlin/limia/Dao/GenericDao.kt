package limia.Dao

import limia.Dao.IGenericDao
import limia.Grakn.GraknEntityManager
import java.lang.reflect.ParameterizedType
import java.util.*


/**
 * Created by workstation on 05/04/2017.
 */
abstract class GenericDao<T>() : IGenericDao<T> {

    private var type: Class<T>? = null
    private var graknEntityManager: GraknEntityManager? = null

    override fun create(t: T): T {
        return graknEntityManager!!.persist(t)
    }

    override fun update(t: T): T {
        return graknEntityManager!!.update(t)
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun read(id: Any): T {
        return graknEntityManager!!.read(type, id)
    }

    override fun delete(id: Any) {
        graknEntityManager!!.delete(type, id)
    }

    override fun readAll(type: Class<T>): ArrayList<T> {
        return graknEntityManager!!.readAll(type)
    }

    override fun readAllSpecificRelations(t: T) : ArrayList<T> {
        return graknEntityManager!!.readAllSpecificRelations(t)
    }

    init {
        graknEntityManager = GraknEntityManager()
        val t = javaClass.genericSuperclass
        val pt = t as ParameterizedType
        type = pt.actualTypeArguments[0] as Class<T>
    }


}
