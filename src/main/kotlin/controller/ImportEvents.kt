package controller

import kotlinx.coroutines.runBlocking
import view.PlayersView

class ImportEvents(val serviceLocator: ServiceLocator) {
    val importPlayers: (id: Int) -> Unit = {
        runBlocking {
            val players = Import().fetch(it).parse()
            for (player in players) {
                serviceLocator.tournament.addPlayer(player)
            }
            serviceLocator.setMainContent(PlayersView(serviceLocator).node)
        }
    }
}