package model

class Round (copyPlayers: List<Player>, copyPreviousRounds: List<Round>) {
    private var players = ArrayList<Player>(copyPlayers)
    private val previousRounds = ArrayList<Round>(copyPreviousRounds)
    private var matches = ArrayList<Match>()
    private val byes = mutableListOf<Player>()

    init {
        previousRounds.forEach {
            it.getMatches().forEach{
                    match ->
                if (match.getPlayers().size == 1) {
                    byes.add(match.getPlayers().first())
                }
            }
        }
    }

    fun getMatches(): List<Match> {
        return matches
    }

    fun allMatchesDone(): Boolean {
        return !matches.all { it.getResult() != Match.Result.ONGOING }
    }

    fun pairSwiss() {
        if (previousRounds.size >= players.size - 1) {
            return
        }

        val scores = Score(previousRounds)
        val sortedPlayers = arrayListOf<Player>()

        if (previousRounds.isEmpty()){
            sortedPlayers.addAll(players)
            sortedPlayers.shuffle()
        }
        else {
            sortedPlayers.addAll(
                scores.generateScores()
                    .map { it.player }
                    .filter { it in players }
            )
            sortedPlayers.addAll(players.filter { it !in sortedPlayers })
        }

        var byeMatch: Match? = null
        if (players.size % 2 == 1) {
            for (player in sortedPlayers.reversed()) {
                if (!byes.contains(player)) {
                    byeMatch = Match(player, null)
                    sortedPlayers.remove(player)
                    break
                }
            }
        }

        while (sortedPlayers.isNotEmpty()) {
            val player1 = sortedPlayers.first()
            sortedPlayers.remove(player1)

            val opponents = scores.getOpponents(player1)
            val availablePlayers = sortedPlayers
                .filter { !opponents.contains(it) }

            val player2 = if (availablePlayers.isNotEmpty()) {
                availablePlayers.take(3).random()
            } else {
                matches = arrayListOf()
                pairSwiss()
                return
            }
            sortedPlayers.remove(player2)
            matches.add(Match(player1, player2))
        }

        if (byeMatch != null) {
            matches.add(byeMatch)
        }
    }
}
