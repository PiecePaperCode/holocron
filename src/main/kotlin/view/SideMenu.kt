package view

import controller.ServiceLocator
import javafx.css.PseudoClass
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox

class SideMenu(serviceLocator: ServiceLocator): ViewInterface {
    override val node = VBox()
    private val selected = PseudoClass.getPseudoClass("selected")
    private val items = linkedMapOf<String, Button>()

    private fun item(id: String, text: String, handler: EventHandler<MouseEvent>): Button =
        Button(text).apply {
            maxWidth = Double.MAX_VALUE
            addEventHandler(MouseEvent.MOUSE_CLICKED, handler)
            items[id] = this
        }

    init {
        node.prefWidth = 220.0
        node.minWidth = 220.0
        node.styleClass.add("sidebar")
        val brand = VBox(
            Label("HOLOCRON").apply { styleClass.add("brand") },
            Label("X-WING TOURNAMENTS").apply { styleClass.add("eyebrow") }
        ).apply { styleClass.add("brand-block") }
        val top = VBox(
            item("overview", "OVERVIEW", EventHandler {
                serviceLocator.setMainContent(MainView(serviceLocator).node)
            }),
            item("players", "PLAYERS", serviceLocator.sideMenuEvents.selectPlayers),
            item("tournament", "TOURNAMENT", serviceLocator.sideMenuEvents.selectTournament),
            item("ranking", "RANKING", serviceLocator.sideMenuEvents.selectRanking)
        ).apply { styleClass.add("nav-group") }
        val spacer = VBox().apply { VBox.setVgrow(this, Priority.ALWAYS) }
        val bottom = VBox(
            item("import", "IMPORT", serviceLocator.sideMenuEvents.selectImport),
            item("export", "EXPORT", serviceLocator.sideMenuEvents.selectExport),
            item("settings", "SETTINGS", serviceLocator.sideMenuEvents.selectSettings)
        ).apply { styleClass.add("nav-group") }
        node.children.addAll(brand, top, spacer, bottom)
        select("overview")
    }

    fun select(id: String?) {
        items.forEach { (key, button) -> button.pseudoClassStateChanged(selected, key == id) }
    }
}
