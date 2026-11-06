package model

class Tournament {
    var name = ""

    private val players = ArrayList<Player>()

    fun addPlayer(player: Player) {
        players.add(player)
    }

    fun getPlayers(): ArrayList<Player> = players

    fun updatePlayer(name: String, list: String, uniqueID: String) {
        val updatedPlayer = Player(name, list, uniqueID)
        players.forEachIndexed { index, player ->
            if (player == updatedPlayer) {
                players[index] = updatedPlayer
                return
            }
        }
    }

    fun removePlayer(player: Player) {
        players.remove(player)
    }

    private val rounds = ArrayList<Round>()

    fun nextRound() {
        if (!canGenerateNextRound()) return
        val round = Round(ArrayList(players), ArrayList(rounds))
        round.pairSwiss()
        if (round.getMatches().isNotEmpty()) rounds.add(round)
    }

    fun canGenerateNextRound(): Boolean = players.size >= 2 && rounds.size < players.size - 1

    fun isFinished(): Boolean = rounds.isNotEmpty() && !canGenerateNextRound() &&
        rounds.all { it.allMatchesDone() }

    fun getRounds(): ArrayList<Round> {
        return rounds
    }

    fun deleteCurrentRound() {
        rounds.removeAt(rounds.size - 1)
    }

    fun getScores(): List<Stats> {
        val score = Score(players, rounds)
        return score.generateScores()
    }
}
