package controller

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
        runBlocking {
            val parsedPlayers: List<Player> = Import().fetch(31293).parse()
            assertEquals("LowFreq", parsedPlayers[0].name)
            assertEquals("DarkAngelus", parsedPlayers[10].name)
        }
    }
}