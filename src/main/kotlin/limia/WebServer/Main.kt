package limia.WebServer

import limia.Connection.DBConnection
import limia.Routing.HealthRoutingService
import limia.Routing.MovieRoutingService
import limia.Routing.RelationRoutingService
import limia.Service.UserService
import limia.WebServer.WebServerManager
import limia.Routing.UserRoutingService
import limia.Service.RelationService

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
        WebServerManager.enableGlobalFilters();
    }



}
