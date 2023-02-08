package controller

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import model.Tournament
import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8

class Export {
    fun parse(tournament: Tournament): TournamentExport {
        return TournamentExport(tournament)
    }
    fun toJson(tournament: Tournament): String? {
        val gson = Gson()
        return gson.toJson(TournamentExport(tournament))
    }
}

class TournamentExport (
    tournament: Tournament
) {
    val name = tournament.name
    val players = tournament.getScores().mapIndexed{ index, it ->
        PlayerExport(
            id = md5(it.player.name),
            name = it.player.name,
            score = it.getPoints(),
            sos = it.getSOS(),
            missionPoints = it.getMissionPoints(),
            rank = RankExport(index + 1),
            list = it.player.list,
        )
    }
    val matches = tournament.getRounds().mapIndexed { index, round ->
        RoundExport(
            roundType = "swiss",
            roundNumber = index + 1,
            scenario = "",
            matches = round.getMatches()
                .filter { it.getPlayers().size == 2 }
                .map {
                    val player1 = it.getPlayers()[0]
                    val player2 = it.getPlayers()[1]
                    MatchExport(
                        player1 = player1.name,
                        player1Id = md5(player1.name),
                        player2 = player2.name,
                        player2Id = md5(player2.name),
                        player1Points = it.getEventPoints()[0],
                        player2Points = it.getEventPoints()[1],
                    )
                }
        )
    }
}

data class PlayerExport(
    val id: String,
    val name: String,
    val score: Int,
    val sos: Double,
    @SerializedName("mov") val missionPoints: Int, // Used for Mission Points
    val rank: RankExport,
    val list: String
)

data class RankExport(
    val swiss: Int
)

data class RoundExport(
    @SerializedName("round-type") val roundType: String,
    @SerializedName("round-number") val roundNumber: Int,
    val scenario: String,
    val matches: List<MatchExport>,
)

data class MatchExport(
    val player1: String,
    @SerializedName("player1-id") val player1Id: String,
    val player2: String,
    @SerializedName("player2-id") val player2Id: String,
    @SerializedName("player1points") val player1Points: Int,
    @SerializedName("player2points") val player2Points: Int
)

fun md5(str: String): String = MessageDigest
    .getInstance("MD5")
    .digest(str.toByteArray(UTF_8))
    .toString()
