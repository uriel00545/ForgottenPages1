/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ForgottenPages.util;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *
 * @author jaeau
 */
public class EfeitoBotao {
    public static void applyLoREffect(Button button) {

        // ✨ glow
        DropShadow glow = new DropShadow();
        glow.setColor(Color.web("#ffd27a"));
        glow.setRadius(20);
        glow.setSpread(0.4);

        // 🔍 zoom
        ScaleTransition zoomIn = new ScaleTransition(Duration.millis(120), button);
        zoomIn.setToX(1.05);
        zoomIn.setToY(1.05);

        ScaleTransition zoomOut = new ScaleTransition(Duration.millis(120), button);
        zoomOut.setToX(1.0);
        zoomOut.setToY(1.0);

        button.setOnMouseEntered(e -> {
            button.setEffect(glow);
            zoomIn.playFromStart();
        });

        button.setOnMouseExited(e -> {
            button.setEffect(null);
            zoomOut.playFromStart();
        });
    }
}
