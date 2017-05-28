package limia.Grakn;

import ai.grakn.GraknGraph;
import ai.grakn.concept.*;
import ai.grakn.exception.GraknValidationException;
import ai.grakn.graql.InsertQuery;
import ai.grakn.graql.MatchQuery;
import ai.grakn.graql.QueryBuilder;
import ai.grakn.graql.Var;
import limia.Connection.DBConnection;
import limia.Dto.Movie;
import limia.Dto.Relation;
import limia.Dto.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static ai.grakn.graql.Graql.var;

/**
 * Created by workstation on 06/04/2017.
 */
@SuppressWarnings("unchecked")
public class GraknEntityManager {

    private final QueryBuilder queryBuilder;
    private final GraknGraph graknGraph;


    public GraknEntityManager() {
        graknGraph = DBConnection.getInstance().getGraph();
        queryBuilder = graknGraph.graql();
    }

    public boolean exists(Class type, final Object id) {
        if (type == User.class) {
            User user = read(User.class, id);
            return user != null;
        }
        if (type == Movie.class) {
            Movie movie = read(Movie.class, id);
            return movie != null;
        }
        if (type == Relation.class) {
            Relation relation = read(Relation.class, id);
            return relation != null;
        }
        if (type == limia.Dto.Entity.class) {
            limia.Dto.Entity entity = read(limia.Dto.Entity.class, id);
            return entity != null;
        }
        return false;
    }

    public boolean existsRelation(final Object id) {

        Relation relation = read(Relation.class, id);
        return relation != null;
    }

    public <T> T findBy(Class<T> type, final String key, final String value) {
        DBConnection.getInstance().open();
        EntityMapper entityMapper = new EntityMapper<>(type);
        MatchQuery query = queryBuilder.match(
                var("entity").has(key, var("key")),
                var("key").value(value)
        );
        final boolean[] first = {true};
        final Object[] object = new Object[1];
        query.forEach(k -> {
            Entity entity = k.get("entity").asEntity();
            if (first[0])
                object[0] = type.cast(entityMapper.fromEntity(entity));
            first[0] = false;
        });
        DBConnection.getInstance().close();
        return type.cast(object[0]);
    }

    public <T> T persist(T t) {
        DBConnection.getInstance().open();
        try {
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
            graknGraph.commit();
        } catch (GraknValidationException e) {
            System.out.println("Unable to commit changes");
        }
        DBConnection.getInstance().close();
        return t;
    }

    public <T> void delete(Class<T> type, final Object id) {
        DBConnection.getInstance().open();
        try {
            String entityName;
            if (type == User.class) {
                entityName = "user";
                queryBuilder.match(var(entityName).has("identifier", id)).delete(entityName).execute();
            }
            if (type == Movie.class) {
                entityName = "movie";
                queryBuilder.match(var(entityName).has("identifier", id)).delete(entityName).execute();
            }
            if (type == Relation.class) {
                entityName = "relation";
                queryBuilder.match(var(entityName).id(ConceptId.of(id))).delete(entityName).execute();
            }
            graknGraph.commit();
        } catch (GraknValidationException e) {
            System.out.println("Unable to commit changes");
        }
        DBConnection.getInstance().close();
    }

    public boolean existsRelation(String relationName, String firstRoleplayerID, String secondRoleplayerID,
                                 String firstRole, String secondRole) {

        DBConnection.getInstance().open();
        MatchQuery query = queryBuilder.match(
                var("relation").rel(firstRole, "x").rel(secondRole, "y"),
                var("relation").isa(relationName),
                var("x").has("identifier", firstRoleplayerID),
                var("y").has("identifier", secondRoleplayerID)
        );
        /*MatchQuery query = queryBuilder.match(var("relation").rel(var("x").playsRole(firstRole),
                var("y").playsRole(secondRole)).isa(relationName), var("x").has("identifier",
                firstRoleplayerID), var("y").has("identifier", secondRoleplayerID));*/
        final boolean[] found = {false};
        query.forEach(k -> {
            ai.grakn.concept.Relation graknRelation = k.get("relation").asRelation();
            found[0] = true;
        });
        DBConnection.getInstance().close();
        return found[0];
    }

