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
    val userID = UUID.randomUUID().toString()
    val movieID = UUID.randomUUID().toString()


    @Test
    fun createDownloadRelationBetweenUserAndMovie() {
        val postRequest = Unirest.post("$SERVERURL/users/$userID/movies/$movieID")
                .queryString("relation", "download")
        val jsonNode = postRequest.asJson()
        val status = jsonNode.status
        val jsonObject = jsonNode.body.`object`
        assertEquals(200, status)
        assertEquals(201, jsonObject.getInt("code"))
        assertNotNull(jsonObject)
        assertNotNull(jsonObject.getJSONObject("data"))
    }


}