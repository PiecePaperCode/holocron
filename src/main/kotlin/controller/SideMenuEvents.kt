package controller

import javafx.event.EventHandler
import javafx.scene.input.MouseEvent
import view.*

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

    val selectImport: EventHandler<MouseEvent> = EventHandler {
        serviceLocator.setMainContent(ImportView(serviceLocator).node)
    }

    val selectExport: EventHandler<MouseEvent> = EventHandler {
        serviceLocator.setMainContent(ExportView(serviceLocator).node)
    }

    val selectSettings: EventHandler<MouseEvent> = EventHandler {
        serviceLocator.setMainContent(SettingsView(serviceLocator).node)
    }
}