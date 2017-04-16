package limia

import com.mashape.unirest.http.Unirest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Created by workstation on 16/04/2017.
 */
class UserRoutesTest {

    val SERVERURL = "http://localhost:4568"
    var userID : String? = null
    val name = "Daniel"
    val email = "limiaspasdaniel@gmail.com"

    @Before
    fun setUp() {
        ServerConnectionTest().test()
    }

    @Test
    fun createUser() {
        val postRequest = Unirest.post(SERVERURL + "/users")
                .queryString("name", name)
                .queryString("email", email)
        val jsonNode = postRequest.asJson()
        val status = jsonNode.status
        val jsonObject = jsonNode.body.`object`
        assertEquals(200, status)
        assertEquals(201, jsonObject.getInt("code"))
        assertNotNull(jsonObject)
        assertNotNull(jsonObject.getJSONObject("data"))
        assertEquals(jsonObject.getJSONObject("data").getString("name"), name)
        assertEquals(jsonObject.getJSONObject("data").getString("email"), email)
        assertEquals(jsonObject.getString("message"), "User created")
        assertNotNull(jsonObject.getJSONObject("data").getString("identifier"))
        userID = jsonObject.getJSONObject("data").getString("identifier")
        assertEquals(jsonObject.getString("message"), "User created")
    }

    @Test
    fun getExistingUser() {
        createUser()
        val getRequest = Unirest.get(SERVERURL + "/users/" + userID)
        val jsonNode = getRequest.asJson()
        val status = jsonNode.status
        val jsonObject = jsonNode.body.`object`
        assertEquals(200, status)
        assertEquals(200, jsonObject.getInt("code"))
        assertNotNull(jsonObject)
        assertNotNull(jsonObject.getJSONObject("data"))
        assertEquals(jsonObject.getJSONObject("data").getString("name"), name)
        assertEquals(jsonObject.getJSONObject("data").getString("email"), email)
        assertEquals(jsonObject.getString("message"), "User read")
    }

    @Test
    fun deleteExistingUser() {
        createUser()
        val deleteRequest = Unirest.delete(SERVERURL + "/users/" + userID)
        val jsonNode = deleteRequest.asJson()
        val status = jsonNode.status
        val jsonObject = jsonNode.body.`object`
        assertEquals(200, status)
        assertEquals(204, jsonObject.getInt("code"))
        assertEquals("User deleted", jsonObject.getString("message"))
    }
}