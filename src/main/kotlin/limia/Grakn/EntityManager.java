package limia.Grakn;

import ai.grakn.GraknGraph;
import ai.grakn.concept.Entity;
import ai.grakn.exception.GraknValidationException;
import ai.grakn.graql.InsertQuery;
import ai.grakn.graql.MatchQuery;
import ai.grakn.graql.QueryBuilder;
import ai.grakn.graql.Var;
import limia.Connection.DBConnection;
import limia.Dto.Movie;
import limia.Dto.Relation;
import limia.Dto.User;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static ai.grakn.graql.Graql.match;
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
        if (t instanceof Movie) {
            try {
                InsertQuery insertQuery = queryBuilder.insert(var()
                        .has("themoviedb_id", ((Movie) t).getThemoviedb_id())
                        .has("identifier", ((Movie) t).getIdentifier())
                        .isa("movie"));
                insertQuery.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (t instanceof Relation) {
            MatchQuery matchQuery = queryBuilder.match(var("first_entity")
                    .has("identifier", ((Relation) t).getFirstEntityID()),
                    var("second_entity")
                    .has("identifier", ((Relation) t).getSecondEntityID()));
            matchQuery.insert(var()
                    .rel(((Relation) t).getFirstRole(), "first_entity")
                    .rel(((Relation) t).getSecondRole(), "second_entity")
                    .isa(((Relation) t).getRelationName())).execute();
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
        DBConnection.getInstance().open();
        String entityName = null;
        if (type == User.class) {
            entityName = "user";
            queryBuilder.match(var(entityName).has("identifier", id)).delete(entityName).execute();
        }
        if (type == Movie.class) {
            entityName = "movie";
            queryBuilder.match(var(entityName).has("identifier", id)).delete(entityName).execute();
        }
        try {
            graknGraph.commit();
        } catch (GraknValidationException e) {
            System.out.println("Unable to commit");
        }
        DBConnection.getInstance().close();
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
       if (type == Movie.class) {
           EntityMapper entityMapper = new EntityMapper<>(Movie.class);
           MatchQuery query = queryBuilder.match(
                   var("movie").has("identifier", var("id")),
                   var("id").value(id)
           );
           final boolean[] first = {true};
           final Movie[] movie = new Movie[1];
           query.forEach(k -> {
               Entity entity = k.get("movie").asEntity();
               if (first[0])
                   movie[0] = (Movie) entityMapper.fromEntity(entity);
               first[0] = false;
           });
           return (T) movie[0];
       }
        DBConnection.getInstance().close();
        return null;
    }

    public <T> T update(T t) {
        DBConnection.getInstance().open();
        String id = null;
        if (t instanceof User) {
            id = ((User) t).getIdentifier();
            MatchQuery matchQuery = queryBuilder.match(var("user").has("identifier", ((User)t).getIdentifier()));
            Collection<Var> collection = new ArrayList<>();
            EntityMapper entityMapper = new EntityMapper<>(User.class);
            entityMapper.extractNonNullFields(t).forEach((name, value) -> {
                collection.add(var("user").has((String) name, value));
            });

            matchQuery.insert(collection).execute();
        }
        try {
            graknGraph.commit();
        } catch (GraknValidationException e) {
            System.out.println("Unable to commit");
            e.printStackTrace();
        }
        DBConnection.getInstance().close();
        return this.read((Class<T>) t.getClass(),id); // TODO: 8/4/17 Return the actual updated object when updated
    }
}
