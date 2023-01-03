package view

import controller.ServiceLocator
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import model.Match

class TournamentView(private val serviceLocator: ServiceLocator): ViewInterface {
    override var node = VBox()
    private val title = Title("Tournament").node
    private val roundsTab = TabPane()
    private val tableScroll = ScrollPane()
    private val generateRound = Button("Generate Round")
    private val deleteRound = Button("Delete Last Round")
    private val buttonNode = HBox(generateRound, deleteRound)

    init {
        HBox.setHgrow(node, Priority.ALWAYS)
        node.styleClass.addAll("gap", "background")
        roundsTab.styleClass.addAll("foreground")
        tableScroll.content = roundsTab
        tableScroll.isFitToWidth = true
        buttonNode.styleClass.add("gap")
        node.children.addAll(
            title,
            tableScroll,
            buttonNode
        )
    }

    init {
        val rounds = serviceLocator.tournament.getRounds()
        for ((roundIndex, round) in rounds.withIndex()) {
            val matches = VBox()
            HBox.setHgrow(matches, Priority.ALWAYS)
            matches.styleClass.addAll("gap", "foreground")
            for ((index, match) in round.getMatches().withIndex()) {
                matches.children.add(generateMatchView(match, index+1))
            }
            val tab = Tab("Round${roundIndex + 1}")
            tab.content = matches
            tab.isClosable = false
            roundsTab.tabs.add(tab)
        }
        val lastTab = roundsTab.tabs.size - 1
        roundsTab.selectionModel.select(lastTab)

    }

    init {
        generateRound.addEventHandler(
            MouseEvent.MOUSE_CLICKED,
            serviceLocator.tournamentEvents.generateNewRound
        )
        deleteRound.addEventHandler(
            MouseEvent.MOUSE_CLICKED,
            serviceLocator.tournamentEvents.destroyLastRound
        )
    }

    private fun generateMatchView(match: Match, tableNumber: Int): GridPane {
        val textField1 = TextField(match.getEventPoints()[0].toString())
        val textField2 = TextField(match.getEventPoints()[1].toString())
        val textFieldList = listOf(textField1, textField2)
        for (field in textFieldList) {
            field.alignment = Pos.CENTER
            field.focusedProperty().addListener {
                _, _, gainedFocus ->
                if (
                    !gainedFocus
                    && textField1.text.toIntOrNull() != null
                    && textField2.text.toIntOrNull() != null
                ) {
                    serviceLocator.tournamentEvents.setMatchResult(
                        match,
                        textField1.text.toInt(),
                        textField2.text.toInt()
                    )
                }
            }
        }
        val grid = GridPane()
        grid.styleClass.addAll("gap", "background")
        val col = ColumnConstraints()
        col.percentWidth = 20.0
        grid.columnConstraints.addAll(
            ColumnConstraints(50.0),
            col,
            col,
            ColumnConstraints(10.0),
            col,
            col
        )
        val tableNumberLabel = Label("Nr. $tableNumber")
        grid.add(tableNumberLabel, 0, 0)
        val vs = Title(":").node
        grid.add(vs, 3, 0)

        for ((index, player) in match.getPlayers().withIndex()) {
            val playerName = Title(player.name, Title.Size.SMALL).node
            playerName.alignment = Pos.BASELINE_RIGHT
            when (player.name) {
                match.getWinner()?.name -> playerName.text = "${playerName.text} ✅"
                match.getLooser()?.name -> playerName.text = "${playerName.text} ❌"
            }
            if (index == 0) {
                playerName.prefWidth = 5000.0
                playerName.alignment = Pos.CENTER_RIGHT
                grid.add(playerName, 1, 0)
                grid.add(textFieldList[index], 2, 0)
            }
            if (index == 1) {
                grid.add(playerName, 5, 0)
                grid.add(textFieldList[index], 4, 0)
            }

        }
        return grid
    }
}