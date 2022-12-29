package model

class Match(
    private val player1: Player,
    private val player2: Player?
) {
    private var eventPoints1 = 0
    private var eventPoints2 = 0
    private var ongoing = true

    init {
        if (player2 == null) {
            setMatchResult(18, 0)
        }
    }

    fun setMatchResult(points1: Int, points2: Int): Result {
        eventPoints1 = points1
        eventPoints2 = points2
        ongoing = false
        return when {
            points1 > points2 -> Result.PLAYER1
            points1 < points2 -> Result.PLAYER2
            else -> Result.DRAW
        }
    }

    fun getResult(): Result {
        if (ongoing) {
            return Result.ONGOING
        }
        return setMatchResult(eventPoints1, eventPoints2)
    }

    fun getPlayers(): List<Player> {
        if (player2 == null) {
            return listOf(player1)
        }
        return listOf(player1, player2)
    }

    fun getWinner(): Player? {
        return when {
            getResult() == Result.PLAYER1 -> player1
            getResult() == Result.PLAYER2 -> player2
            else -> null
        }
    }

    fun getLooser(): Player? {
        return when {
            getResult() == Result.PLAYER1 -> player2
            getResult() == Result.PLAYER2 -> player1
            else -> null
        }
    }

    fun getEventPoints(): List<Int> {
        return listOf(eventPoints1, eventPoints2)
    }

    enum class Result {
        PLAYER1, PLAYER2, DRAW, ONGOING
    }
}
