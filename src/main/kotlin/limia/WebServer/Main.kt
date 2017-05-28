package limia.WebServer

import limia.Connection.DBConnection
import limia.Routing.HealthRoutingService
import limia.Routing.MovieRoutingService
import limia.Routing.RelationRoutingService
import limia.Routing.UserRoutingService

/**
 * Created by workstation on 07/04/2017.
 */
object Main {

    @JvmStatic fun main(args: Array<String>) {
        DBConnection.getInstance().load()
        WebServerManager.start(4568)
        WebServerManager.registerRoutingService(UserRoutingService())
        WebServerManager.registerRoutingService(RelationRoutingService())
        WebServerManager.registerRoutingService(MovieRoutingService())
        WebServerManager.registerRoutingService(HealthRoutingService())
        WebServerManager.enableGlobalFilters()
    }



}
