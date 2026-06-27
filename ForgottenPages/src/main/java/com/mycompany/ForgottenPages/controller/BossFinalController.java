package com.mycompany.ForgottenPages.controller;

import com.mycompany.ForgottenPages.model.BasicAi;
import com.mycompany.ForgottenPages.model.Clash;
import com.mycompany.ForgottenPages.model.Skill;
import com.mycompany.ForgottenPages.model.DamageType;
import com.mycompany.ForgottenPages.model.GameState;
import com.mycompany.ForgottenPages.model.CombatAction;
import com.mycompany.ForgottenPages.model.Boss;
import com.mycompany.ForgottenPages.model.Skilltype;
import com.mycompany.ForgottenPages.model.Personagem;
import com.mycompany.ForgottenPages.model.Player;
import com.mycompany.ForgottenPages.util.Navegar;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Desafio final único: Boss com 3 fases de HP.
 * Cada fase o boss fica mais forte (buff de coinPower).
 */
public class BossFinalController {

    // =========================
    // UI
    // =========================
    @FXML private Label lblFase;
    @FXML private Label lblBossName;
    @FXML private Label lblBossHp;
    @FXML private ProgressBar pbBossHp;
    @FXML private Label lblPlayerHp;
    @FXML private ProgressBar pbPlayerHp;
    @FXML private HBox hboxSkills;
    @FXML private TextArea taLog;
    @FXML private Label lblStatus;

    // =========================
    // GAME STATE
    // =========================
    private final GameState gs = GameState.getInstance();
    private Player player;
    private Boss bossFinal;

    private int fase = 1;
    private boolean aguardando = true;

    // =========================
    // INIT
    // =========================
    @FXML
    public void initialize() {
        player = gs.getPlayer();
        bossFinal = criarBossFinal();

        atualizarUI();
        construirBotoes();

        log("⚔ DESAFIO FINAL ⚔");
        log("O Guardião das Profundezas desperta...");
        setStatus("Enfrente o Guardião! Escolha sua skill.");
    }

    // =========================
    // CREATION
    // =========================
    private Boss criarBossFinal() {
        Boss b = new Boss(new BasicAi(), "Guardião das Profundezas", 500, 500);
        b.getSkills().add(new Skill("Fúria Abissal", 20, 8, 3, Skilltype.ATTACK, DamageType.BLUNT));
        b.getSkills().add(new Skill("Corte Sombrio", 15, 6, 4, Skilltype.ATTACK, DamageType.SLASH));
        b.getSkills().add(new Skill("Escudo Eterno", 0, 7, 2, Skilltype.DEFENSE, DamageType.BLUNT));
        return b;
    }

    // =========================
    // UI - BUTTONS
    // =========================
    private void construirBotoes() {
        hboxSkills.getChildren().clear();

        for (Skill skill : player.getSkills()) {
            Button btn = new Button(
                    skill.getNome()
                            + "\n[" + skill.getBasePower()
                            + "+" + skill.getCoinPower()
                            + "x" + skill.getCoinCount() + "]"
            );

            btn.setPrefWidth(120);
            btn.setPrefHeight(65);
            btn.setWrapText(true);
            btn.setFont(Font.font("System", FontWeight.BOLD, 11));

            String cor = switch (skill.getTipo()) {
                case ATTACK -> "#8B1A1A";
                case DEFENSE -> "#1A3A8B";
                case EVADE -> "#1A6B2A";
            };

            btn.setStyle(
                    "-fx-background-color:" + cor +
                    ";-fx-text-fill:white;" +
                    "-fx-border-color:#FFD700;" +
                    "-fx-border-width:1.5;" +
                    "-fx-border-radius:4;" +
                    "-fx-background-radius:4;"
            );

            btn.setOnAction(e -> {
                if (aguardando) executarTurno(skill);
            });

            hboxSkills.getChildren().add(btn);
        }
    }

    // =========================
    // COMBAT FLOW
    // =========================
    private void executarTurno(Skill skillPlayer) {
        aguardando = false;
        hboxSkills.setDisable(true);

        List<Personagem> pList = new ArrayList<>(List.of(player));
        List<Personagem> bList = new ArrayList<>(List.of(bossFinal));

        CombatAction acao = bossFinal.getAi().chooseAction(bossFinal, bList, pList);

        Clash clash = new Clash(skillPlayer, acao.getSkill());
        log(clash.aplicar(player, bossFinal));

        atualizarUI();
        verificarFase();

        PauseTransition p = new PauseTransition(Duration.millis(700));
        p.setOnFinished(e -> verificarEstado());
        p.play();
    }

    // =========================
    // PHASE SYSTEM
    // =========================
    private void verificarFase() {
        int limiar = bossFinal.getMaxHp() / 3 * (3 - fase);

        if (bossFinal.getHp() <= limiar && fase < 3) {
            fase++;

            bossFinal.getSkills().forEach(s ->
                    s.setCoinPower(s.getCoinPower() + 2)
            );

            log("💥 FASE " + fase + "! O Guardião ficou mais forte!");
            if (lblFase != null) lblFase.setText("Fase " + fase);
        }
    }

    // =========================
    // GAME STATE CHECK
    // =========================
    private void verificarEstado() {
        if (!player.Tavivo()) {
            log("💀 Você foi derrotado pelo Guardião...");
            gs.finalizarRun();
            Navegar.ir("end");
            return;
        }

        if (!bossFinal.Tavivo()) {
            log("🏆 VITÓRIA! O Guardião foi derrotado!");
            gs.getProgressao().ganharPonto(3);
            gs.finalizarRun();

            PauseTransition p = new PauseTransition(Duration.seconds(2));
            p.setOnFinished(e -> Navegar.ir("end"));
            p.play();

            return;
        }

        aguardando = true;
        hboxSkills.setDisable(false);
        setStatus("Fase " + fase + " — Escolha sua skill!");
    }

    // =========================
    // UI UPDATE
    // =========================
    private void atualizarUI() {
        if (lblBossName != null)
            lblBossName.setText(bossFinal.getNome());

        if (lblBossHp != null)
            lblBossHp.setText(bossFinal.getHp() + "/" + bossFinal.getMaxHp());

        if (pbBossHp != null) {
            double r = (double) bossFinal.getHp() / bossFinal.getMaxHp();
            pbBossHp.setProgress(r);
            pbBossHp.setStyle("-fx-accent:" +
                    (r > 0.6 ? "#CC2222" : r > 0.3 ? "#EE9900" : "#660000") + ";");
        }

        if (lblPlayerHp != null)
            lblPlayerHp.setText("HP: " + player.getHp() + "/" + player.getMaxHp());

        if (pbPlayerHp != null) {
            double r = (double) player.getHp() / player.getMaxHp();
            pbPlayerHp.setProgress(r);
            pbPlayerHp.setStyle("-fx-accent:" +
                    (r > 0.6 ? "#44BB44" : r > 0.3 ? "#EE9900" : "#CC2222") + ";");
        }

        if (lblFase != null)
            lblFase.setText("Fase " + fase);
    }

    // =========================
    // HELPERS
    // =========================
    private void log(String msg) {
        taLog.appendText(msg + "\n");
    }

    private void setStatus(String msg) {
        if (lblStatus != null) lblStatus.setText(msg);
    }
}