package limia.Controller;

import limia.Dao.UserDao;
import limia.Dto.User;
import limia.Service.UserService;
import spark.Request;
import spark.Response;

/**
 * Created by macbook on 8/4/17.
 */
public class UserController {

    private UserService userService;

    public void createUser(Request request) {
        String name = request.queryParams("name");
        String email = request.queryParams("email");
        userService.create(name, email);
    }

    /*public static Response updateUser(Request request) {

    }

    public static Response findUser(Request request) {

    }

    public static Response deleteUser(Request request) {

    }*/

    public UserController() {
        userService = new UserService();
    }
}
