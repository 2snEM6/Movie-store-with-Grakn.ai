package limia.Routing

import limia.Response.Response
import spark.Spark.*
/**
 * Created by workstation on 16/04/2017.
 */
class HealthRoutingService : RoutingService<Any>(), IRoutingService<Any> {

    override fun initializeRoutes() {
        get("/alive") { req, res ->
            gson.toJson(Response(200,"I am alive", null))
        }
    }


}