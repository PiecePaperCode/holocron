package controller

import javafx.scene.control.Alert
import kotlinx.coroutines.runBlocking
import model.Player
import view.PlayersView

class ImportEvents(val serviceLocator: ServiceLocator) {
    val importPlayers: (id: String) -> Unit = {
        val alert = Alert(Alert.AlertType.ERROR)
        alert.contentText = "Please enter the id number of the tournament"
        var players: List<Player> = listOf()
        when {
            it.toIntOrNull() == null -> {
                alert.headerText = "ID is not a Number"
                alert.show()
            }
            else -> {
                runBlocking {
                    players = Import().fetch(it.toInt()).parse()
                }
            }

        }
        when {
            players.isEmpty() -> {
                alert.headerText = "ID is not a Valid Tournament"
                alert.show()
            }
            else -> {
                for (player in players) {
                    serviceLocator.tournament.addPlayer(player)
                }
                serviceLocator.setMainContent(PlayersView(serviceLocator).node)
            }
        }
    }
}