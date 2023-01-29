package view

import controller.ServiceLocator
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox

class ImportView(serviceLocator: ServiceLocator) : ViewInterface {
    override var node = VBox()
    private val title = Title("Import").node
    private val description = Label("""
        Import your Tournament from t3.
        Enter your tournament id into the field to import players.
    """.trimIndent())
    private val textField = TextField()
    private val importButton = Button("Import")

    init {
        importButton.addEventHandler(
            MouseEvent.MOUSE_CLICKED
        ) { serviceLocator.importEvents.importPlayers(textField.text.toInt()) }
        HBox.setHgrow(node, Priority.ALWAYS)
        node.styleClass.addAll("gap", "foreground")
        node.children.addAll(
            title,
            description,
            textField,
            importButton
        )
    }
}