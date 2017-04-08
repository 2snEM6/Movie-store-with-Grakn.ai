package limia.Grakn;

import ai.grakn.concept.Entity;
import ai.grakn.concept.Resource;
import com.google.gson.Gson;
import limia.Dto.User;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by macbook on 8/4/17.
 */
public class EntityMapper<T> implements IEntityMapper<T>{

    protected Class<T> type;


    public EntityMapper(Class<T> type) {
        this.type = type;
    }


    @Override
    public T fromEntity(Entity entity) {
        T t = null;
        try {
            t = type.newInstance();
        } catch (InstantiationException e) {
            System.out.println("Unable to instantiate");
        } catch (IllegalAccessException e) {
            System.out.println("Unable to instantiate");
        }
        for (Field field : type.getDeclaredFields()) {
            for (Resource<?> resource : entity.resources()) {
                String resourceName = resource.type().getName().toString();
                String fieldName = field.getName();
                if (resourceName.equals(fieldName)) {
                    try {
                        field.setAccessible(true);
                        field.set(t, resource.getValue());
                        field.setAccessible(false);
                    } catch (IllegalAccessException e) {
                        System.out.println("Unable to parse field");
                    }
                }
            }
        }
        return t;
    }

    @Override
    public Entity toEntity(T t) {
        return null;
    }
}
