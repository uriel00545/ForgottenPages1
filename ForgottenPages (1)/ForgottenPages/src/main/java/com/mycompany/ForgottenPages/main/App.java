package com.mycompany.ForgottenPages.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {

    private static Scene scene;
    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;

        scene = new Scene(loadFXML("menu"), 800, 600);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.F11) {
                mainStage.setFullScreen(!mainStage.isFullScreen());
            }
        });

        stage.setTitle("Forgotten Pages");
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.setFullScreenExitHint("Pressione F11 para sair da tela cheia");
        stage.setScene(scene);
        stage.show();
        stage.setFullScreen(true);
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static void alternarTelaCheia() {
        if (mainStage != null) {
            mainStage.setFullScreen(!mainStage.isFullScreen());
        }
    }

    public static boolean isTelaCheia() {
        return mainStage != null && mainStage.isFullScreen();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(
            App.class.getResource("/com/mycompany/ForgottenPages/" + fxml + ".fxml")
        );
        return loader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
