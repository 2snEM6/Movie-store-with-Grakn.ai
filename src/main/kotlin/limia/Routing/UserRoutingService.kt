package limia.Routing

import com.google.gson.Gson
import limia.Controller.UserController
import limia.Dto.User
import limia.Response.Response
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
                var user = userController?.createUser(req)
                res.status(201)
                gson.toJson(Response(201, "User created",user))
            }

            get("/:id") { req, res ->
                var user = userController?.findUser(req)
                gson.toJson(Response(201,null,user))
            }

            /*get("/:id") { req, res ->
                userController?.
            }*/
        }
    }
}
