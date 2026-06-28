package com.mycompany.ForgottenPages.controller;

import com.mycompany.ForgottenPages.model.GameState;
import com.mycompany.ForgottenPages.util.Cena;
import com.mycompany.ForgottenPages.util.Navegar;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Controller da área principal do jogo (hub).
 * Responsável por navegação entre cenas e exibição de informações do ciclo.
 */
public class AreaController {

    @FXML private AnchorPane AreaCena;
    @FXML private Label lblWaveInfo;
    @FXML private Label lblPontos;
    @FXML private Label lblMoedas;

    @FXML
    public void initialize() {
        Cena.setRootContainer(AreaCena);
        Cena.mostrar("/com/mycompany/ForgottenPages/fogueira.fxml");

        atualizarInfo();
    }

    private void atualizarInfo() {
        GameState gs = GameState.getInstance();

        int wave = gs.getCiclo().getWaveAtual();
        boolean boss = gs.getCiclo().isBossWave();

        if (lblWaveInfo != null) {
            lblWaveInfo.setText(
                    "Próxima: Wave " + wave + (boss ? " ⚠ BOSS!" : "")
            );
        }

        if (lblPontos != null) {
            lblPontos.setText(
                    "Pontos: " + gs.getProgressao().getPontos()
            );
        }

        if (lblMoedas != null) {
            lblMoedas.setText(
                    "Moedas: " + gs.getProgressao().getMoedas()
            );
        }
    }

    @FXML
    private void tfogueira() {
        Cena.mostrar("/com/mycompany/ForgottenPages/fogueira.fxml");
    }

    @FXML
    private void tLoja() {
        Cena.mostrar("/com/mycompany/ForgottenPages/shop.fxml");
    }

    @FXML
    private void irParaBatalha() {
        Navegar.ir("battle");
    }

    @FXML
    private void tinv() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/mycompany/ForgottenPages/inv.fxml")
            );

            Parent root = loader.load();

            Stage invStage = new Stage();
            invStage.setTitle("Inventário / Decks");
            invStage.setScene(new Scene(root, 560, 500));
            invStage.initOwner(AreaCena.getScene().getWindow());
            invStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}