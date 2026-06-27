package com.mycompany.ForgottenPages.controller;

import com.mycompany.ForgottenPages.model.Deck;
import com.mycompany.ForgottenPages.model.GameState;
import com.mycompany.ForgottenPages.model.Skill;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * Inventário do jogador.
 * Agora exibe o Deck: skills, itens e buffs temporarios da run.
 */
public class InvController {

    @FXML private ListView<String> listSkills;
    @FXML private Label lblNome;
    @FXML private Label lblHp;
    @FXML private Label lblRecursos;
    @FXML private Label lblDeck;

    @FXML
    public void initialize() {
        GameState gs = GameState.getInstance();

        if (gs.getPlayer() == null) return;

        atualizarInfoJogador(gs);
        carregarSkills(gs);
    }

    private void atualizarInfoJogador(GameState gs) {
        Deck deck = gs.getDeck();

        if (lblNome != null) {
            lblNome.setText(gs.getPlayer().getNome());
        }

        if (lblHp != null) {
            lblHp.setText(
                    "HP: " + gs.getPlayer().getHp()
                    + "/" + gs.getPlayer().getMaxHp()
            );
        }

        if (lblRecursos != null) {
            lblRecursos.setText(
                    "Pontos: " + gs.getProgressao().getPontos()
                    + " | Moedas: " + gs.getProgressao().getMoedas()
                    + " | Poções: " + deck.getPocoesVida()
            );
        }

        if (lblDeck != null) {
            lblDeck.setText(
                    "Deck da run → Vida +" + deck.getBonusVidaTotal()
                    + " | Dano +" + deck.getBonusDanoTotal()
                    + " | Clash nível " + deck.getClashRunNivel()
            );
        }
    }

    private void carregarSkills(GameState gs) {
        if (listSkills == null) return;

        listSkills.getItems().clear();

        for (Skill s : gs.getDeck().getSkills()) {
            listSkills.getItems().add(formatSkill(s));
        }
    }

    private String formatSkill(Skill s) {
        return String.format(
                "%s  [base:%d  dado:%dx%d]  %s | %s",
                s.getNome(),
                s.getBasePower(),
                s.getCoinPower(),
                s.getCoinCount(),
                s.getTipo(),
                s.getDamageType()
        );
    }

    @FXML
    private void fechar() {
        Stage stage = (Stage) listSkills.getScene().getWindow();
        stage.close();
    }
}
