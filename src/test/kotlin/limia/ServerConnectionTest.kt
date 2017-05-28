package limia


import com.mashape.unirest.http.Unirest
import org.junit.Test
import kotlin.test.assertEquals

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
