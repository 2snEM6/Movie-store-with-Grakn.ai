package limia

import com.mashape.unirest.http.Unirest
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Created by workstation on 19/04/2017.
 */
class RelationTest {

    val SERVERURL = "http://localhost:4568"
    var userID = UUID.randomUUID().toString()
    var movieID = UUID.randomUUID().toString()


    @Test
    fun createDownloadRelationBetweenNonExistentUserAndMovie() {
        val postRequest = Unirest.post("$SERVERURL/users/$userID/movies/$movieID/downloaded")
        val jsonNode = postRequest.asJson()
        val status = jsonNode.status
        val jsonObject = jsonNode.body.`object`
        assertEquals(200, status)
        assertNotNull(jsonObject)
        assertEquals(404, jsonObject.getInt("code"))
        assertNotNull(jsonObject.getJSONArray("errors"))
        assertEquals(2, jsonObject.getJSONArray("errors").length())
        assertEquals(true,jsonObject.getJSONArray("errors").contains("Movie not found"))
        assertEquals(true,jsonObject.getJSONArray("errors").contains("User not found"))
        /*assertNotNull(jsonObject)
        assertNotNull(jsonObject.getJSONObject("data"))*/
    }

    @Test
    fun createDownloadRelationBetweenNonExistentUserAndExistentMovie() {
        val movieRoutesTest = MovieRoutesTest()
        movieRoutesTest.createMovie()
        movieID = movieRoutesTest.movieID!!

        val postRequest = Unirest.post("$SERVERURL/users/$userID/movies/$movieID/downloaded")
                .queryString("relation", "download")
        val jsonNode = postRequest.asJson()
        val status = jsonNode.status
        val jsonObject = jsonNode.body.`object`
        assertEquals(200, status)
        assertNotNull(jsonObject)
        assertEquals(404, jsonObject.getInt("code"))
        assertNotNull(jsonObject.getJSONArray("errors"))
        assertEquals(1, jsonObject.getJSONArray("errors").length())
        assertEquals(true,jsonObject.getJSONArray("errors").contains("User not found"))
    }

    @Test
    fun createDownloadRelationBetweenExistentUserAndNonExistentMovie() {
        val userRoutesTest = UserRoutesTest()
        userRoutesTest.createUser()
        userID = userRoutesTest.userID!!

        val postRequest = Unirest.post("$SERVERURL/users/$userID/movies/$movieID/downloaded")
        val jsonNode = postRequest.asJson()
        val status = jsonNode.status
        val jsonObject = jsonNode.body.`object`
        assertEquals(200, status)
        assertNotNull(jsonObject)
        assertEquals(404, jsonObject.getInt("code"))
        assertNotNull(jsonObject.getJSONArray("errors"))
        assertEquals(1, jsonObject.getJSONArray("errors").length())
        assertEquals(true,jsonObject.getJSONArray("errors").contains("Movie not found"))
    }

    @Test
    fun createDownloadRelationBetweenExistentUserAndExistentMovie() {
        val userRoutesTest = UserRoutesTest()
        userRoutesTest.createUser()
        userID = userRoutesTest.userID!!

        val movieRoutesTest = MovieRoutesTest()
        movieRoutesTest.createMovie()
        movieID = movieRoutesTest.movieID!!

        val postRequest = Unirest.post("$SERVERURL/users/$userID/movies/$movieID/downloaded")
        val jsonNode = postRequest.asJson()
        val status = jsonNode.status
        val jsonObject = jsonNode.body.`object`
        assertEquals(200, status)
        assertNotNull(jsonObject)
        assertEquals(201, jsonObject.getInt("code"))
        assertNotNull(jsonObject.getJSONObject("data"))
        val data = jsonObject.getJSONObject("data")
        assertEquals(data.getString("relationName"), "download")
        assertEquals(data.getString("firstEntityID"), userID)
        assertEquals(data.getString("secondEntityID"), movieID)
        assertEquals(data.getString("firstRole"), "downloaded_a")
        assertEquals(data.getString("secondRole"), "is_downloaded_by")
        assertEquals(jsonObject.getString("message"), "Relation created")
    }


}