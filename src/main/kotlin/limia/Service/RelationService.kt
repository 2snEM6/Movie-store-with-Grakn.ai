package limia.Service

import limia.Dao.RelationDao
import limia.Dao.UserDao
import limia.Dto.Relation
import limia.Dto.User
import limia.Service.IUserService
import java.util.*

/**
 * Created by workstation on 05/04/2017.
 */
class RelationService() : IRelationService {

    private var dao: RelationDao? = null

    override fun create(firstEntityID: String, secondEntityID: String, firstEntityRole: String,
                        secondEntityRole: String, relationName: String): Relation {
        val relation = Relation(firstEntityID, secondEntityID, relationName, firstEntityRole, secondEntityRole)
        return dao!!.create(relation)
    }

    override fun exists(relationName: String, firstRoleplayerID: String, secondRoleplayerID: String,
                        firstRole: String, secondRole: String) :Boolean {
        return dao!!.existsRelation(relationName, firstRoleplayerID, secondRoleplayerID, firstRole, secondRole)
    }

    override fun readByID(identifier: String): Relation? {
        return dao!!.read(Relation::class, identifier as Any) as Relation?
    }

    override fun readAllOfSpecificType(relationName: String): ArrayList<Relation> {
        return dao!!.readAllSpecificRelations(Relation(null, null, relationName, null, null))
    }

    override fun readAll(): ArrayList<Relation> {
        return dao!!.readAll(Relation::class.java)
    }

    init {
        dao = RelationDao()
    }
}
