package controller

import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent


class ExportEvents {
    val copyToClipboard: (json: String) -> Unit = {
        val content = ClipboardContent()
        content.putString(it)
        val clipboard: Clipboard = Clipboard.getSystemClipboard()
        clipboard.setContent(content)
    }
}