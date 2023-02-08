package controller

import model.Player
import model.Tournament
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.*

class TestExport {
    private var tournament = Tournament()
    private val player1 = Player("Tim")
    private val player2 = Player("Tom")
    private val player3 = Player("Bob")
    private val player4 = Player("Lisa")
    private val player5 = Player("Irina")

    @BeforeEach
    fun setupTournament() {
        tournament = Tournament()
        tournament.name = "Test Tournament"
        tournament.addPlayer(player1)
        tournament.addPlayer(player2)
        tournament.addPlayer(player3)
        tournament.addPlayer(player4)
        tournament.addPlayer(player5)
        tournament.nextRound()
        tournament.getRounds().forEach {
            it.getMatches().forEach { match ->
                match.setMatchResult(
                    (0..20).random(), (0..20).random()
                )
            }
        }
    }

    @Test
    fun testExport() {
        val export = Export().parse(tournament)
        assertEquals(tournament.name, export.name)
        assertEquals(
            tournament.getRounds()[0].getMatches()[0].getPlayers()[0].name,
            export.matches[0].matches[0].player1
        )
        val json = Export().toJson(tournament)
        println(json)
    }
}