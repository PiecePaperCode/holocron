package view

import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox

class MainContent: Interface {
    override var node = VBox()

    init {
        HBox.setHgrow(node, Priority.ALWAYS)
        node.styleClass.addAll("gap", "background")
        node.children.add(Label("nothing selected"))
    }

}