package controller

import javafx.event.EventHandler
import javafx.scene.input.MouseEvent
import view.PlayersContent
import view.RankingContent
import view.TournamentContent

class SideMenuEvents(serviceLocator: ServiceLocator) {
    val selectPlayers: EventHandler<MouseEvent> = EventHandler {
        serviceLocator.setMainContent(PlayersContent(serviceLocator).node)
    }

    val selectRanking: EventHandler<MouseEvent> = EventHandler {
        serviceLocator.setMainContent(RankingContent(serviceLocator).node)
    }

    val selectTournament: EventHandler<MouseEvent> = EventHandler {
        serviceLocator.setMainContent(TournamentContent(serviceLocator).node)
    }
}