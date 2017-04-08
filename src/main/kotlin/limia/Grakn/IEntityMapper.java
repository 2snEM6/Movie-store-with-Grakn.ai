package limia.Grakn;

import ai.grakn.concept.Entity;

/**
 * Created by macbook on 8/4/17.
 */
public interface IEntityMapper<T> {
    public T fromEntity(Entity entity);
    public Entity toEntity(T t);
}
