package controller

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import model.Player

class Import(private val baseUrl: String = "https://www.tabletoptournaments.net") {
    private var html: String = ""
    private val regex  = "<a href=\"/player/(.+)\" title=\"(.+)\">&quot;(.+)&quot;</a>".toRegex()

    fun parse(html: String): List<Player> {
        val players = regex.findAll(html)
            .map {
                Player(it.groupValues[3])
            }.toList()
        return players
    }
    fun parse(): List<Player> {
        val players = regex.findAll(html)
            .map {
                Player(it.groupValues[3])
            }.toList()
        return players
    }
    suspend fun fetch(tournamentId: Int): Import {
        val url = "$baseUrl/t3_tournament_list.php?tid=${tournamentId}"
        HttpClient(CIO) { expectSuccess = true }.use { client ->
            val response: HttpResponse = client.get(url)
            html = response.body()
        }
        return this
    }
}