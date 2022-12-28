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
                    { it.player.name }
                )
            )
            .reversed()
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
                stats[match.getWinner()]?.creditMissionPoints(match.getEventPoints().max())
                stats[match.getLooser()]?.creditMissionPoints(match.getEventPoints().min())
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
            (player, _) -> run {
                val sumOpponentDivRounds = getOpponents(player)
                    .sumOf { opponent ->
                        val opponentTotalEventPoints = stats[opponent]?.getPoints() ?: 1
                        val opponentTotalRounds = getOpponents(opponent).size
                        opponentTotalEventPoints / opponentTotalRounds
                    }
                var playerTotalRounds = getOpponents(player).size
                if (playerTotalRounds == 0) {
                    playerTotalRounds = 1
                }
                val sos = sumOpponentDivRounds / playerTotalRounds
                stats[player]?.creditSOS(sos)
            }
        }
    }

    fun getOpponents(player: Player): List<Player> {
        val opponents = ArrayList<Player>()
        matches
            .filter { it.getPlayers().contains(player) }
            .forEach {
                match -> when {
                    match.getPlayers().size == 1 -> {}
                    match.getPlayers()[0] == player -> opponents.add(match.getPlayers()[1])
                    match.getPlayers()[1] == player -> opponents.add(match.getPlayers()[0])
                }
            }
        return opponents
    }
}
