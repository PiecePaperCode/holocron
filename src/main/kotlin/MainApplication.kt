import controller.ServiceLocator
import javafx.application.Application
import javafx.stage.Stage


class MainApplication : Application() {
    private val serviceLocator = ServiceLocator()
    private val layout = serviceLocator.layout

    init {
        layout.scene.stylesheets.add(
            MainApplication::class
                .java
                .getResource("style.css")
                ?.toString() ?: ""
        )
    }

    override fun start(stage: Stage) {
        stage.title = "Holocron"
        stage.scene = layout.scene
        stage.show()
    }
}

fun main() {
    Application.launch(MainApplication::class.java)
}