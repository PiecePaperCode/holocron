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
        serviceLocator.tournament.addPlayer(newPlayer)
        serviceLocator.setMainContent(PlayersView(serviceLocator).node)
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