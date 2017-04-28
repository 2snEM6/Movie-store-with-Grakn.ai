package limia

import com.mashape.unirest.http.Unirest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import com.github.javafaker.Faker
import java.util.*


/**
 * Created by workstation on 16/04/2017.
 */
class UserRoutesTest {

    val SERVERURL = "http://localhost:4568"
    var userID : String? = null
    var name : String? = null
    var email : String? = null

    @Before
    fun setUp() {
        ServerConnectionTest().test()
    }

    @Test
    fun createUser() {
        val faker = Faker()
        name = faker.name().fullName()
        email = faker.internet().emailAddress()
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
        assertNotNull(jsonObject.getJSONObject("data").getString("identifier"))
        userID = jsonObject.getJSONObject("data").getString("identifier")
        assertEquals(jsonObject.getString("message"), "User created")
    }

    @Test
    fun createUserWithWrongParams() {
        val faker = Faker()
        name = faker.name().fullName()
        val postRequest = Unirest.post(SERVERURL + "/users")
                .queryString("name", name)
        val jsonNode = postRequest.asJson()
        val status = jsonNode.status
        val jsonObject = jsonNode.body.`object`
        assertEquals(200, status)
        assertEquals(201, jsonObject.getInt("code"))
        assertNotNull(jsonObject)
        assertNotNull(jsonObject.getJSONArray("errors"))
        assertEquals(jsonObject.getJSONArray("errors").contains("Invalido or missing parameters"), true)
        assertEquals(jsonObject.getJSONArray("errors").length(), 1)
    }



    @Test
    fun createExistingUserByEmail() {
        createUser()
        val postRequest = Unirest.post(SERVERURL + "/users")
                .queryString("name", name)
                .queryString("email", email)
        val jsonNode = postRequest.asJson()
        val status = jsonNode.status
        val jsonObject = jsonNode.body.`object`
        assertEquals(200, status)
        assertEquals(409, jsonObject.getInt("code"))
        assertNotNull(jsonObject)
        assertNotNull(jsonObject.getJSONArray("errors"))
        assertEquals(jsonObject.getJSONArray("errors").contains("User already exists"), true)
        assertEquals(jsonObject.getJSONArray("errors").length(), 1)
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
    fun getNonExistingUser() {
        val randomID = UUID.randomUUID().toString();
        val getRequest = Unirest.get(SERVERURL + "/users/" + randomID)
        val jsonNode = getRequest.asJson()
        val status = jsonNode.status
        val jsonObject = jsonNode.body.`object`
        assertEquals(200, status)
        assertEquals(404, jsonObject.getInt("code"))
        assertNotNull(jsonObject)
        assertNotNull(jsonObject.getJSONArray("errors"))
        assertEquals(jsonObject.getJSONArray("errors").contains("User not found"), true)
        assertEquals(jsonObject.getJSONArray("errors").length(), 1)
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

    @Test
    fun deleteNonExistingUser() {
        val deleteRequest = Unirest.delete(SERVERURL + "/users/" + userID)
        val jsonNode = deleteRequest.asJson()
        val status = jsonNode.status
        val jsonObject = jsonNode.body.`object`
        assertEquals(200, status)
        assertEquals(404, jsonObject.getInt("code"))
        assertNotNull(jsonObject)
        assertNotNull(jsonObject.getJSONArray("errors"))
        assertEquals(jsonObject.getJSONArray("errors").contains("User not found"), true)
        assertEquals(jsonObject.getJSONArray("errors").length(), 1)
    }
}