package model

import java.util.*

class Player(
    val name: String,
    val list: String = "",
    val uniqueID: String = UUID.randomUUID().toString(),
) : Comparable<Player> {
    override fun compareTo(other: Player): Int {
        return uniqueID.compareTo(other.uniqueID)
    }
}