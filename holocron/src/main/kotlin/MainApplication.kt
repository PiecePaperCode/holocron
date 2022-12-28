import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.BorderPane
import javafx.stage.Stage

class MainApplication : Application() {
    val scene = Scene(BorderPane(), 320.0, 240.0)

    override fun start(stage: Stage) {
        stage.title = "Hello!"
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Application.launch(MainApplication::class.java)
}