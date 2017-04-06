package limia.Connection;

/**
 * Created by workstation on 06/04/2017.
 */
public class Connection {
    public static Connection instance; // Singleton pattern

    private Connection(){
        // DB Driver and connect to the DB
    }

    public synchronized static Connection getInstance() {
        if (instance == null) {
            return new Connection();
        }
        return instance;
    }

    public void open() {
        // Open connection
    }

    public void close() {
        // Close connection
    }
}
