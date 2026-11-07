package persistence

import com.j256.ormlite.support.ConnectionSource

internal class RoundEntityRepository(source: ConnectionSource) :
    EntityRepository<RoundEntity, Int>(source, RoundEntity::class.java) {
    fun findAll(): List<RoundEntity> = dao.queryBuilder().orderBy("id", true).query()
}
