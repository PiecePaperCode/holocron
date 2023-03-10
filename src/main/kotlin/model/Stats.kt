package model

class Stats(
    val player: Player,
) {
    private var points = 0
    private var sos = 0.0
    private var missionPoints = 0
    private var wins = 0
    private var losses = 0
    private var draws = 0

    fun creditWin() {
        points += Points.WIN
        wins ++
    }

    fun creditLoss() {
        losses ++
    }

    fun creditDraw() {
        points += Points.DRAW
        draws ++
    }

    fun creditSOS(sos: Double) {
        this.sos += sos
    }

    fun creditMissionPoints(missionPoints: Int) {
        this.missionPoints += missionPoints
    }

    fun getPoints(): Int {
        return points
    }

    fun getSOS(): Double {
        return sos
    }

    fun getMissionPoints(): Int {
        return missionPoints
    }

    override fun toString(): String {
        return "$wins/$losses/$draws"
    }
}

class Points {
    companion object {
        const val WIN = 3
        const val DRAW = 1
    }
}
