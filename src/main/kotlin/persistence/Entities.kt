package persistence

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "tournament")
internal data class TournamentEntity(
    @field:DatabaseField(id = true) var id: Int = 1,
    @field:DatabaseField(canBeNull = false) var name: String = ""
)

@DatabaseTable(tableName = "players")
internal data class PlayerEntity(
    @field:DatabaseField(id = true) var id: Int = 0,
    @field:DatabaseField(columnName = "active_position") var activePosition: Int? = null,
    @field:DatabaseField(columnName = "unique_id", canBeNull = false) var uniqueId: String = "",
    @field:DatabaseField(canBeNull = false) var name: String = "",
    @field:DatabaseField(canBeNull = false) var squad: String = ""
)

@DatabaseTable(tableName = "rounds")
internal data class RoundEntity(
    @field:DatabaseField(id = true) var id: Int = 0
)

@DatabaseTable(tableName = "matches")
internal data class MatchEntity(
    @field:DatabaseField(columnName = "round_id", foreign = true, canBeNull = false, uniqueCombo = true)
    var round: RoundEntity = RoundEntity(),
    @field:DatabaseField(uniqueCombo = true) var position: Int = 0,
    @field:DatabaseField(columnName = "player1", foreign = true, canBeNull = false)
    var player1: PlayerEntity = PlayerEntity(),
    @field:DatabaseField(columnName = "player2", foreign = true) var player2: PlayerEntity? = null,
    @field:DatabaseField(canBeNull = false) var points1: Int = 0,
    @field:DatabaseField(canBeNull = false) var points2: Int = 0,
    @field:DatabaseField(canBeNull = false) var ongoing: Boolean = true
)
