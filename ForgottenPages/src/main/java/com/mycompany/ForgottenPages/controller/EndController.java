package com.mycompany.ForgottenPages.controller;

import com.mycompany.ForgottenPages.dao.playerDAO;
import com.mycompany.ForgottenPages.model.GameState;
import com.mycompany.ForgottenPages.util.Navegar;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Tela final do jogo (game over / resultado da run).
 * Exibe estatisticas, salva o resultado uma unica vez e permite abrir a Arvore de Habilidades.
 */
public class EndController {

    @FXML private Label lblWaveFinal;
    @FXML private Label lblPontos;
    @FXML private Label lblNome;

    @FXML
    public void initialize() {
        GameState gs = GameState.getInstance();
        gs.finalizarRun();

        int wave = gs.getCiclo().getWaveAtual();
        int pontos = gs.getProgressao().getPontos();
        String nome = gs.getNomeJogador();

        atualizarLabels(nome, wave, pontos);
        salvarResultadoUmaVez(gs, nome, wave);
    }

    private void atualizarLabels(String nome, int wave, int pontos) {
        if (lblNome != null) {
            lblNome.setText("Jogador: " + nome);
        }

        if (lblWaveFinal != null) {
            lblWaveFinal.setText("Wave alcançada: " + wave);
        }

        if (lblPontos != null) {
            lblPontos.setText("Pontos para a árvore: " + pontos);
        }
    }

    private void salvarResultadoUmaVez(GameState gs, String nome, int wave) {
        if (gs.isResultadoSalvo()) {
            return;
        }

        playerDAO.salvarResultadoRun(nome, wave);
        gs.marcarResultadoSalvo();
    }

    @FXML
    private void voltarMenu() {
        GameState.reset();
        Navegar.ir("menu");
    }

    @FXML
    private void irArvore() {
        Navegar.ir("fogueira");
    }

    // Mantido para compatibilidade caso algum FXML antigo ainda chame este metodo.
    @FXML
    private void irUpgrades() {
        irArvore();
    }
}
