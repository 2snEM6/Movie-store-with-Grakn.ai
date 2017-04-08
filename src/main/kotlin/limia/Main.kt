package limia

import limia.Connection.DBConnection
import limia.Dto.User
import limia.Service.UserService
import limia.WebServer.WebServerManager
import limia.Routing.UserRoutingService

/**
 * Created by workstation on 07/04/2017.
 */
object Main {

    @JvmStatic fun main(args: Array<String>) {
        DBConnection.getInstance().load()
        WebServerManager.start(4568)
        WebServerManager.registerRoutes(UserRoutingService())
    }



}
