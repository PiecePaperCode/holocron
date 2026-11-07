package persistence

import com.j256.ormlite.support.ConnectionSource

internal class PlayerEntityRepository(source: ConnectionSource) :
    EntityRepository<PlayerEntity, Int>(source, PlayerEntity::class.java) {
    fun findAll(): List<PlayerEntity> = dao.queryBuilder()
        .orderBy("active_position", true)
        .orderBy("id", true)
        .query()
}
