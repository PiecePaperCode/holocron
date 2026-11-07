package view

import controller.ServiceLocator
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox

class MainView(serviceLocator: ServiceLocator): ViewInterface {
    override val node = VBox()

    private fun metric(value: String, caption: String) = VBox(
        Label(value).apply { styleClass.add("metric-value") },
        Label(caption).apply { styleClass.add("eyebrow") }
    ).apply { styleClass.add("metric") }

    init {
        HBox.setHgrow(node, Priority.ALWAYS)
        node.id = "overview"
        node.styleClass.addAll("background", "welcome")
        val title = Label("Holocron").apply {
            styleClass.add("hero-title")
            isWrapText = true
        }
        val players = Button("MANAGE PLAYERS").apply {
            styleClass.add("primary")
            setOnAction { serviceLocator.setMainContent(PlayersView(serviceLocator).node) }
        }
        val tournament = Button("OPEN TOURNAMENT").apply {
            styleClass.add("secondary")
            setOnAction { serviceLocator.setMainContent(TournamentView(serviceLocator).node) }
        }
        val actions = HBox(players, tournament).apply { styleClass.add("gap") }
        val metrics = HBox(
            metric(serviceLocator.tournament.getPlayers().size.toString(), "REGISTERED PLAYERS"),
            metric(serviceLocator.tournament.getRounds().size.toString(), "ROUNDS PAIRED"),
            metric("SWISS", "TOURNAMENT FORMAT")
        ).apply { styleClass.add("metrics") }
        node.children.addAll(title, actions, metrics)

        val leaders = serviceLocator.tournament.getScores().take(3)
        if (leaders.isNotEmpty()) {
            val standings = GridPane().apply {
                styleClass.addAll("foreground", "leaderboard")
                columnConstraints.addAll(
                    ColumnConstraints(),
                    ColumnConstraints().apply { hgrow = Priority.ALWAYS },
                    ColumnConstraints()
                )
            }
            standings.add(Label("CURRENT TOP 3").apply { styleClass.add("eyebrow") }, 0, 0, 3, 1)
            leaders.forEachIndexed { index, score ->
                standings.add(Label(listOf("1st", "2nd", "3rd")[index]), 0, index + 1)
                standings.add(Label(score.player.name), 1, index + 1)
                standings.add(Label("${score.getPoints()} pts"), 2, index + 1)
            }
            node.children.add(standings)
        }
    }
}
