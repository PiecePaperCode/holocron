package view

import controller.ServiceLocator
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.input.MouseEvent
import javafx.scene.layout.*
import model.Match

class TournamentView(
    private val serviceLocator: ServiceLocator,
    selectedRoundIndex: Int = serviceLocator.tournament.getRounds().lastIndex
): ViewInterface {
    override var node = VBox()
    private val title = Title("Tournament").node
    private val nameLabel = Label("Name")
    private val name = TextField(serviceLocator.tournament.name)
    private val nameNode = HBox(nameLabel, name)
    private val roundsTab = TabPane()
    private val tableScroll = ScrollPane()
    private val generateRound = Button("Generate Round").apply { styleClass.add("primary") }
    private val deleteRound = Button("Delete Last Round").apply { styleClass.add("danger") }
    private val buttonNode = HBox(generateRound, deleteRound)

    init {
        node.id = "tournament"
        HBox.setHgrow(node, Priority.ALWAYS)
        node.styleClass.addAll("gap", "background")
        roundsTab.styleClass.addAll("foreground")
        tableScroll.content = roundsTab
        tableScroll.isFitToWidth = true
        name.prefWidth = 500.0
        nameNode.styleClass.addAll("gap", "foreground")
        buttonNode.styleClass.add("gap")
        node.children.addAll(
            title,
            nameNode,
            tableScroll,
            buttonNode
        )
        generateRound.isDisable = !serviceLocator.tournament.canGenerateNextRound()
        if (serviceLocator.tournament.isFinished()) {
            node.children.add(Label("Tournament finished"))
        }
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
            val tab = Tab("ROUND ${roundIndex + 1}")
            tab.content = matches
            tab.isClosable = false
            roundsTab.tabs.add(tab)
        }
        val lastTab = roundsTab.tabs.size - 1
        if (lastTab >= 0) {
            roundsTab.selectionModel.select(selectedRoundIndex.coerceIn(0, lastTab))
        }

    }

    init {
        name.focusedProperty().addListener{
            _, _, gainedFocus ->
                if (!gainedFocus) {
                    serviceLocator.tournamentEvents.nameTournament(name.text)
                }
        }
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
        grid.styleClass.addAll("gap", "match")
        grid.columnConstraints.addAll(
            listOf(8.0, 28.0, 12.0, 4.0, 12.0, 36.0).map { width ->
                ColumnConstraints().apply { percentWidth = width }
            }
        )
        val tableNumberLabel = Label("Nr. $tableNumber")
        grid.add(tableNumberLabel, 0, 0)
        val vs = Title(":").node
        grid.add(vs, 3, 0)

        for ((index, player) in match.getPlayers().withIndex()) {
            val playerName = Label(player.name).apply {
                styleClass.add("match-player")
                isWrapText = true
            }
            playerName.alignment = Pos.BASELINE_RIGHT
            when (player.name) {
                match.getWinner()?.name -> playerName.text = "${playerName.text} ✔️"
                match.getLooser()?.name -> playerName.text = "${playerName.text} ❌"
            }
            if (index == 0) {
                playerName.maxWidth = Double.MAX_VALUE
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
