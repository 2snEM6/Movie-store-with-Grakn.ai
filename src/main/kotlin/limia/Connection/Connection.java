package limia.Connection;

import ai.grakn.Grakn;
import ai.grakn.GraknGraph;

/**
 * Created by workstation on 06/04/2017.
 */
public class Connection {
    private static Connection instance; // Singleton pattern
    private GraknGraph graph;

    private Connection(){
        // DB Driver and connect to the DB
    }

    public synchronized static Connection getInstance() {
        if (instance == null) {
            instance = new Connection();
            return instance;
        }
        return instance;
    }

    public void open() {
        graph = Grakn.factory(Grakn.IN_MEMORY, "moviestore").getGraph();
    }

    public void close() {
        graph = null;
    }

    public GraknGraph getGraph() {
        return graph;
    }
}
