package model

import com.google.gson.Gson
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Files
import java.nio.file.Path
import persistence.SqliteDatabase
import persistence.TournamentEntity
import persistence.TournamentEntityRepository

class TestSafeFile {
    @TempDir lateinit var directory: Path
    private fun store() = SafeFile(directory.resolve("data/holocron.db"))

    @Test fun `database is next to native launcher jar or compiled classes`() {
        val launcher = directory.resolve("app with spaces/holocron.exe")
        val jar = directory.resolve("lib/holocron.jar")
        assertEquals(launcher.parent.resolve("holocron.db"),
            SafeFile.defaultDatabasePath(launcher.toString(), jar))
        assertEquals(jar.parent.resolve("holocron.db"),
            SafeFile.defaultDatabasePath(null, jar))
        assertEquals(directory.resolve("classes/holocron.db"),
            SafeFile.defaultDatabasePath(null, directory.resolve("classes")))
    }

    @Test fun `round trip preserves players results byes and ongoing games`() {
        val tournament = Tournament().apply { name = "Torneo 日本 ' test" }
        repeat(5) { tournament.addPlayer(Player("Player $it", "List\n$it", "id-$it")) }
        tournament.nextRound()
        tournament.getRounds()[0].getMatches()[0].setMatchResult(12, 12)
        val before = Gson().toJson(tournament.getScores())
        store().save(tournament)
        val restored = store().load()
        assertEquals(tournament.name, restored.name)
        assertEquals(tournament.getPlayers().map { it.uniqueID }, restored.getPlayers().map { it.uniqueID })
        tournament.getRounds()[0].getMatches().zip(restored.getRounds()[0].getMatches()).forEach { (a, b) ->
            assertEquals(a.getPlayers().map { it.uniqueID }, b.getPlayers().map { it.uniqueID })
            assertEquals(a.getEventPoints(), b.getEventPoints())
            assertEquals(a.getResult(), b.getResult())
        }
        assertEquals(before, Gson().toJson(restored.getScores()))
        restored.nextRound()
        assertEquals(2, restored.getRounds().size)
        store().save(restored)
        assertEquals(2, store().load().getRounds().size)
        assertEquals("SQLite format 3", String(Files.readAllBytes(directory.resolve("data/holocron.db")).take(15).toByteArray()))
    }

    @Test fun `reset persists an empty tournament`() {
        store().save(Tournament().apply { name = "Saved"; addPlayer(Player("Alice")) })
        store().delete()
        val restored = store().load()
        assertTrue(restored.getPlayers().isEmpty())
        assertTrue(restored.getRounds().isEmpty())
        assertEquals("", restored.name)
    }

    @Test fun `failed save rolls back previous tournament`() {
        store().save(Tournament().apply { name = "Original" })
        assertThrows(Exception::class.java) {
            SqliteDatabase(directory.resolve("data/holocron.db")).transaction { source ->
                val tournaments = TournamentEntityRepository(source)
                tournaments.deleteAll()
                tournaments.insert(TournamentEntity(name = "Replacement"))
                tournaments.insert(TournamentEntity(name = "Duplicate"))
            }
        }
        assertEquals("Original", store().load().name)
    }

    @Test fun `fresh storage and overwrite preserve empty rounds and historical player details`() {
        assertTrue(store().load().getPlayers().isEmpty())
        val tournament = Tournament()
        val old = Player("Old name", "old list", "id")
        val other = Player("Other", uniqueID = "other")
        tournament.addPlayer(old)
        tournament.addPlayer(other)
        tournament.getRounds().add(Round(listOf(old, other), emptyList(), listOf(Match(old, other))))
        tournament.getRounds().add(Round(emptyList(), emptyList(), emptyList()))
        tournament.updatePlayer("New name", "new list", "id")
        store().save(tournament)
        val restored = store().load()
        assertEquals("New name", restored.getPlayers()[0].name)
        assertEquals("Old name", restored.getRounds()[0].getMatches()[0].getPlayers()[0].name)
        assertTrue(restored.getRounds()[1].getMatches().isEmpty())
        store().save(Tournament())
        assertTrue(store().load().getRounds().isEmpty())
    }
}
