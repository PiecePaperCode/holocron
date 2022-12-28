package model

class Round (copyPlayers: List<Player>, copyPreviousRounds: List<Round>) {
    private var players = ArrayList<Player>(copyPlayers)
    private val previousRounds = ArrayList<Round>(copyPreviousRounds)
    private val matches = ArrayList<Match>()

    fun getMatches(): List<Match> {
        return matches
    }

    fun allMatchesDone(): Boolean {
        return !matches.all { it.getResult() != Match.Result.ONGOING }
    }

    fun pairSwiss(avoidRematches: Boolean = true) {
        val scores = Score(previousRounds)

        if (previousRounds.isNotEmpty()) {
            players = scores.generateScores().map { it.player } as ArrayList<Player>
        } else {
            players.shuffle()
        }

        for (index in 0 until players.size step 2) {
            val player1 = players[index]
            var player2 = if (index + 1 < players.size) players[index + 1] else null

            if (avoidRematches && player2 != null) {
                val opponents = scores.getOpponents(player1)
                if (opponents.contains(player2)) {
                    val availablePlayers = players.filter { it != player1 && it != player2 }
                    player2 = availablePlayers.firstOrNull { !opponents.contains(it) }
                }
            }

            matches.add(Match(player1, player2))
        }
    }
}