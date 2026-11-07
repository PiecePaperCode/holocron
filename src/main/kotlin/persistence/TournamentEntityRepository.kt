package persistence

import com.j256.ormlite.support.ConnectionSource

internal class TournamentEntityRepository(source: ConnectionSource) :
    EntityRepository<TournamentEntity, Int>(source, TournamentEntity::class.java) {
    fun find(): TournamentEntity? = dao.queryForId(1)
}
