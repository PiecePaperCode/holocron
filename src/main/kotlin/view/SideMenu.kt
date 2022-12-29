package view

import controller.ServiceLocator
import javafx.scene.control.*
import javafx.scene.input.MouseEvent
import javafx.scene.layout.VBox

class SideMenu(serviceLocator: ServiceLocator): Interface {
    override val node = VBox()
    private val playersMenuItem = Button("Players")
    private val rankingMenuItem = Button("Ranking")
    private val tournamentMenuItem = Button("Tournament")
    // private val settingsMenuItem = Button("Settings")

    init {
        node.minWidth = 200.0
        node.styleClass.addAll("gap", "dark")
        playersMenuItem.addEventHandler(
            MouseEvent.MOUSE_CLICKED,
            serviceLocator.sideMenuEvents.selectPlayers
        )
        rankingMenuItem.addEventHandler(
            MouseEvent.MOUSE_CLICKED,
            serviceLocator.sideMenuEvents.selectRanking
        )
        tournamentMenuItem.addEventHandler(
            MouseEvent.MOUSE_CLICKED,
            serviceLocator.sideMenuEvents.selectTournament
        )
        node.children.addAll(
            playersMenuItem,
            rankingMenuItem,
            tournamentMenuItem
        )
    }
}