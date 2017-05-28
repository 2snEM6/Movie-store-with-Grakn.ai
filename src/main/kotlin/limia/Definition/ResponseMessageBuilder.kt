package limia.Definition

import limia.Dto.User

import limia.Definition.GlobalConstants.CRUD
import limia.Definition.GlobalConstants.HTTPMessages.ALREADY_EXISTS
import limia.Definition.GlobalConstants.HTTPMessages.CREATE
import limia.Definition.GlobalConstants.HTTPMessages.DELETE
import limia.Definition.GlobalConstants.HTTPMessages.EXISTS
import limia.Definition.GlobalConstants.HTTPMessages.NOT_FOUND
import limia.Definition.GlobalConstants.HTTPMessages.READ
import limia.Definition.GlobalConstants.HTTPMessages.UPDATE


/**
 * Created by macbook on 9/4/17.
 */
open class ResponseMessageBuilder {

    companion object {
        fun CREATE(t: Class<*>): String {
            return t.simpleName + " " + CREATE
        }

        fun EXISTS(t: Class<*>): String {
            return "The " + (t.simpleName.toLowerCase() + " " + EXISTS)
        }

        fun ALREADY_EXISTS(t: Class<*>): String {
            if (t == User::class.java) {
                return "The " + (t.simpleName.toLowerCase() + " " + ALREADY_EXISTS + " with that email")
            }
            return "The " + (t.simpleName.toLowerCase() + " " + ALREADY_EXISTS)
        }

        fun BAD_REQUEST(): String {
            return "Invalid, malformed or missing parameters"
        }

        fun DELETE(t: Class<*>): String {
            return t.simpleName + " " + DELETE
        }

        fun READ(t: Class<*>): String {
            return t.simpleName + " " + READ
        }

        fun NOT_FOUND(t: Class<*>): String {
            return t.simpleName + " " + NOT_FOUND
        }

        fun READ_ALL(t: Class<*>): String {
            return t.simpleName + "s " + READ
        }

        fun UPDATE(t: Class<*>): String {
            return t.simpleName + " " + UPDATE
        }

        fun ERROR(t: Class<*>, crud: CRUD): String {
            var verb = "undefined"
            when (crud) {
                GlobalConstants.CRUD.CREATE -> verb = "creating"
                GlobalConstants.CRUD.READ -> verb = "reading"
                GlobalConstants.CRUD.UPDATE -> verb = "updating"
                GlobalConstants.CRUD.DELETE -> verb = "deleting"
            }
            return "Error " + verb + " the " + t.simpleName
        }

        fun NOT_FOUND(t: Class<*>, crud: CRUD): String? {
            var message: String? = null
            when (crud) {
                GlobalConstants.CRUD.READ -> message = t.simpleName + " not found"
                GlobalConstants.CRUD.CREATE -> TODO()
                GlobalConstants.CRUD.UPDATE -> TODO()
                GlobalConstants.CRUD.DELETE -> TODO()
            }
            return message
        }

        fun NOT_FOUND_ALL(t: Class<*>, crud: CRUD): String? {
            var message: String? = null
            when (crud) {
                GlobalConstants.CRUD.READ -> message = t.simpleName + "s" + " not found"
                GlobalConstants.CRUD.CREATE -> TODO()
                GlobalConstants.CRUD.UPDATE -> TODO()
                GlobalConstants.CRUD.DELETE -> TODO()
            }
            return message
        }
    }
}
