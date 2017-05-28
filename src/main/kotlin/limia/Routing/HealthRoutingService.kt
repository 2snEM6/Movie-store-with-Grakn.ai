package limia.Routing

import limia.Response.SuccessResponse
import spark.Spark.*
/**
 * Created by workstation on 16/04/2017.
 */
class HealthRoutingService : RoutingService<Any>(), IRoutingService<Any> {

    override fun initializeRoutes() {
        get("/alive") { _, _ ->
            gson.toJson(SuccessResponse(200,"I am alive", null))
        }
    }


}