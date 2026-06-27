package com.mycompany.ForgottenPages.controller;

import com.mycompany.ForgottenPages.dao.playerDAO;
import com.mycompany.ForgottenPages.model.GameState;
import com.mycompany.ForgottenPages.model.Progressao;
import com.mycompany.ForgottenPages.util.Navegar;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Loja de upgrades do jogador.
 * Permite melhorar vida, dano e sistema de clash.
 */
public class ShopController {

    // =========================
    // UI
    // =========================
    @FXML private Label lblPontos;
    @FXML private Label lblFeedback;

    // =========================
    // CONFIG
    // =========================
    private static final int MAX_NIVEL = 3;

    // =========================
    // INIT
    // =========================
    @FXML
    public void initialize() {
        atualizarUI();
    }

    // =========================
    // UI UPDATE
    // =========================
    private void atualizarUI() {
        GameState gs = GameState.getInstance();

        if (lblPontos != null) {
            lblPontos.setText(
                    "Pontos disponíveis: " + gs.getProgressao().getPontos()
            );
        }
    }

    private void atualizarFeedback(String msg) {
        if (lblFeedback != null) {
            lblFeedback.setText(msg);
        }
    }

    // =========================
    // PURCHASE SYSTEM
    // =========================
    private void comprar(String tipo, Runnable aplicar) {
        GameState gs = GameState.getInstance();
        Progressao progressao = gs.getProgressao();

        if (progressao.gastarPonto(1)) {
            aplicar.run();

            playerDAO.salvarUpgrade(gs.getNomeJogador(), tipo, 1);

            atualizarFeedback("✅ " + tipo + " melhorado!");
            atualizarUI();
        } else {
            atualizarFeedback("❌ Pontos insuficientes!");
        }
    }

    // =========================
    // UPGRADES
    // =========================
    @FXML
    private void upgradeVida() {
        comprar("vida", () -> {
            GameState gs = GameState.getInstance();

            if (gs.getProgressao().getVidaNivel() < MAX_NIVEL) {
                gs.getPlayer().setMaxHp(gs.getPlayer().getMaxHp() + 20);
                gs.getPlayer().setHp(gs.getPlayer().getHp() + 20);

                gs.getProgressao().setVidaNivel(
                        gs.getProgressao().getVidaNivel() + 1
                );
            }
        });
    }

    @FXML
    private void upgradeDano() {
        comprar("dano", () -> {
            GameState gs = GameState.getInstance();

            if (gs.getProgressao().getDanoNivel() < MAX_NIVEL) {
                gs.getPlayer().getSkills().stream()
                        .filter(s -> s.getTipo().name().equals("ATTACK"))
                        .forEach(s -> s.setBasePower(s.getBasePower() + 3));

                gs.getProgressao().setDanoNivel(
                        gs.getProgressao().getDanoNivel() + 1
                );
            }
        });
    }

    @FXML
    private void upgradeClash() {
        comprar("clash", () -> {
            GameState gs = GameState.getInstance();

            if (gs.getProgressao().getClashNivel() < MAX_NIVEL) {
                gs.getPlayer().getSkills()
                        .forEach(s -> s.setCoinPower(s.getCoinPower() + 1));

                gs.getProgressao().setClashNivel(
                        gs.getProgressao().getClashNivel() + 1
                );
            }
        });
    }

    // =========================
    // NAVIGATION
    // =========================
    @FXML
    private void voltar() {
        Navegar.ir("area");
    }
}