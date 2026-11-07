package view

import controller.ServiceLocator
import javafx.scene.control.Button
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox

class SettingsView(serviceLocator: ServiceLocator): ViewInterface {
    override var node = VBox()
    private val resetButton = Button("Reset Everything").apply { styleClass.add("danger") }

    init {
        resetButton.addEventHandler(
            MouseEvent.MOUSE_CLICKED,
            serviceLocator.settingsEvents.reset
        )
    }

    init {
        node.id = "settings"
        HBox.setHgrow(node, Priority.ALWAYS)
        node.styleClass.addAll("gap", "background")
        node.children.addAll(
            Title("Settings").node,
            resetButton
        )
    }

}