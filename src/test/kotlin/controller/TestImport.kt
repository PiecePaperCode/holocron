package controller

import com.sun.net.httpserver.HttpServer
import io.ktor.client.plugins.ServerResponseException
import java.net.InetSocketAddress
import TestStartup
import kotlinx.coroutines.runBlocking
import model.Player
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

class TestImport {
    @Test
    fun testParsingHTML() {
        val htmlToParse = TestStartup::class
            .java
            .getResource("tabletoptournament.html")!!.readText()
        val parsedPlayers: List<Player> = Import().parse(htmlToParse)
        assertEquals("LowFreq", parsedPlayers[0].name)
        assertEquals("DarkAngelus", parsedPlayers[10].name)
    }

    @Test
    fun testFetchTableTopTournament() {
        val html = TestStartup::class.java.getResource("tabletoptournament.html")!!.readText()
        withTournamentServer(200, html) { baseUrl ->
            val parsedPlayers = runBlocking { Import(baseUrl).fetch(31293).parse() }
            assertEquals(Import().parse(html).map { it.name }, parsedPlayers.map { it.name })
            assertEquals("LowFreq", parsedPlayers.first().name)
            assertEquals("DarkAngelus", parsedPlayers[10].name)
        }
    }

    @Test
    fun testFetchRejectsHttpError() {
        withTournamentServer(503, "Service unavailable") { baseUrl ->
            assertThrows(ServerResponseException::class.java) {
                runBlocking { Import(baseUrl).fetch(31293) }
            }
        }
    }

    private fun withTournamentServer(status: Int, body: String, test: (String) -> Unit) {
        val server = HttpServer.create(InetSocketAddress("127.0.0.1", 0), 0)
        server.createContext("/t3_tournament_list.php") { exchange ->
            val bytes = body.toByteArray(Charsets.UTF_8)
            val responseStatus = if (exchange.requestURI.rawQuery == "tid=31293") status else 400
            exchange.responseHeaders.set("Content-Type", "text/html; charset=UTF-8")
            exchange.sendResponseHeaders(responseStatus, bytes.size.toLong())
            exchange.responseBody.use { it.write(bytes) }
        }
        server.start()
        try {
            test("http://127.0.0.1:${server.address.port}")
        } finally {
            server.stop(0)
        }
    }
}
