package controller

import javafx.event.EventHandler
import javafx.scene.control.Alert
import javafx.scene.input.MouseEvent
import model.Player
import view.CreatePlayer
import view.PlayersContent

class PlayerEvents(serviceLocator: ServiceLocator) {
    val selectNewPlayer: EventHandler<MouseEvent> = EventHandler {
        serviceLocator.createPlayer = CreatePlayer(serviceLocator)
        serviceLocator.setMainContent(serviceLocator.createPlayer.node)
    }

    val createNewPlayer: EventHandler<MouseEvent> = EventHandler {
        val newPlayer = Player(serviceLocator.createPlayer.getPlayerName())
        if (serviceLocator.tournament.getRounds().size > 0) {
            val alert = Alert(Alert.AlertType.ERROR)
            alert.headerText = "The Tournament started already"
            alert.contentText = "Players can't late join"
            alert.show()
            return@EventHandler
        }
        serviceLocator.tournament.addPlayer(newPlayer)
        serviceLocator.setMainContent(PlayersContent(serviceLocator).node)
    }

    val deletePlayer: (index: Int) -> Unit = {
        serviceLocator.tournament.removePlayer(
            serviceLocator.tournament.getPlayers()[it]
        )
        serviceLocator.setMainContent(PlayersContent(serviceLocator).node)
    }
}