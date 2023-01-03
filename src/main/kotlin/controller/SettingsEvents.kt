package controller

import javafx.event.EventHandler
import javafx.scene.input.MouseEvent
import model.SafeFile
import model.Tournament

class SettingsEvents(serviceLocator: ServiceLocator) {
    val reset: EventHandler<MouseEvent> = EventHandler {
        SafeFile().delete()
        serviceLocator.tournament = Tournament()
        SafeFile().save(serviceLocator.tournament)
    }
}