package limia.Connection;

import ai.grakn.Grakn;
import ai.grakn.GraknGraph;
import ai.grakn.client.Client;


/**
 * Created by workstation on 06/04/2017.
 */
public class DBConnection {
    private static DBConnection instance; // Singleton pattern
    private GraknGraph graph;

    private DBConnection(){
        // DB Driver and connect to the DB
    }

    public synchronized static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
            return instance;
        }
        return instance;
    }

    public boolean isServerRunning() {
        return Client.serverIsRunning(Grakn.DEFAULT_URI);
    }

    public void load() {
        do {
            System.out.println("Please start Grakn Engine");
            System.out.println("You can get more information on how to do so using our setup guide: https://grakn.ai/pages/documentation/get-started/setup-guide.html");

        } while (!isServerRunning());
        graph = Grakn.factory(Grakn.DEFAULT_URI, "moviestore").getGraph();
    }

    public void open() {
        graph.open();
    }

    public void close() {
        graph.close();
    }

    public GraknGraph getGraph() {
        return graph;
    }
}
