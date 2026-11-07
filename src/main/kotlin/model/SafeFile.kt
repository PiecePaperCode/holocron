package model

import java.nio.file.Path
import persistence.SqliteDatabase
import persistence.TournamentRepository

class SafeFile(
    private val database: Path = defaultDatabasePath()
) {
    companion object {
        internal fun defaultDatabasePath(
            launcher: String? = System.getProperty("jpackage.app-path"),
            codeLocation: Path = Path.of(SafeFile::class.java.protectionDomain.codeSource.location.toURI())
        ): Path {
            val directory = when {
                !launcher.isNullOrBlank() -> Path.of(launcher).toAbsolutePath().parent
                codeLocation.fileName.toString().endsWith(".jar", ignoreCase = true) ->
                    codeLocation.toAbsolutePath().parent
                else -> codeLocation.toAbsolutePath()
            }
            return directory.resolve("holocron.db")
        }
    }

    private val repository = TournamentRepository(SqliteDatabase(database))

    fun save(tournament: Tournament) = repository.save(tournament)

    fun load(): Tournament = repository.load()

    fun delete() = repository.delete()
}
