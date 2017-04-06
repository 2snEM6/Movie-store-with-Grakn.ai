package limia.Dao

import limia.Dao.IGenericDao
import limia.Grakn.EntityManager
import java.lang.reflect.ParameterizedType



/**
 * Created by workstation on 05/04/2017.
 */
abstract class GenericDao<T>() : IGenericDao<T> {

    private var type: Class<T>? = null
    private var entityManager: EntityManager? = null

    override fun create(t: T): T {
        entityManager!!.persist(t)
        return t
    }

    override fun update(t: T): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun read(id: Any): T {
        TODO("not implemented")
    }

    override fun delete(id: Any) {
        TODO("not implemented")
    }

    init {
        entityManager = EntityManager()
        val t = javaClass.genericSuperclass
        val pt = t as ParameterizedType
        type = pt.actualTypeArguments[0] as Class<T>
    }


}
