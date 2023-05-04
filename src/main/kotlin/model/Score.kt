package model

class Score(rounds: List<Round>) {
    private val stats = hashMapOf<Player, Stats>()
    private val matches = arrayListOf<Match>()

    init {
        rounds.forEach { round ->
            round.getMatches().forEach {
                match -> matches.add(match)
            }
        }
        matches.forEach{
            match -> run {
                for (player in match.getPlayers()) {
                    stats[player] = Stats(player)
                }
            }
        }
    }

    fun generateScores(): List<Stats> {
        generateStats()
        return stats.values
            .toList()
            .sortedWith(
                compareBy(
                    { it.getPoints() },
                    { it.getSOS() } ,
                    { it.getMissionPoints() },
                    { it.player.uniqueID }
                )
            )
            .reversed()
    }

    fun getOpponents(player: Player): List<Player> {
        val opponents = ArrayList<Player>()
        matches
            .filter { it.getPlayers().contains(player) }
            .forEach {
                    match ->
                when {
                    match.getPlayers().size == 1 -> {}
                    match.getPlayers()[0] == player ->
                        opponents.add(match.getPlayers()[1])
                    match.getPlayers()[1] == player ->
                        opponents.add(match.getPlayers()[0])
                }
            }
        return opponents
    }

    private fun generateStats() {
        matches.forEach{ creditStats(it) }
        matches.forEach{ creditMissionPoints(it) }
        creditSOS()
    }

    private fun creditStats(match: Match) {
        when (match.getResult()) {
            Match.Result.PLAYER1, Match.Result.PLAYER2 -> {
                stats[match.getWinner()]?.creditWin()
                stats[match.getLooser()]?.creditLoss()
            }
            Match.Result.DRAW ->
                match.getPlayers().forEach {
                    stats[it]?.creditDraw()
                }
            Match.Result.ONGOING -> { }
        }
    }

    private fun creditMissionPoints(match: Match) {
        when (match.getResult()) {
            Match.Result.PLAYER1, Match.Result.PLAYER2 -> {
                stats[match.getWinner()]
                    ?.creditMissionPoints(match.getEventPoints().max())
                stats[match.getLooser()]
                    ?.creditMissionPoints(match.getEventPoints().min())
            }
            Match.Result.DRAW ->
                match.getPlayers().forEach {
                    stats[it]?.creditMissionPoints(match.getEventPoints()[0])
                }
            Match.Result.ONGOING -> { }
        }
    }

    private fun creditSOS() {
        stats.forEach{
            (player, stat) -> run {
                val sumOpponentDivRounds = getOpponents(stat.player)
                    .sumOf { opponent ->
                        val opponentTotalPoints: Double = (
                            stats[opponent]?.getPoints() ?: 1.0
                        ).toDouble()
                        val opponentTotalRounds = getOpponents(opponent)
                            .size
                            .toDouble()
                        opponentTotalPoints / opponentTotalRounds
                    }
                var playerTotalRounds = getOpponents(stat.player).size.toDouble()
                if (playerTotalRounds == 0.0) {
                    playerTotalRounds = 1.0
                }
                val sos = sumOpponentDivRounds / playerTotalRounds
                stats[player]?.creditSOS(sos)
            }
        }
    }
}
