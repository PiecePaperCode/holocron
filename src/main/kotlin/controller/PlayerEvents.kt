package controller

import javafx.event.EventHandler
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.input.MouseEvent
import model.Player
import model.SafeFile
import view.CreatePlayer
import view.EditPlayer
import view.PlayersView

class PlayerEvents(serviceLocator: ServiceLocator) {
    val selectNewPlayer: EventHandler<MouseEvent> = EventHandler {
        serviceLocator.setMainContent(CreatePlayer(serviceLocator).node)
    }

    val createNewPlayer: (name: String, list: String) -> Unit = {
            name, list ->
        val newPlayer = Player(name, list)
        serviceLocator.tournament.addPlayer(newPlayer)
        serviceLocator.setMainContent(PlayersView(serviceLocator).node)
        SafeFile().save(serviceLocator.tournament)
    }

    val selectPlayer: (index: Int) -> Unit = {
            index ->
        serviceLocator.setMainContent(EditPlayer(serviceLocator, index).node)
    }

    val updatePlayer: (index: Int, name: String, list: String) -> Unit = {
            index, name, list ->
        val player = serviceLocator.tournament.getPlayers()[index]
        serviceLocator.tournament.getPlayers()[index] = Player(
            uniqueID = player.uniqueID,
            name = name,
            list = list
        )
        serviceLocator.setMainContent(PlayersView(serviceLocator).node)
        SafeFile().save(serviceLocator.tournament)
    }

    val deletePlayer: (index: Int) -> Unit = {
        val alert = Alert(Alert.AlertType.CONFIRMATION)
        val player = serviceLocator.tournament.getPlayers()[it]
        alert.headerText = "Delete Player?"
        alert.contentText = "Are you Sure you want to delete ${player.name}"
        alert.showAndWait()
        if (alert.result == ButtonType.OK) {
            serviceLocator.tournament.removePlayer(
                serviceLocator.tournament.getPlayers()[it]
            )
        }

        serviceLocator.setMainContent(PlayersView(serviceLocator).node)
        SafeFile().save(serviceLocator.tournament)
    }
}