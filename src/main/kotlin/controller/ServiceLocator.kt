package controller

import javafx.scene.layout.VBox
import model.SafeFile
import view.Layout
import view.SideMenu

class ServiceLocator() {
    var tournament = SafeFile().load()



    val sideMenuEvents = SideMenuEvents(this)
    val sideMenu = SideMenu(this)

    val playerEvents = PlayerEvents(this)

    val tournamentEvents = TournamentEvents(this)

    val settingsEvents = SettingsEvents(this)

    val layout = Layout(this)

    fun setMainContent(node: VBox) {
        layout.mainView = node
        layout.update()
    }
}