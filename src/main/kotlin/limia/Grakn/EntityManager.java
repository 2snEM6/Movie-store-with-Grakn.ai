package limia.Grakn;

import ai.grakn.GraknGraph;
import ai.grakn.exception.GraknValidationException;
import ai.grakn.graql.QueryBuilder;
import limia.Connection.DBConnection;
import limia.Dto.User;

import java.util.Map;

import static ai.grakn.graql.Graql.var;

/**
 * Created by workstation on 06/04/2017.
 */
public class EntityManager {

    private QueryBuilder queryBuilder;
    private GraknGraph graknGraph;

    public EntityManager() {
        graknGraph = DBConnection.getInstance().getGraph();
        queryBuilder =graknGraph.graql();
    }

    public <T> void persist(T t) {
        DBConnection.getInstance().open();
        if (t instanceof User) {
            queryBuilder.insert(var()
                    .has("name", ((User) t).getName())
                    .has("email", ((User) t).getEmail())
                    .has("identifier", ((User) t).getId())
                    .isa("user"))
                    .execute();
        }
        try {
            graknGraph.commit();
        } catch (GraknValidationException e) {
            System.out.println("Unable to commit changes");
        }
        DBConnection.getInstance().close();
    }

    public <T> void delete(Class<T> type, final Object id) {

    }

    public <T> T read(Class<T> type, final Object id) {
        DBConnection.getInstance().open();
        if (type == User.class) {
            queryBuilder.match(
                    var("user").has("identifier", var("id")),
                    var("id").value(equals(id))

            ).execute().stream().map(Map::entrySet);
        }
    }

    public <T> void update(T t) {

    }
}
