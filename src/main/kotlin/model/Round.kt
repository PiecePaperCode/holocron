package model

class Round (copyPlayers: List<Player>, copyPreviousRounds: List<Round>) {
    private var players = ArrayList<Player>(copyPlayers)
    private val previousRounds = ArrayList<Round>(copyPreviousRounds)
    private val matches = ArrayList<Match>()
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
        }

        // Pair players, avoiding rematches and giving byes to the lowest scoring players
        while (sortedPlayers.isNotEmpty()) {
            val player1 = sortedPlayers.first()
            sortedPlayers.remove(player1)

            val opponents = scores.getOpponents(player1)
            val availablePlayers = sortedPlayers
                .filter { !opponents.contains(it) }

            val player2 = if (availablePlayers.isNotEmpty()) {
                availablePlayers.first()
            } else{
                byes.add(player1)
                null
            }

            if (player2 != null) {
                sortedPlayers.remove(player2)
            }

            matches.add(Match(player1, player2))
        }

    }
}