    public <T> T read(Class<T> type, final Object id) {
        DBConnection.getInstance().open();
        try {
            if (type == limia.Dto.Entity.class) {
                EntityMapper entityMapper = new EntityMapper<>(limia.Dto.Entity.class);
                MatchQuery query = queryBuilder.match(
                        var("entity").has("identifier", var("id")),
                        var("id").value(id)
                );
                final boolean[] first = {true};
                final limia.Dto.Entity[] _entity = new limia.Dto.Entity[1];
                query.forEach(k -> {
                    ai.grakn.concept.Entity entity = k.get("entity").asEntity();
                    if (first[0]) {
                        _entity[0] = (limia.Dto.Entity) entityMapper.fromEntity(entity);
                        if (_entity[0] != null) {
                            _entity[0].setIdentifier((String) id);
                        }
                    }
                    first[0] = false;
                });

                DBConnection.getInstance().close();
                return (T) _entity[0];
            }
            if (type == User.class) {
                EntityMapper entityMapper = new EntityMapper<>(User.class);
                MatchQuery query = queryBuilder.match(
                        var("user").has("identifier", var("id")),
                        var("id").value(id)
                );
                final boolean[] first = {true};
                final User[] user = new User[1];
                query.forEach(k -> {
                    ai.grakn.concept.Entity entity = k.get("user").asEntity();
                    if (first[0]) {
                        user[0] = (User) entityMapper.fromEntity(entity);
                        if (user[0] != null) {
                            user[0].setIdentifier((String) id);
                        }
                    }
                    first[0] = false;
                });
                DBConnection.getInstance().close();
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
                    if (first[0]) {
                        movie[0] = (Movie) entityMapper.fromEntity(entity);
                        if (movie[0] != null) {
                            movie[0].setIdentifier((String) id);
                        }
                    }
                    first[0] = false;
                });
                DBConnection.getInstance().close();
                return (T) movie[0];
            }
            if (type == Relation.class) {
                MatchQuery query = queryBuilder.match(var("relation").id(ConceptId.of(id)));
                RelationMapper relationMapper = new RelationMapper();
                final boolean[] first = {true};
                final Relation[] relation = new Relation[1];
                query.forEach(k -> {
                    ai.grakn.concept.Relation graknRelation = k.get("relation").asRelation();
                    if (first[0]) {
                        relation[0] = relationMapper.fromRelation(graknRelation);// TODO: 21/05/2017 Map the relation
                        if (relation[0] != null) {
                            relation[0].setIdentifier((String) id);
                        }
                    }
                    first[0] = false;
                });
                DBConnection.getInstance().close();
                return (T) relation[0];

            }
        } catch (Exception e) {
            System.out.println("Unable to read entities");
        }
        DBConnection.getInstance().close();
        return null;
    }

    public <T> T update(T t) {
        DBConnection.getInstance().open();
        String id;
        try {
            id = null;
            if (t instanceof User) {
                id = ((User) t).getIdentifier();
                MatchQuery matchQuery = queryBuilder.match(var("user").has("identifier", ((User) t).getIdentifier()));
                Collection<Var> collection = new ArrayList<>();
                EntityMapper entityMapper = new EntityMapper<>(User.class);
                entityMapper.extractNonNullFields(t).forEach((name, value) ->
                        collection.add(var("user").has((String) name, value)));
                matchQuery.insert(collection).execute();
            }
            graknGraph.commit();
        } catch (GraknValidationException e) {
            System.out.println("Unable to commit");
            return null;
        }
        DBConnection.getInstance().close();
        return this.read((Class<T>) t.getClass(), id); // TODO: 8/4/17 Return the actual updated object when updated
    }

    public <T> ArrayList<T> readAll(Class<T> type) {
        DBConnection.getInstance().open();
        if (type == User.class) {
            ArrayList<User> users = new ArrayList<>();
            //noinspection unchecked
            EntityMapper entityMapper = new EntityMapper(User.class);
            MatchQuery matchQuery = queryBuilder.match(var("user").isa("user"));
            for (Map<String, Concept> conceptMap : matchQuery) {
                Entity entity = conceptMap.get("user").asEntity();
                System.out.println(conceptMap);
                User user = (User) entityMapper.fromEntity(conceptMap.get("user").asEntity());
                users.add(user);
            }
            DBConnection.getInstance().close();
            return (ArrayList<T>) users;
        }
        if (type == Relation.class) {
            ArrayList<Relation> relations = new ArrayList<>();
            RelationMapper relationMapper = new RelationMapper();
            MatchQuery matchQuery = queryBuilder.match(var("relation").sub("relation"));
            ArrayList<String> relationNames = new ArrayList<>();
            matchQuery.forEach(concept -> relationNames.add(concept.get("relation").asType().getName().getValue()));
            relationNames.forEach(relationName -> {
                if (!relationName.equals("relation")) {
                    MatchQuery _matchQuery = queryBuilder.match(var("relation").isa(relationName));
                    for (Map<String, Concept> conceptMap : _matchQuery) {
                        ai.grakn.concept.Relation relation = conceptMap.get("relation").asRelation();
                        Relation _relation = relationMapper.fromRelation(relation);
                        relations.add(_relation);
                    }
                }
            });
            DBConnection.getInstance().close();
            return (ArrayList<T>) relations;
        }
        if (type == Movie.class) {
            ArrayList<Movie> movies = new ArrayList<>();
            //noinspection unchecked
            EntityMapper entityMapper = new EntityMapper(Movie.class);
            MatchQuery matchQuery = queryBuilder.match(var("movie").isa("movie"));
            for (Map<String, Concept> conceptMap : matchQuery) {
                Movie movie = (Movie) entityMapper.fromEntity(conceptMap.get("movie").asEntity());
                movies.add(movie);
            }
            DBConnection.getInstance().close();
            return (ArrayList<T>) movies;
        }
        DBConnection.getInstance().close();
        return new ArrayList<>();
    }



    public <T> ArrayList<T> readRelationsByType(String type) {
        DBConnection.getInstance().open();
        ArrayList<Relation> relations = new ArrayList<>();
        //noinspection unchecked
        EntityMapper entityMapperEntity = new EntityMapper(limia.Dto.Entity.class);
        MatchQuery matchQuery = queryBuilder.match(var("relation").isa(type));
        for (Map<String, Concept> conceptMap : matchQuery) {
            Relation relation = new Relation();
            relation.setRelationName(type);
            Map<RoleType, Instance> rolePlayers = conceptMap.get("relation").asRelation().rolePlayers();
            relation.setIdentifier(conceptMap.get("relation").asRelation().getId().getValue());
            if (rolePlayers.size() == 2) {
                final int[] count = {0};
                rolePlayers.forEach((k, v) ->{
                    String roleType = k.getName().getValue();

                    limia.Dto.Entity entity = (limia.Dto.Entity) entityMapperEntity.fromEntity(v.asEntity());
                    if (entity != null) {
                        String rolePlayerID = entity.getIdentifier();
                        if (count[0] == 0) {
                            relation.setFirstEntityID(rolePlayerID);
                            relation.setFirstRole(roleType);
                        }
                        if (count[0] == 1) {
                            relation.setSecondEntityID(rolePlayerID);
                            relation.setSecondRole(roleType);
                        }
                        count[0] += 1;
                    }
                });
            }
            relations.add(relation);
        }
        DBConnection.getInstance().close();
        return (ArrayList<T>) relations;
    }
}
