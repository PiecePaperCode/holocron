package view

import javafx.scene.control.Label

class Title(text: String, size: Size = Size.BIG): ViewInterface {
    override val node = Label(text)

    init {
        when (size) {
            Size.BIG -> node.styleClass.add("title")
            Size.SMALL -> node.styleClass.add("H1")
        }

    }

    enum class Size {
        BIG, SMALL
    }
}