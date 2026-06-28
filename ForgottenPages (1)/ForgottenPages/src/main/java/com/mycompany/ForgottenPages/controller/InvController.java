package com.mycompany.ForgottenPages.controller;

import com.mycompany.ForgottenPages.model.Deck;
import com.mycompany.ForgottenPages.model.GameState;
import com.mycompany.ForgottenPages.model.Progressao;
import com.mycompany.ForgottenPages.model.Skill;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * Inventario do jogador.
 *
 * A tela agora tem duas abas:
 * - Inventario: recursos, itens e buffs temporarios da run.
 * - Decks: skills/cartas do player.
 */
public class InvController {

    @FXML private ListView<String> listItens;
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
        carregarInventario(gs);
        carregarDecks(gs);
    }

    private void atualizarInfoJogador(GameState gs) {
        Deck deck = gs.getDeck();
        Progressao progressao = gs.getProgressao();

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
                    "Pontos: " + progressao.getPontos()
                    + " | Moedas: " + progressao.getMoedas()
            );
        }

        if (lblDeck != null) {
            lblDeck.setText(
                    "Skills equipadas: " + deck.getSkills().size()
                    + " | Vida +" + deck.getBonusVidaTotal()
                    + " | Dano +" + deck.getBonusDanoTotal()
                    + " | Clash +" + deck.getBonusClashTotal()
            );
        }
    }

    private void carregarInventario(GameState gs) {
        if (listItens == null) return;

        Deck deck = gs.getDeck();
        Progressao progressao = gs.getProgressao();

        listItens.getItems().clear();

        listItens.getItems().add("Poções de Vida: " + deck.getPocoesVida());
        listItens.getItems().add("Moedas da run: " + progressao.getMoedas());
        listItens.getItems().add("Pontos da Árvore: " + progressao.getPontos());
        listItens.getItems().add("Buff temporário de Vida: nível " + deck.getVidaRunNivel() + " (Vida +" + deck.getBonusVidaTotal() + ")");
        listItens.getItems().add("Buff temporário de Dano: nível " + deck.getDanoRunNivel() + " (Dano +" + deck.getBonusDanoTotal() + ")");
        listItens.getItems().add("Buff temporário de Clash: nível " + deck.getClashRunNivel() + " (Coin Power +" + deck.getBonusClashTotal() + ")");

        if (deck.getPocoesVida() <= 0
                && progressao.getMoedas() <= 0
                && deck.getVidaRunNivel() <= 0
                && deck.getDanoRunNivel() <= 0
                && deck.getClashRunNivel() <= 0) {
            listItens.getItems().add("Nenhum item temporário comprado nesta run.");
        }
    }

    private void carregarDecks(GameState gs) {
        if (listSkills == null) return;

        listSkills.getItems().clear();

        if (gs.getDeck().getSkills().isEmpty()) {
            listSkills.getItems().add("Nenhuma skill equipada no deck.");
            return;
        }

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