package model

class Tournament {
    var name = ""

    private val players = ArrayList<Player>()

    fun addPlayer(player: Player) {
        players.add(player)
    }

    fun getPlayers(): ArrayList<Player> = players

    fun removePlayer(player: Player) {
        players.remove(player)
    }

    private val rounds = ArrayList<Round>()

    fun nextRound() {
        val round = Round(ArrayList(players), ArrayList(rounds))
        round.pairSwiss()
        rounds.add(round)
    }

    fun getRounds(): ArrayList<Round> {
        return rounds
    }

    fun deleteCurrentRound() {
        rounds.removeAt(rounds.size - 1)
    }

    fun getScores(): List<Stats> {
        val score = Score(rounds)
        return score.generateScores()
    }

}