package model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.*
import java.util.Random

class TestTournament {
    private var tournament = Tournament()
    private val player1 = Player("Tim")
    private val player2 = Player("Tom")
    private val player3 = Player("Bob")
    private val player4 = Player("Lisa")
    private val player5 = Player("Irina")

    @BeforeEach
    fun setupTournament() {
        tournament = Tournament()
        tournament.addPlayer(player1)
        tournament.addPlayer(player2)
        tournament.addPlayer(player3)
        tournament.addPlayer(player4)
        tournament.addPlayer(player5)
        val random = Random()
        tournament.nextRound()
        tournament.getRounds().forEach{
            round -> round.getMatches()
            .forEach { match -> match.setMatchResult(
                    random.nextInt(21), random.nextInt(21)
                )
            }
        }
    }

    @Test
    fun testAddPlayer() {
        val player = Player("Tim1")
        tournament.addPlayer(player)
        assertTrue(tournament.getPlayers().contains(player))
    }

    @Test
    fun testRemovePlayer() {
        tournament.removePlayer(player1)
        assertFalse(tournament.getPlayers().contains(player1))
    }

    @Test
    fun testGetPlayers() {
        assertTrue(tournament.getPlayers().contains(player1))
    }

    @Test
    fun testUpdatePlayers() {
        tournament.updatePlayer("Tim2", player1.list, player1.uniqueID)
        assertEquals(tournament.getPlayers()[0].name, "Tim2")
    }

    @Test
    fun testCreateRound() {
        tournament.nextRound()
        assertEquals(2, tournament.getRounds().size)
        tournament.deleteCurrentRound()
        assertEquals(1, tournament.getRounds().size)
    }

    @Test
    fun testGetScores() {
        assertEquals(5, tournament.getScores().size)
    }

    @Test
    fun testScoresAfterPlayerRename() {
        tournament.updatePlayer("Tim2", player1.list, player1.uniqueID)
        val updatedPlayer = tournament.getScores().first {
            it.player.name == "Tim2"
        }
        assertEquals(updatedPlayer.player.name, "Tim2")
    }
}