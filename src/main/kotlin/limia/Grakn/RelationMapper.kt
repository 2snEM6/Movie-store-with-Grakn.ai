package limia.Grakn

import ai.grakn.concept.Relation
import java.lang.reflect.Field
import java.util.*

/**
 * Created by workstation on 20/05/2017.
 */
class RelationMapper {


    fun getField(clazz: Class<*>?, name: String): Field? {
        var type = clazz
        var field: Field? = null
        while (type != null && field == null) {
            try {
                field = type.getDeclaredField(name)
            } catch (e: Exception) {
            }

            type = type.superclass
        }
        return field
    }

    fun fromRelation(relation: Relation): limia.Dto.Relation? {

        val roleTypeNames = ArrayList<String>()
        val roleTypeIDs = ArrayList<String>()

        val _relation = limia.Dto.Relation()
        _relation.relationName = relation.type().name.value
        val map = relation.rolePlayers()

        map.forEach { entry ->
            roleTypeNames.add(entry.key.name.value)
            entry.value.asEntity().resources().forEach { resource ->
                if (resource.type().name.value == "identifier") {
                    roleTypeIDs.add(resource.value.toString())
                }
            }
        }

        _relation.firstRole = roleTypeNames[0]
        _relation.secondRole = roleTypeNames[1]
        _relation.firstEntityID = roleTypeIDs[0]
        _relation.secondEntityID = roleTypeIDs[1]
        _relation.identifier = relation.id.value

        return _relation

    }



}
