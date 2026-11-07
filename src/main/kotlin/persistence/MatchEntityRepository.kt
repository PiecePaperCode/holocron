package persistence

import com.j256.ormlite.support.ConnectionSource

internal class MatchEntityRepository(source: ConnectionSource) :
    EntityRepository<MatchEntity, Void>(source, MatchEntity::class.java) {
    fun findByRound(roundId: Int): List<MatchEntity> = dao.queryBuilder().apply {
        orderBy("position", true)
        where().eq("round_id", roundId)
    }.query()
}
