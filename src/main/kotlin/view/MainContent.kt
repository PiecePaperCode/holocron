package view

import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox

class MainContent: Interface {
    override var node = VBox()

    init {
        HBox.setHgrow(node, Priority.ALWAYS)
        node.styleClass.addAll("gap", "background")
        node.children.addAll(
            Title("Holocron").node,
            Label("Thank you for choosing Holocron to help manage your tournament."),
            Label("I hope that the program helps make your event run smoothly."),
            Label("I hope you have a great tournament experience with Holocron."),
            Label("If you have any feedback or suggestions for future updates, please don't hesitate to let me know."),
            Label("PiecePaperCode@https://github.com/PiecePaperCode/holocron")
        )
    }

}