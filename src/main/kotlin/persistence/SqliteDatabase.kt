package persistence

import com.j256.ormlite.jdbc.DataSourceConnectionSource
import com.j256.ormlite.misc.TransactionManager
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import org.sqlite.SQLiteConfig
import org.sqlite.SQLiteDataSource
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.Callable

class SqliteDatabase(private val path: Path) {
    internal fun <T> transaction(action: (ConnectionSource) -> T): T {
        Files.createDirectories(path.toAbsolutePath().parent)
        val url = "jdbc:sqlite:${path.toAbsolutePath()}"
        val config = SQLiteConfig().apply {
            enforceForeignKeys(true)
            setBusyTimeout(5000)
        }
        val dataSource = SQLiteDataSource(config).apply { setUrl(url) }
        return DataSourceConnectionSource(dataSource, url).use { source ->
            TableUtils.createTableIfNotExists(source, TournamentEntity::class.java)
            TableUtils.createTableIfNotExists(source, PlayerEntity::class.java)
            TableUtils.createTableIfNotExists(source, RoundEntity::class.java)
            TableUtils.createTableIfNotExists(source, MatchEntity::class.java)
            TransactionManager.callInTransaction(source, Callable { action(source) })
        }
    }
}
