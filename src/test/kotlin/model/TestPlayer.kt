package model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class TestPlayer {
    private val player1 = Player("Tim")
    private val player2 = Player("Max")

    @Test
    fun testComparingPlayers() {
        assertNotEquals(player1, player2)
        val player3 = Player(
            uniqueID = player1.uniqueID,
            name = player1.name,
            list = player1.list
        )
        assertEquals(player1, player3)
    }

    @Test
    fun testPlayerHashCode() {
        assertNotEquals(player1.hashCode(), player2.hashCode())
        val player3 = Player(
            uniqueID = player1.uniqueID,
            name = player1.name,
            list = player1.list
        )
        assertEquals(player1.hashCode(), player3.hashCode())
    }
}