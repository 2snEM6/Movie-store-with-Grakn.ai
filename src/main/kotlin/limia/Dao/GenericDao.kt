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
        return entityManager!!.persist(t)
    }

    override fun update(t: T): T {
        return entityManager!!.update(t)
        //To change body of created functions use File | Settings | File Templates.
    }

    override fun read(id: Any): T {
        return entityManager!!.read(type, id)
    }

    override fun delete(id: Any) {
        entityManager!!.delete(type, id)
    }

    init {
        entityManager = EntityManager()
        val t = javaClass.genericSuperclass
        val pt = t as ParameterizedType
        type = pt.actualTypeArguments[0] as Class<T>
    }


}
