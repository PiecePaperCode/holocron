package view

import controller.ServiceLocator
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox

class CreatePlayer(serviceLocator: ServiceLocator): ViewInterface {
    override val node = VBox()
    private val title = Title("Create Player").node
    private val nameLabel = Label("Name")
    private val nameInput = TextField()
    private val listLabel = Label("XWT List")
    private val xwtInput = TextArea()
    private val formNode = VBox(nameLabel, nameInput, listLabel, xwtInput)
    private val submitButton = Button("Create")
    private val cancelButton = Button("Cancel")
    private val buttonNode = HBox(submitButton, cancelButton)

    init {
        submitButton.addEventHandler(
            MouseEvent.MOUSE_CLICKED
        ) { serviceLocator.playerEvents.createNewPlayer(nameInput.text, xwtInput.text) }
        cancelButton.addEventHandler(
            MouseEvent.MOUSE_CLICKED,
            serviceLocator.sideMenuEvents.selectPlayers
        )
    }

    init {
        HBox.setHgrow(node, Priority.ALWAYS)
        buttonNode.styleClass.add("gap")
        formNode.styleClass.addAll("gap", "foreground")
        node.styleClass.addAll("gap", "background")
        node.children.addAll(
            title,
            formNode,
            buttonNode
        )
    }
}