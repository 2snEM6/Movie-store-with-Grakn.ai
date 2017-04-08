package limia.Service

import limia.Dto.Relation
import limia.Dto.User

/**
 * Created by workstation on 05/04/2017.
 */
interface IRelationService {

    fun create(firstEntityID: String, secondEntityID: String, firstEntityRole: String, secondEntityRole: String,
               relationName: String): Relation
}
