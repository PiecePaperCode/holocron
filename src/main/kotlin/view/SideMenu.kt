package view

import controller.ServiceLocator
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox

class SideMenu(serviceLocator: ServiceLocator): ViewInterface {
    override val node = VBox()
    private val top = VBox()
    private val bottom = VBox()
    private val playersMenuItem = Button("Players")
    private val rankingMenuItem = Button("Ranking")
    private val tournamentMenuItem = Button("Tournament")
    private val exportMenuItem = Button("Export")
    private val settingsMenuItem = Button("Settings")

    init {
        node.minWidth = 200.0
        node.styleClass.addAll("dark")
        top.styleClass.addAll("gap")
        bottom.styleClass.addAll("gap")
        VBox.setVgrow(bottom, Priority.ALWAYS)
    }

    init {
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
        exportMenuItem.addEventHandler(
            MouseEvent.MOUSE_CLICKED,
            serviceLocator.sideMenuEvents.selectExport
        )
        settingsMenuItem.addEventHandler(
            MouseEvent.MOUSE_CLICKED,
            serviceLocator.sideMenuEvents.selectSettings
        )
    }

    init {
        top.children.addAll(
            playersMenuItem,
            rankingMenuItem,
            tournamentMenuItem
        )

        bottom.alignment = Pos.BOTTOM_LEFT
        bottom.children.addAll(
            exportMenuItem,
            settingsMenuItem
        )
        node.children.addAll(top, bottom)
    }
}