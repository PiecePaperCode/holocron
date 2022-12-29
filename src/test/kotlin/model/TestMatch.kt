package model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.*

class TestMatch {
    private lateinit var player1: Player
    private lateinit var player2: Player
    private lateinit var match: Match

    @BeforeEach
    fun setupMatch() {
        player1 = Player("Tim")
        player2 = Player("Tom")
        match = Match(player1, player2)
        assertEquals(Match.Result.ONGOING, match.getResult())
    }

    @Test
    fun testWinner() {
        val result = match.setMatchResult(10, 20)
        assertEquals(Match.Result.PLAYER2, result)
        assertEquals(player2, match.getWinner())
    }

    @Test
    fun testLooser() {
        val result = match.setMatchResult(10, 20)
        assertEquals(Match.Result.PLAYER2, result)
        assertEquals(player1, match.getLooser())
    }

    @Test
    fun testDraw() {
        val result = match.setMatchResult(10, 10)
        assertEquals(Match.Result.DRAW, result)
        assertEquals(null, match.getWinner())
    }

    @Test
    fun testResult() {
        val result = match.setMatchResult(10, 10)
        assertEquals(match.getResult(), result)
    }

    @Test
    fun testBye() {
        val byeMatch = Match(player1, null)
        assertEquals(Match.Result.PLAYER1, byeMatch.getResult())
    }

    @Test
    fun testEventPoints() {
        match.setMatchResult(10, 12)
        assertEquals(listOf(10, 12), match.getEventPoints())
    }

    @Test
    fun testgetPlayers() {
        match.setMatchResult(10, 12)
        assertEquals(listOf(player1, player2), match.getPlayers())
    }
}