package limia.Definition

/**
 * Created by macbook on 9/4/17.
 */
open class GlobalConstants {
    object HTTPMessages {
        val CREATE = "created"
        val READ = "read"
        val UPDATE = "updated"
        val DELETE = "deleted"
        val NOT_FOUND = "not found"
        val EXISTS = "exists"
        val ALREADY_EXISTS = "already exists"
    }

    enum class CRUD {
        CREATE, READ, UPDATE, DELETE
    }
}
