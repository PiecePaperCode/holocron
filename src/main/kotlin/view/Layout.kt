package view

import controller.ServiceLocator
import javafx.scene.Scene
import javafx.scene.layout.HBox

class Layout(private var serviceLocator: ServiceLocator) {
    private var layout = HBox()
    var mainView = MainView().node
    var scene: Scene

    init {
        layout.children.addAll(
            serviceLocator.sideMenu.node,
            mainView
        )
        scene = Scene(layout, 1280.0, 720.0)
    }

    fun update() {
        layout.children.clear()
        layout.children.addAll(
            serviceLocator.sideMenu.node,
            mainView
        )
    }
}