package persistence

import model.Match
import model.Player
import model.Round
import model.Tournament

class TournamentRepository(private val database: SqliteDatabase) {
    fun save(tournament: Tournament) = database.transaction { connection ->
        val tournaments = TournamentEntityRepository(connection)
        val players = PlayerEntityRepository(connection)
        val rounds = RoundEntityRepository(connection)
        val matches = MatchEntityRepository(connection)

        matches.deleteAll()
        rounds.deleteAll()
        players.deleteAll()
        tournaments.deleteAll()
        tournaments.insert(TournamentEntity(name = tournament.name))

        var nextId = 0
        fun insertPlayer(player: Player, position: Int? = null): PlayerEntity {
            val id = nextId++
            val entity = PlayerEntity(id, position, player.uniqueID, player.name, player.list)
            players.insert(entity)
            return entity
        }

        tournament.getPlayers().forEachIndexed { index, player -> insertPlayer(player, index) }
        tournament.getRounds().forEachIndexed { roundIndex, round ->
            val savedRound = RoundEntity(roundIndex)
            rounds.insert(savedRound)
            round.getMatches().forEachIndexed { index, match ->
                val participants = match.getPlayers()
                matches.insert(MatchEntity(
                    savedRound, index,
                    insertPlayer(participants[0]), participants.getOrNull(1)?.let { insertPlayer(it) },
                    match.getEventPoints()[0], match.getEventPoints()[1],
                    match.getResult() == Match.Result.ONGOING
                ))
            }
        }
    }

    fun load(): Tournament = database.transaction { connection ->
        val saved = TournamentEntityRepository(connection).find() ?: return@transaction Tournament()
        val tournament = Tournament().apply { name = saved.name }
        val savedPlayers = PlayerEntityRepository(connection).findAll()
        val players = savedPlayers.associate { it.id to Player(it.name, it.squad, it.uniqueId) }
        savedPlayers.filter { it.activePosition != null }.forEach { tournament.addPlayer(players.getValue(it.id)) }

        val matches = MatchEntityRepository(connection)
        RoundEntityRepository(connection).findAll().forEach { round ->
            val restoredMatches = matches.findByRound(round.id).map { savedMatch ->
                Match(players.getValue(savedMatch.player1.id), savedMatch.player2?.let { players.getValue(it.id) }).apply {
                    if (!savedMatch.ongoing) setMatchResult(savedMatch.points1, savedMatch.points2)
                }
            }
            tournament.getRounds().add(Round(tournament.getPlayers(), tournament.getRounds(), restoredMatches))
        }
        tournament
    }

    fun delete() = save(Tournament())
}
