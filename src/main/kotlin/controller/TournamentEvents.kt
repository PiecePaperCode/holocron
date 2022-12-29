package controller

import javafx.event.EventHandler
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.input.MouseEvent
import model.Match
import view.TournamentContent

class TournamentEvents(serviceLocator: ServiceLocator) {
    val generateNewRound: EventHandler<MouseEvent> = EventHandler {
        serviceLocator.tournament.nextRound()
        serviceLocator.setMainContent(TournamentContent(serviceLocator).node)
    }

    val destroyLastRound: EventHandler<MouseEvent> = EventHandler {
        val alert = Alert(Alert.AlertType.CONFIRMATION)
        alert.headerText = "Delete Last Round?"
        alert.contentText = "Are you Sure you want to delete the last Round"
        alert.showAndWait()
        if (alert.result == ButtonType.OK) {
            serviceLocator.tournament.deleteCurrentRound()
        }
        serviceLocator.setMainContent(TournamentContent(serviceLocator).node)
    }

    val setMatchResult: (match: Match, point1: Int, point2: Int) -> Unit = {
        match, point1, point2 -> serviceLocator
            .tournament
            .getRounds()
            .forEach{ round ->
                round.getMatches().forEach{
                    if (it == match) {
                        it.setMatchResult(point1, point2)
                    }
                }
            }
            serviceLocator
                .setMainContent(TournamentContent(serviceLocator).node)
    }
}