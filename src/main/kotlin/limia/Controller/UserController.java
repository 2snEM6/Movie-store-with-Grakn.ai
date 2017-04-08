package limia.Controller;

import limia.Dto.User;
import limia.Service.UserService;
import spark.Request;

/**
 * Created by macbook on 8/4/17.
 */
public class UserController {

    private UserService userService;

    public User createUser(Request request) {
        String name = request.queryParams("name");
        String email = request.queryParams("email");
        return userService.create(name, email);
    }

    /*public static Response updateUser(Request request) {

    }*/

    public User findUser(Request request) {
        return userService.read(request.params(":id"));
    }

    /*public static Response deleteUser(Request request) {

    }*/

    public UserController() {
        userService = new UserService();
    }
}
