package limia.Dao

import limia.Dao.IGenericDao
import limia.Dto.Entity
import limia.Exception.EntityAlreadyExistsException
import limia.Exception.EntityNotFoundException
import limia.Grakn.GraknEntityManager
import java.lang.reflect.ParameterizedType
import java.util.*


/**
 * Created by workstation on 05/04/2017.
 */
abstract class GenericDao<T>() : IGenericDao<T> {

    private var type: Class<T>? = null
    private var graknEntityManager: GraknEntityManager? = null

    @Throws(EntityAlreadyExistsException::class)
    override fun create(t: T): T {
        val e = t as Entity
        if (graknEntityManager!!.exists(t.javaClass, e.identifier))
            throw EntityAlreadyExistsException()
        else
            return graknEntityManager!!.persist(t)
    }

    @Throws(Exception::class)
    override fun existsBy(type: Class<T>, key :String, value: String)  {
        val entity = graknEntityManager!!.findBy(type, key, value)
        if (entity != null)
            throw EntityAlreadyExistsException()
        else
            throw EntityNotFoundException()
    }


    override fun update(t: T): T {
        return graknEntityManager!!.update(t)
        //To change body of created functions use File | Settings | File Templates.
    }

    @Throws(EntityNotFoundException::class)
    override fun read(id: Any): T {
        if (!graknEntityManager!!.exists(Entity::class.java, id))
            throw EntityNotFoundException()
        return graknEntityManager!!.read(type, id)
    }

    @Throws(EntityNotFoundException::class)
    override fun delete(id: Any) {
        if (!graknEntityManager!!.exists(Entity::class.java, id))
            throw EntityNotFoundException()
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
