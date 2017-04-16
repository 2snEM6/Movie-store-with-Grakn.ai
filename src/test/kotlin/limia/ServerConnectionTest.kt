package limia


import com.mashape.unirest.http.Unirest
import limia.Routing.HealthRoutingService
import org.json.JSONObject
import org.junit.ClassRule
import org.junit.Test
import spark.servlet.SparkApplication
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ServerConnectionTest {

        val SERVERURL = "http://localhost:4568"

        @Test
        fun test() {
            /* The second parameter indicates whether redirects must be followed or not */
            val jsonResponse = Unirest.get(SERVERURL + "/alive").asJson().body.`object`
            assertEquals(200, jsonResponse.getInt("code"))
            assertEquals("I am alive", jsonResponse.getString("message"))
        }

}
