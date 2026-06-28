package com.mycompany.ForgottenPages.controller;

import com.mycompany.ForgottenPages.dao.playerDAO;
import com.mycompany.ForgottenPages.model.GameState;
import com.mycompany.ForgottenPages.model.Player;
import com.mycompany.ForgottenPages.model.Progressao;
import com.mycompany.ForgottenPages.util.EfeitoBotao;
import com.mycompany.ForgottenPages.util.Navegar;

import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class MenuController {

    @FXML private StackPane rootMenu;
    @FXML private ImageView imgFundoMenu;

    @FXML private TextField tfNome;

    @FXML private Button jogar;
    @FXML private Button encerrar;
    @FXML private Button ranking;
    @FXML private Button creditos;

    @FXML
    public void initialize() {
        configurarFundoResponsivo();

        EfeitoBotao.applyLoREffect(jogar);
        EfeitoBotao.applyLoREffect(encerrar);
        EfeitoBotao.applyLoREffect(ranking);
        EfeitoBotao.applyLoREffect(creditos);
    }

    private void configurarFundoResponsivo() {
        if (rootMenu == null || imgFundoMenu == null) {
            return;
        }

        imgFundoMenu.fitWidthProperty().bind(rootMenu.widthProperty());
        imgFundoMenu.fitHeightProperty().bind(rootMenu.heightProperty());
        imgFundoMenu.setMouseTransparent(true);
    }

    @FXML
    private void jogar() {
        String nome = obterNomeJogador();

        if (!nomeValido(nome)) {
            mostrarErroNome();
            return;
        }

        iniciarNovaRun(nome);
        Navegar.ir("area");
    }

    @FXML
    private void abrirRanking() {
        Navegar.ir("ranking");
    }

    @FXML
    private void abrirCreditos() {
        Navegar.ir("creditos");
    }

    @FXML
    private void fecharJogo() {
        System.exit(0);
    }

    private boolean nomeValido(String nome) {
        return nome != null && !nome.trim().isEmpty();
    }

    private void mostrarErroNome() {
        if (tfNome != null) {
            tfNome.setStyle(
                    "-fx-background-color: rgba(20, 20, 45, 0.95);" +
                    "-fx-text-fill: white;" +
                    "-fx-prompt-text-fill: #AAAAAA;" +
                    "-fx-border-color: #FF3333;" +
                    "-fx-border-width: 2;" +
                    "-fx-border-radius: 4;" +
                    "-fx-background-radius: 4;" +
                    "-fx-font-size: 14;"
            );

            tfNome.requestFocus();
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Nome obrigatório");
        alert.setHeaderText("Digite um nome para começar");
        alert.setContentText("Você precisa colocar o nome do jogador antes de iniciar a run.");
        alert.showAndWait();
    }

    private void iniciarNovaRun(String nome) {
        GameState.reset();

        GameState gs = GameState.getInstance();
        Player player = playerDAO.buscarOuCriar(nome);

        gs.setNomeJogador(nome);
        gs.setPlayer(player);

        aplicarUpgradesPermanentes(gs, playerDAO.carregarUpgrades(nome));
    }

    private void aplicarUpgradesPermanentes(GameState gs, Map<String, Integer> upgrades) {
        if (upgrades == null || upgrades.isEmpty() || gs.getPlayer() == null) {
            return;
        }

        Player player = gs.getPlayer();
        Progressao progressao = gs.getProgressao();

        int vida = Math.min(3, upgrades.getOrDefault("vida", 0));
        int dano = Math.min(3, upgrades.getOrDefault("dano", 0));
        int clash = Math.min(3, upgrades.getOrDefault("clash", 0));

        if (vida > 0) {
            player.setMaxHp(player.getMaxHp() + (20 * vida));
            player.setHp(player.getMaxHp());
            progressao.setVidaNivel(vida);
        }

        if (dano > 0) {
            player.getSkills().stream()
                    .filter(s -> s.getTipo().name().equals("ATTACK"))
                    .forEach(s -> s.setBasePower(s.getBasePower() + (3 * dano)));

            progressao.setDanoNivel(dano);
        }

        if (clash > 0) {
            player.getSkills().forEach(s -> s.setCoinPower(s.getCoinPower() + clash));

            progressao.setClashNivel(clash);
        }
    }

    private String obterNomeJogador() {
        return (tfNome != null) ? tfNome.getText().trim() : "";
    }
}