package persistence

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.support.ConnectionSource

internal open class EntityRepository<T : Any, ID>(source: ConnectionSource, entityClass: Class<T>) {
    protected val dao: Dao<T, ID> = DaoManager.createDao(source, entityClass)

    fun insert(entity: T) {
        dao.create(entity)
    }

    fun deleteAll() {
        dao.delete(dao.deleteBuilder().prepare())
    }
}
