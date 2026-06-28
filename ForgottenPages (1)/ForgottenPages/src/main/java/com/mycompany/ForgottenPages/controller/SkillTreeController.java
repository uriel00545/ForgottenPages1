package com.mycompany.ForgottenPages.controller;

import com.mycompany.ForgottenPages.dao.playerDAO;
import com.mycompany.ForgottenPages.model.GameState;
import com.mycompany.ForgottenPages.model.Player;
import com.mycompany.ForgottenPages.model.Progressao;
import com.mycompany.ForgottenPages.util.Navegar;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Arvore de Habilidades.
 * Usa PONTOS e representa os upgrades permanentes do jogador.
 * Esta tela fica disponível apenas no fim da run.
 */
public class SkillTreeController {

    @FXML private Label lblPontos;
    @FXML private Label lblFeedback;
    @FXML private Label lblVidaNivel;
    @FXML private Label lblDanoNivel;
    @FXML private Label lblClashNivel;

    private static final int MAX_NIVEL = 100;
    private static final int CUSTO = 1;

    private static final int BONUS_VIDA = 2;
    private static final int BONUS_DANO = 1;
    private static final int INTERVALO_BONUS_CLASH = 10;

    @FXML
    public void initialize() {
        atualizarUI();
    }

    private void atualizarUI() {
        GameState gs = GameState.getInstance();
        Progressao p = gs.getProgressao();

        if (lblPontos != null) {
            lblPontos.setText("Pontos disponíveis: " + p.getPontos());
        }
        if (lblVidaNivel != null) {
            lblVidaNivel.setText("Nível " + p.getVidaNivel() + "/" + MAX_NIVEL);
        }
        if (lblDanoNivel != null) {
            lblDanoNivel.setText("Nível " + p.getDanoNivel() + "/" + MAX_NIVEL);
        }
        if (lblClashNivel != null) {
            lblClashNivel.setText("Nível " + p.getClashNivel() + "/" + MAX_NIVEL);
        }
    }

    private void feedback(String msg) {
        if (lblFeedback != null) {
            lblFeedback.setText(msg);
        }
    }

    private boolean podeComprar(int nivelAtual, String nomeUpgrade) {
        if (nivelAtual >= MAX_NIVEL) {
            feedback(nomeUpgrade + " já está no nível máximo.");
            return false;
        }

        if (!GameState.getInstance().getProgressao().gastarPonto(CUSTO)) {
            feedback("Pontos insuficientes!");
            return false;
        }

        return true;
    }

    @FXML
    private void upgradeVida() {
        GameState gs = GameState.getInstance();
        Progressao progressao = gs.getProgressao();

        if (!podeComprar(progressao.getVidaNivel(), "Vida")) return;

        Player player = gs.getPlayer();
        if (player != null) {
            player.setMaxHp(player.getMaxHp() + BONUS_VIDA);
            player.setHp(Math.min(player.getMaxHp(), player.getHp() + BONUS_VIDA));
        }

        progressao.setVidaNivel(progressao.getVidaNivel() + 1);
        playerDAO.salvarUpgrade(gs.getNomeJogador(), "vida", 1);

        feedback("Vida melhorada na Árvore de Habilidades!");
        atualizarUI();
    }

    @FXML
    private void upgradeDano() {
        GameState gs = GameState.getInstance();
        Progressao progressao = gs.getProgressao();

        if (!podeComprar(progressao.getDanoNivel(), "Dano")) return;

        Player player = gs.getPlayer();
        if (player != null) {
            player.getSkills().stream()
                    .filter(s -> s.getTipo().name().equals("ATTACK"))
                    .forEach(s -> s.setBasePower(s.getBasePower() + BONUS_DANO));
        }

        progressao.setDanoNivel(progressao.getDanoNivel() + 1);
        playerDAO.salvarUpgrade(gs.getNomeJogador(), "dano", 1);

        feedback("Dano melhorado na Árvore de Habilidades!");
        atualizarUI();
    }

    @FXML
    private void upgradeClash() {
        GameState gs = GameState.getInstance();
        Progressao progressao = gs.getProgressao();

        if (!podeComprar(progressao.getClashNivel(), "Clash")) return;

        int proximoNivel = progressao.getClashNivel() + 1;

        Player player = gs.getPlayer();
        if (player != null && proximoNivel % INTERVALO_BONUS_CLASH == 0) {
            player.getSkills().forEach(s -> s.setCoinPower(s.getCoinPower() + 1));
        }

        progressao.setClashNivel(proximoNivel);
        playerDAO.salvarUpgrade(gs.getNomeJogador(), "clash", 1);

        if (progressao.getClashNivel() % INTERVALO_BONUS_CLASH == 0) {
            feedback("Clash melhorado na Árvore de Habilidades!");
        } else {
            feedback("Nível de Clash comprado. O poder aumenta a cada " + INTERVALO_BONUS_CLASH + " níveis.");
        }
        atualizarUI();
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
