package com.mycompany.ForgottenPages.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import java.io.IOException;

public class Cena {

    private static AnchorPane rootContainer;

    public static void setRootContainer(AnchorPane container) {
        rootContainer = container;
    }

    public static void mostrar(String fxmlPath) {
        if (rootContainer == null) {
            return;
        }

        try {
            Parent view = FXMLLoader.load(Cena.class.getResource(fxmlPath));

            AnchorPane.setTopAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);

            if (view instanceof Region region) {
                region.setMaxWidth(Double.MAX_VALUE);
                region.setMaxHeight(Double.MAX_VALUE);
            }

            rootContainer.getChildren().setAll(view);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
