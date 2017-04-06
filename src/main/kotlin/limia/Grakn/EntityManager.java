package limia.Grakn;

import ai.grakn.graql.QueryBuilder;
import limia.Connection.Connection;
import limia.Dto.User;

import static ai.grakn.graql.Graql.var;

/**
 * Created by workstation on 06/04/2017.
 */
public class EntityManager {

    private QueryBuilder queryBuilder;

    public EntityManager() {
        queryBuilder = Connection.getInstance().getGraph().graql();
    }

    public <T> void persist(T t) {
        if (t instanceof User) {
            queryBuilder
                    .insert(var()
                            .isa("user")
                            .has("name", ((User) t).getName())
                            .has("email", ((User) t).getEmail())
                            .has("id", ((User) t).getId())
                    );
        }
    }

    public <T> void delete(Class<T> type, final Object id) {

    }

    public <T> T read(Class<T> type, final Object id) {
        return null; // Todo
    }

    public <T> void update(T t) {

    }
}
