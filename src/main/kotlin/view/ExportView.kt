package view

import controller.ServiceLocator
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import controller.Export

class ExportView(serviceLocator: ServiceLocator) : ViewInterface {
    override var node = VBox()
    private val title = Title("Export").node
    private val description = Label("""
        Cryodex Json string export of the current tournament.
        Copy this string to ListJuggler.
    """.trimIndent())
    private val textField = TextArea(Export().toJson(serviceLocator.tournament))
    private val clipboardButton = Button("Copy to Clipboard")

    init {
        textField.prefRowCount = 100
        textField.isWrapText = true
        clipboardButton.addEventHandler(
            MouseEvent.MOUSE_CLICKED
        ) { serviceLocator.exportEvents.copyToClipboard(textField.text) }
        HBox.setHgrow(node, Priority.ALWAYS)
        node.styleClass.addAll("gap", "foreground")
        node.children.addAll(
            title,
            description,
            textField,
            clipboardButton
        )
    }
}