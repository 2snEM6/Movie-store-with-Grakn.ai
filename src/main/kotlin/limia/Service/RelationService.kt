package limia.Service

import limia.Dao.RelationDao
import limia.Dao.UserDao
import limia.Dto.Relation
import limia.Dto.User
import limia.Service.IUserService

import java.util.UUID

/**
 * Created by workstation on 05/04/2017.
 */
class RelationService() : IRelationService {

    private var dao: RelationDao? = null

    override fun create(firstEntityID: String, secondEntityID: String, firstEntityRole: String,
                        secondEntityRole: String, relationName: String): Relation {
        val relation = Relation(firstEntityID,secondEntityID,relationName,firstEntityRole,secondEntityRole)
        return dao!!.create(relation)
    }

    init {
        dao = RelationDao()
    }
}
