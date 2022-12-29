package controller

import javafx.scene.Node
import javafx.scene.layout.VBox
import model.Player
import model.Tournament
import view.CreatePlayer
import view.Layout
import view.SideMenu
import view.TournamentContent

class ServiceLocator {
    val tournament = Tournament()

    init {
        tournament.addPlayer(Player("Marcos"))
        tournament.addPlayer(Player("David"))
        tournament.addPlayer(Player("Daniel"))
        tournament.addPlayer(Player("Irina"))
    }

    val sideMenuEvents = SideMenuEvents(this)
    val sideMenu = SideMenu(this)

    val playerEvents = PlayerEvents(this)
    var createPlayer = CreatePlayer(this)

    val tournamentEvents = TournamentEvents(this)

    val layout = Layout(this)

    fun setMainContent(node: VBox) {
        layout.mainContent = node
        layout.update()
    }
}