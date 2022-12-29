package model

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

class TestScore {
    private lateinit var player1: Player
    private lateinit var player2: Player
    private lateinit var player3: Player
    private lateinit var player4: Player
    private lateinit var player5: Player
    private lateinit var round: Round
    private lateinit var tournament: Tournament

    @BeforeEach
    fun setupScore() {
        tournament = Tournament()
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
        round.pairSwiss()
        round.getMatches()[0].setMatchResult(20, 0)
        round.getMatches()[1].setMatchResult(10, 10)
    }

    @Test
    fun testScore() {
        val scores = Score(arrayListOf(round)).generateScores()
        assertEquals(3, scores[0].getPoints())
        assertEquals(3, scores[1].getPoints())
        assertEquals(1, scores[2].getPoints())
        assertEquals(1, scores[3].getPoints())
        assertEquals(0, scores[4].getPoints())
    }

    @Test
    fun testSOS() {
        val scores = Score(arrayListOf(round)).generateScores()
        assertEquals(0.0, scores[0].getSOS())
        assertEquals(0.0, scores[1].getSOS())
        assertEquals(1.0, scores[2].getSOS())
        assertEquals(1.0, scores[3].getSOS())
        assertEquals(3.0, scores[4].getSOS())
    }

    @Test
    fun testMissionPoints() {
        val scores = Score(arrayListOf(round)).generateScores()
        assertEquals(20, scores[0].getMissionPoints())
        assertEquals(18, scores[1].getMissionPoints())
        assertEquals(10, scores[2].getMissionPoints())
        assertEquals(10, scores[3].getMissionPoints())
        assertEquals(0, scores[4].getMissionPoints())
    }

    @Test
    fun testOpponents() {
        val score = Score(arrayListOf(round))
        val scores = score.generateScores()
        val opponents1 = score.getOpponents(scores[0].player)
        assertEquals(1, opponents1.size)
        val opponents2 = score.getOpponents(scores[1].player)
        assertEquals(0, opponents2.size)
    }
}