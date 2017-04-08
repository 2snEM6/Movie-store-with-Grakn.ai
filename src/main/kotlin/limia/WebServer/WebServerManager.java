package limia.WebServer;

import limia.Routing.RoutingService;

import static spark.Spark.awaitInitialization;
import static spark.Spark.port;

/**
 * Created by macbook on 8/4/17.
 */
public class WebServerManager {

    public static void start(int port) {
        port(port);
        System.out.println(String.format("Starting web server on port %s",port));
    }

    public static void registerRoutes(RoutingService routingService) {
        routingService.initializeRoutes();
    }
}
