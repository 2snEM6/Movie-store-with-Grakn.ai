package limia

import com.mashape.unirest.http.Unirest
import org.junit.Before
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Created by workstation on 16/04/2017.
 */
class MovieRoutesTest {

    val SERVERURL = "http://localhost:4568"
    var movieID : String? = null
    var themoviedbID : String? = null

    @Before
    fun setUp() {
        ServerConnectionTest().test()
    }

    @Test
    fun createMovie() {
        themoviedbID = UUID.randomUUID().toString()
        val postRequest = Unirest.post(SERVERURL + "/movies")
                .queryString("themoviedb_id", themoviedbID)
        val jsonNode = postRequest.asJson()
        val status = jsonNode.status
        val jsonObject = jsonNode.body.`object`
        assertEquals(200, status)
        assertEquals(201, jsonObject.getInt("code"))
        assertNotNull(jsonObject)
        assertNotNull(jsonObject.getJSONObject("data"))
        assertNotNull(jsonObject.getJSONObject("data").getString("identifier"))
        movieID = jsonObject.getJSONObject("data").getString("identifier")
        assertNotNull(jsonObject.getJSONObject("data").getString("themoviedb_id"))
        assertEquals(jsonObject.getJSONObject("data").getString("themoviedb_id"), themoviedbID)
        assertEquals(jsonObject.getString("message"), "Movie created")
    }

    @Test
    fun getExistingMovie() {
        createMovie()
        val getRequest = Unirest.get(SERVERURL + "/movies/" + movieID)
        val jsonNode = getRequest.asJson()
        val status = jsonNode.status
        val jsonObject = jsonNode.body.`object`
        assertEquals(200, status)
        assertEquals(200, jsonObject.getInt("code"))

        assertNotNull(jsonObject)
        assertNotNull(jsonObject.getJSONObject("data"))
        assertEquals(jsonObject.getJSONObject("data").getString("themoviedb_id"), themoviedbID)
        assertEquals(jsonObject.getString("message"), "Movie read")
    }

    @Test
    fun getNonExistingMovie() {
        val getRequest = Unirest.get(SERVERURL + "/movies/" + UUID.randomUUID().toString())
        val jsonNode = getRequest.asJson()
        val status = jsonNode.status
        val jsonObject = jsonNode.body.`object`
        assertEquals(200, status)
        assertEquals(404, jsonObject.getInt("code"))
        assertNotNull(jsonObject)
        assertEquals(jsonObject.getString("message"), "Movie not found")
    }

    @Test
    fun deleteNonExistingMovie() {
        val deleteRequest = Unirest.delete(SERVERURL + "/movies/" +UUID.randomUUID().toString())
        val jsonNode = deleteRequest.asJson()
        val status = jsonNode.status
        val jsonObject = jsonNode.body.`object`
        assertEquals(200, status)
        assertEquals(404, jsonObject.getInt("code"))
        assertEquals("Movie not found", jsonObject.getString("message"))
    }

    @Test
    fun deleteExistingMovie() {
        createMovie()
        val deleteRequest = Unirest.delete(SERVERURL + "/movies/" + movieID)
        val jsonNode = deleteRequest.asJson()
        val status = jsonNode.status
        val jsonObject = jsonNode.body.`object`
        assertEquals(200, status)
        assertEquals(204, jsonObject.getInt("code"))
        assertEquals("Movie deleted", jsonObject.getString("message"))
    }
}