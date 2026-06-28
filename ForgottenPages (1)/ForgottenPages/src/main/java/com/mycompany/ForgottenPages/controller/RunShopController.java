package com.mycompany.ForgottenPages.controller;

import com.mycompany.ForgottenPages.model.Deck;
import com.mycompany.ForgottenPages.model.GameState;
import com.mycompany.ForgottenPages.model.Player;
import com.mycompany.ForgottenPages.model.Progressao;
import com.mycompany.ForgottenPages.model.Skilltype;
import com.mycompany.ForgottenPages.util.Navegar;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * Loja normal da run.
 * Usa MOEDAS e tudo comprado aqui e temporario.
 * Os itens/buffs ficam no Deck e somem quando GameState.reset() iniciar outra run.
 */
public class RunShopController {

    @FXML private Label lblMoedas;
    @FXML private Label lblItens;
    @FXML private Label lblFeedback;
    @FXML private Label lblVidaRunNivel;
    @FXML private Label lblDanoRunNivel;
    @FXML private Label lblClashRunNivel;
    @FXML private AnchorPane rootShop;
    @FXML private ImageView imgBackground;

    private static final int MAX_NIVEL_RUN = 100;

    private static final int CUSTO_POCAO = 50;
    private static final int CUSTO_VIDA = 20;
    private static final int CUSTO_DANO = 25;
    private static final int CUSTO_CLASH = 30;

    private static final int BONUS_VIDA_RUN = 2;
    private static final int BONUS_DANO_RUN = 1;
    private static final int INTERVALO_BONUS_CLASH_RUN = 10;

    @FXML
    public void initialize() {
        configurarBackground();
        atualizarUI();
    }

    private void configurarBackground() {
        if (rootShop == null || imgBackground == null) {
            return;
        }

        var url = getClass().getResource("/com/mycompany/ForgottenPages/loja.png");
        if (url != null) {
            imgBackground.setImage(new Image(url.toExternalForm()));
        }

        imgBackground.fitWidthProperty().bind(rootShop.widthProperty());
        imgBackground.fitHeightProperty().bind(rootShop.heightProperty());
        imgBackground.setPreserveRatio(false);
        imgBackground.setSmooth(true);
        imgBackground.setCache(true);
    }

    private void atualizarUI() {
        GameState gs = GameState.getInstance();
        Progressao p = gs.getProgressao();
        Deck deck = gs.getDeck();

        if (lblMoedas != null) {
            lblMoedas.setText("Moedas disponíveis: " + p.getMoedas());
        }
        if (lblItens != null) {
            lblItens.setText("Poções no deck: " + deck.getPocoesVida());
        }
        if (lblVidaRunNivel != null) {
            lblVidaRunNivel.setText("Nível " + deck.getVidaRunNivel() + "/" + MAX_NIVEL_RUN);
        }
        if (lblDanoRunNivel != null) {
            lblDanoRunNivel.setText("Nível " + deck.getDanoRunNivel() + "/" + MAX_NIVEL_RUN);
        }
        if (lblClashRunNivel != null) {
            lblClashRunNivel.setText("Nível " + deck.getClashRunNivel() + "/" + MAX_NIVEL_RUN);
        }
    }

    private void feedback(String msg) {
        if (lblFeedback != null) {
            lblFeedback.setText(msg);
        }
    }

    private boolean gastarMoedas(int custo) {
        Progressao p = GameState.getInstance().getProgressao();
        if (!p.gastarMoedas(custo)) {
            feedback("Moedas insuficientes!");
            return false;
        }
        return true;
    }

    @FXML
    private void comprarPocaoVida() {
        Deck deck = GameState.getInstance().getDeck();

        if (!gastarMoedas(CUSTO_POCAO)) return;

        deck.adicionarPocaoVida(1);
        feedback("Poção adicionada ao Deck da run. Ela será perdida ao morrer.");
        atualizarUI();
    }

    @FXML
    private void usarPocaoVida() {
        GameState gs = GameState.getInstance();
        Deck deck = gs.getDeck();
        Player player = gs.getPlayer();

        if (player == null) {
            feedback("Jogador não encontrado.");
            return;
        }

        if (!deck.gastarPocaoVida()) {
            feedback("Você não tem poções no Deck.");
            atualizarUI();
            return;
        }

        int hpAntes = player.getHp();
        player.setHp(Math.min(player.getMaxHp(), player.getHp() + 30));

        feedback("Poção usada! HP: " + hpAntes + " -> " + player.getHp());
        atualizarUI();
    }

    @FXML
    private void upgradeVidaRun() {
        GameState gs = GameState.getInstance();
        Deck deck = gs.getDeck();
        Player player = gs.getPlayer();

        if (deck.getVidaRunNivel() >= MAX_NIVEL_RUN) {
            feedback("Buff de vida já está no máximo nesta run.");
            return;
        }
        if (!gastarMoedas(CUSTO_VIDA)) return;

        if (player != null) {
            player.setMaxHp(player.getMaxHp() + BONUS_VIDA_RUN);
            player.setHp(Math.min(player.getMaxHp(), player.getHp() + BONUS_VIDA_RUN));
        }
        deck.setVidaRunNivel(deck.getVidaRunNivel() + 1);

        feedback("Vida da run aumentada. Esse buff some ao morrer.");
        atualizarUI();
    }

    @FXML
    private void upgradeDanoRun() {
        GameState gs = GameState.getInstance();
        Deck deck = gs.getDeck();
        Player player = gs.getPlayer();

        if (deck.getDanoRunNivel() >= MAX_NIVEL_RUN) {
            feedback("Buff de dano já está no máximo nesta run.");
            return;
        }
        if (!gastarMoedas(CUSTO_DANO)) return;

        if (player != null) {
            player.getDeck().getSkills().stream()
                    .filter(s -> s.getTipo() == Skilltype.ATTACK)
                    .forEach(s -> s.setBasePower(s.getBasePower() + BONUS_DANO_RUN));
        }
        deck.setDanoRunNivel(deck.getDanoRunNivel() + 1);

        feedback("Dano das skills de ataque aumentado nesta run.");
        atualizarUI();
    }

    @FXML
    private void upgradeClashRun() {
        GameState gs = GameState.getInstance();
        Deck deck = gs.getDeck();
        Player player = gs.getPlayer();

        if (deck.getClashRunNivel() >= MAX_NIVEL_RUN) {
            feedback("Buff de Clash já está no máximo nesta run.");
            return;
        }
        if (!gastarMoedas(CUSTO_CLASH)) return;

        int proximoNivel = deck.getClashRunNivel() + 1;

        if (player != null && proximoNivel % INTERVALO_BONUS_CLASH_RUN == 0) {
            player.getDeck().getSkills().forEach(s -> s.setCoinPower(s.getCoinPower() + 1));
        }
        deck.setClashRunNivel(proximoNivel);

        if (proximoNivel % INTERVALO_BONUS_CLASH_RUN == 0) {
            feedback("Dados do Clash melhorados nesta run.");
        } else {
            feedback("Nível de Clash comprado. O poder aumenta a cada " + INTERVALO_BONUS_CLASH_RUN + " níveis.");
        }
        atualizarUI();
    }

    @FXML
    private void irParaBatalha() {
        Navegar.ir("battle");
    }

    @FXML
    private void voltar() {
        if (GameState.getInstance().isGameOver()) {
            Navegar.ir("end");
        } else {
            Navegar.ir("area");
        }
    }
}
