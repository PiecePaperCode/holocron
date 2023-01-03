package controller

import javafx.event.EventHandler
import javafx.scene.control.Alert
import javafx.scene.input.MouseEvent
import model.Player
import model.SafeFile
import view.CreatePlayer
import view.PlayersView

class PlayerEvents(serviceLocator: ServiceLocator) {
    val selectNewPlayer: EventHandler<MouseEvent> = EventHandler {
        serviceLocator.setMainContent(CreatePlayer(serviceLocator).node)
    }

    val createNewPlayer: (name: String) -> Unit = {
        val newPlayer = Player(it)
        if (serviceLocator.tournament.getRounds().size > 0) {
            val alert = Alert(Alert.AlertType.ERROR)
            alert.headerText = "The Tournament started already"
            alert.contentText = "Players can't late join"
            alert.show()
        } else {
            serviceLocator.tournament.addPlayer(newPlayer)
            serviceLocator.setMainContent(PlayersView(serviceLocator).node)
        }
        SafeFile().save(serviceLocator.tournament)
    }

    val deletePlayer: (index: Int) -> Unit = {
        serviceLocator.tournament.removePlayer(
            serviceLocator.tournament.getPlayers()[it]
        )
        serviceLocator.setMainContent(PlayersView(serviceLocator).node)
        SafeFile().save(serviceLocator.tournament)
    }
}