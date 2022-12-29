package view

import controller.ServiceLocator
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*

class RankingContent(serviceLocator: ServiceLocator): Interface {
    override var node = VBox()
    private val title = Title("Ranking").node
    private val tableScroll = ScrollPane()
    private val table = GridPane()

    init {
        HBox.setHgrow(node, Priority.ALWAYS)
        table.styleClass.addAll("gap", "foreground")
        table.add(Title("Name", Title.Size.SMALL).node, 0, 0)
        table.add(Label("Points"), 1, 0)
        table.add(Label("SOS"), 2, 0)
        table.add(Label("Mission-Points"), 3, 0)
        table.add(Label("Stats"), 4, 0)

        val scores = serviceLocator.tournament.getScores()
        for ((index, score) in scores.withIndex()) {
            table.add(Label(score.player.name), 0, index + 1)
            table.add(Label(score.getPoints().toString()), 1, index + 1)
            table.add(Label(score.getSOS().toString()), 2, index + 1)
            table.add(Label(score.getMissionPoints().toString()), 3, index + 1)
            table.add(Label(score.toString()), 4, index + 1)

        }
        val col = ColumnConstraints()
        col.percentWidth = 20.0
        table.columnConstraints.addAll(
            col, col, col, col, col
        )
        tableScroll.content = table
        tableScroll.isFitToWidth = true
    }

    init {
        node.styleClass.addAll("gap", "background")
        node.children.addAll(
            title,
            tableScroll,
        )
    }
}