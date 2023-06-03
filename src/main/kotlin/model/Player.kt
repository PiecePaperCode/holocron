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
    override fun hashCode(): Int {
        return uniqueID.hashCode()
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Player

        return uniqueID == other.uniqueID
    }
}