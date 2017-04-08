package limia.Grakn

import ai.grakn.concept.Entity

/**
 * Created by macbook on 8/4/17.
 */
interface IEntityMapper<T> {
    fun fromEntity(entity: Entity): T?
    fun toEntity(t: T): Entity?
    fun extractNonNullFields(t: T): Map<String, Any>
}
