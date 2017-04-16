package limia.Definition;

/**
 * Created by macbook on 9/4/17.
 */
public class GlobalConstants {
    public static class HTTPMessages {
        public static final String CREATE = "created";
        public static final String READ = "read";
        public static final String UPDATE = "updated";
        public static final String DELETE = "deleted";
        public static final String ALREADY_EXISTS = "already exists";
    }

    public static enum CRUD {CREATE, READ, UPDATE, DELETE}
}
