package controller

import javafx.event.EventHandler
import javafx.scene.input.MouseEvent
import view.PlayersView
import view.RankingContent
import view.SettingsView
import view.TournamentView

class SideMenuEvents(serviceLocator: ServiceLocator) {
    val selectPlayers: EventHandler<MouseEvent> = EventHandler {
        serviceLocator.setMainContent(PlayersView(serviceLocator).node)
    }

    val selectRanking: EventHandler<MouseEvent> = EventHandler {
        serviceLocator.setMainContent(RankingContent(serviceLocator).node)
    }

    val selectTournament: EventHandler<MouseEvent> = EventHandler {
        serviceLocator.setMainContent(TournamentView(serviceLocator).node)
    }

    val selectSettings: EventHandler<MouseEvent> = EventHandler {
        serviceLocator.setMainContent(SettingsView(serviceLocator).node)
    }
}