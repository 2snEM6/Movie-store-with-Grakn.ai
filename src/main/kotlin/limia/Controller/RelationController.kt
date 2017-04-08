package limia.Controller

import limia.Dto.Relation
import limia.Dto.User
import limia.Service.RelationService
import limia.Service.UserService
import spark.Request

/**
 * Created by macbook on 8/4/17.
 */
class RelationController {

    private val relationService: RelationService = RelationService()

    fun createRelation(request: Request): Relation? {
        val relationName = request.queryParams("relation")
        val firstSplat = request.splat()[0]
        val secondSplat = request.splat()[1]

        if (firstSplat.equals("users") && secondSplat.equals("movies")) {
            if (relationName.equals("download")) {
                return relationService.create(request.params(":id0"), request.params(":id1"),
                        "downloaded_a", "is_downloaded_by", relationName)
            }
        }
        return null
    }

    /*public static Response updateUser(Request request) {

    }*/

    fun findRelation(request: Request): Relation {
        TODO("Not yet implemented")
    }

    fun deleteRelation(request: Request) {
        TODO("Not yet implemented")
    }

    fun updateRelation(request: Request) {
        TODO("Not yet implemented")
    }

}
