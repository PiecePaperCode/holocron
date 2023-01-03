package view

import controller.ServiceLocator
import javafx.scene.control.*
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*

class PlayersView(serviceLocator: ServiceLocator): ViewInterface {
    override var node = VBox()
    private val title = Title("Players").node
    private val tableScroll = ScrollPane()
    private val table = GridPane()
    private val addPlayerButton = Button("Add Player")

    init {
        table.styleClass.addAll("gap", "foreground")
        table.add(Title("Name", Title.Size.SMALL).node, 0, 0)
        table.add(Title("", Title.Size.SMALL).node, 1, 0)

        val players = serviceLocator.tournament.getPlayers()
        for ((index, player) in players.withIndex()) {
            table.add(Label(player.name), 0, index + 1)
            val button = Button("Remove")
            button.addEventHandler(
                MouseEvent.MOUSE_CLICKED
            ) { serviceLocator.playerEvents.deletePlayer(index) }
            table.add(button, 1, index + 1)
        }
        val col1 = ColumnConstraints()
        col1.percentWidth = 85.0
        val col2 = ColumnConstraints()
        col2.percentWidth = 15.0
        table.columnConstraints.addAll(col1, col2)
        tableScroll.content = table
        tableScroll.isFitToWidth = true
    }

    init {
        addPlayerButton.addEventHandler(
            MouseEvent.MOUSE_CLICKED,
            serviceLocator.playerEvents.selectNewPlayer
        )
    }

    init {
        HBox.setHgrow(node, Priority.ALWAYS)
        node.styleClass.addAll("gap", "background")
        node.children.addAll(
            title,
            tableScroll,
            addPlayerButton
        )
    }
}