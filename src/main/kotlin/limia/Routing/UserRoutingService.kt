package limia.Routing

import limia.Controller.UserController
import limia.Dto.User
import spark.Spark.*


/**
 * Created by macbook on 8/4/17.
 */
class UserRoutingService : RoutingService<User>(), IRoutingService<User> {

    private var userController : UserController? = null

    init {
        userController = UserController()
    }


    override fun initializeRoutes() {

        path("/users") {
            post("") { req, res ->
                userController?.createUser(req);
                res.status(201)
                "User created"
            }

            get("/:id") { req, res ->
                userController?.
            }
        }
    }
}
