package model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.*

class TestRound {
    private lateinit var player1: Player
    private lateinit var player2: Player
    private lateinit var player3: Player
    private lateinit var player4: Player
    private lateinit var player5: Player
    private lateinit var round: Round
    private var tournament = Tournament()

    @BeforeEach
    fun setupMatch() {
        player1 = Player("Tim")
        player2 = Player("Tom")
        player3 = Player("Bob")
        player4 = Player("Lisa")
        player5 = Player("Irina")
        tournament.addPlayer(player1)
        tournament.addPlayer(player2)
        tournament.addPlayer(player3)
        tournament.addPlayer(player4)
        tournament.addPlayer(player5)
        round = Round(tournament.getPlayers(), tournament.getRounds())
        assertEquals(false, round.allMatchesDone())
    }

    @Test
    fun testPairings() {
        round.pairSwiss()
        assertEquals(3, round.getMatches().size)
        assertEquals(Match.Result.PLAYER1, round.getMatches()[2].getResult())

        val round2 = Round(tournament.getPlayers(), arrayListOf(round))
        round2.pairSwiss()
        assertEquals(3, round2.getMatches().size)

        val round3 = Round(tournament.getPlayers(), arrayListOf(round, round2))
        round3.pairSwiss()
        assertEquals(3, round3.getMatches().size)

        val matches = round.getMatches() + round2.getMatches() + round3.getMatches()
        val distinctMatches = matches
            .distinctBy { match ->
                match.getPlayers()
                    .sortedBy { it.name }
            }
        assertEquals(matches.size, distinctMatches.size)
    }
}