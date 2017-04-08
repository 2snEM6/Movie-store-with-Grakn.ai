package limia.Grakn;

import ai.grakn.GraknGraph;
import ai.grakn.concept.Entity;
import ai.grakn.exception.GraknValidationException;
import ai.grakn.graql.InsertQuery;
import ai.grakn.graql.MatchQuery;
import ai.grakn.graql.QueryBuilder;
import limia.Connection.DBConnection;
import limia.Dto.User;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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

    public <T> T persist(T t) {
        DBConnection.getInstance().open();
        if (t instanceof User) {
            InsertQuery insertQuery = queryBuilder.insert(var()
                    .has("name", ((User) t).getName())
                    .has("email", ((User) t).getEmail())
                    .has("identifier", ((User) t).getIdentifier())
                    .isa("user"));
            insertQuery.execute();
        }
        try {
            graknGraph.commit();
        } catch (GraknValidationException e) {
            System.out.println("Unable to commit changes");
        }
        DBConnection.getInstance().close();
        return t;
    }

    public <T> void delete(Class<T> type, final Object id) {

    }

   public <T> T read(Class<T> type, final Object id) {
        DBConnection.getInstance().open();
        if (type == User.class) {
            EntityMapper entityMapper = new EntityMapper<>(User.class);
            MatchQuery query = queryBuilder.match(
                    var("user").has("identifier", var("id")),
                    var("id").value(id)
            );
            final boolean[] first = {true};
            final User[] user = new User[1];
            query.forEach(k -> {
                Entity entity = k.get("user").asEntity();
                if (first[0])
                    user[0] = (User) entityMapper.fromEntity(entity);
                    first[0] = false;
            });
            return (T) user[0];
        }
        return (T) new Object();
    }

    public <T> void update(T t) {

    }
}
