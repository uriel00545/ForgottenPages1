package com.mycompany.ForgottenPages.controller;

import com.mycompany.ForgottenPages.model.Clash;
import com.mycompany.ForgottenPages.model.Skill;
import com.mycompany.ForgottenPages.model.GameState;
import com.mycompany.ForgottenPages.model.CombatAction;
import com.mycompany.ForgottenPages.model.PlayerFactory;
import com.mycompany.ForgottenPages.model.Inimigo;
import com.mycompany.ForgottenPages.model.Personagem;
import com.mycompany.ForgottenPages.model.Player;
import com.mycompany.ForgottenPages.util.Navegar;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class BattleController {

    // =========================
    // FXML COMPONENTS
    // =========================
    @FXML private Label lblWave;
    @FXML private Label lblPlayerName;
    @FXML private Label lblPlayerHp;
    @FXML private ProgressBar pbPlayerHp;

    @FXML private Label lblEnemyName;
    @FXML private Label lblEnemyHp;
    @FXML private ProgressBar pbEnemyHp;

    @FXML private HBox hboxSkills;

    @FXML private TextArea taLog;

    @FXML private Label lblTurno;
    @FXML private Label lblStatus;
    @FXML private Label lblPontos;

    // =========================
    // GAME STATE
    // =========================
    private final GameState gs = GameState.getInstance();
    private Player player;
    private Inimigo inimigoAtual;

    private int turno = 1;
    private boolean aguardandoInput = true;

    // =========================
    // INITIALIZE
    // =========================
    @FXML
    public void initialize() {
        if (gs.getPlayer() == null) {
            gs.setPlayer(PlayerFactory.criarPadrao());
        }

        player = gs.getPlayer();

        gs.iniciarWave();
        inimigoAtual = gs.getInimigosAtuais().get(0);

        atualizarUI();
        construirBotoesSkills();

        log("=== Wave " + gs.getCiclo().getWaveAtual() + " iniciada! ===");

        if (gs.getCiclo().isBossWave()) {
            log("⚠ BOSS WAVE! Prepare-se!");
        }

        setStatus("Escolha sua skill para atacar.");
    }

    // =========================
    // SKILL UI
    // =========================
    private void construirBotoesSkills() {
        hboxSkills.getChildren().clear();

        for (Skill skill : player.getSkills()) {
            Button btn = new Button(formatarSkill(skill));

            btn.setPrefWidth(130);
            btn.setPrefHeight(70);
            btn.setWrapText(true);
            btn.setFont(Font.font("System", FontWeight.BOLD, 11));

            estilizarBotaoSkill(btn, skill);

            btn.setOnAction(e -> {
                if (aguardandoInput) {
                    executarTurno(skill);
                }
            });

            hboxSkills.getChildren().add(btn);
        }
    }

    private String formatarSkill(Skill s) {
        return String.format(
            "%s\n[%d + %dx%d]\n%s | %s",
            s.getNome(),
            s.getBasePower(),
            s.getCoinPower(),
            s.getCoinCount(),
            s.getTipo(),
            s.getDamageType()
        );
    }

    private void estilizarBotaoSkill(Button btn, Skill skill) {
        String cor = switch (skill.getTipo()) {
            case ATTACK  -> "#8B1A1A";
            case DEFENSE -> "#1A3A8B";
            case EVADE   -> "#1A6B2A";
        };

        btn.setStyle(String.format(
            "-fx-background-color: %s;" +
            " -fx-text-fill: white;" +
            " -fx-border-color: #FFD700;" +
            " -fx-border-width: 1.5;" +
            " -fx-border-radius: 4;" +
            " -fx-background-radius: 4;",
            cor
        ));
    }

    // =========================
    // TURN SYSTEM
    // =========================
    private void executarTurno(Skill skillPlayer) {
        aguardandoInput = false;

        setStatus("Processando turno " + turno + "...");
        hboxSkills.setDisable(true);

        log("\n─── Turno " + turno + " ───");

        List<Personagem> playerList  = new ArrayList<>(List.of(player));
        List<Personagem> inimigoList = new ArrayList<>(List.of(inimigoAtual));

        CombatAction acaoInimigo = inimigoAtual.getAi()
            .chooseAction(inimigoAtual, inimigoList, playerList);

        Skill skillInimigo = acaoInimigo.getSkill();

        log(player.getNome() + " usa: " + skillPlayer.getNome());
        log(inimigoAtual.getNome() + " usa: " + skillInimigo.getNome());

        Clash clash = new Clash(skillPlayer, skillInimigo);
        String resultado = clash.aplicar(player, inimigoAtual);

        log(resultado);

        atualizarUI();
        turno++;

        PauseTransition pausa = new PauseTransition(Duration.millis(600));
        pausa.setOnFinished(e -> verificarEstado());
        pausa.play();
    }

    // =========================
    // GAME FLOW CHECK
    // =========================
    private void verificarEstado() {
        if (!player.Tavivo()) {
            log("\n💀 " + player.getNome() + " foi derrotado!");
            setStatus("Derrota...");
            finalizarBatalha(false);
            return;
        }

        if (!inimigoAtual.Tavivo()) {
            log("\n✅ " + inimigoAtual.getNome() + " foi derrotado!");

            List<Inimigo> vivos = gs.getInimigosAtuais()
                .stream()
                .filter(Personagem::Tavivo)
                .toList();

            if (vivos.isEmpty()) {
                log("=== Wave " + gs.getCiclo().getWaveAtual() + " concluída! ===");
                finalizarBatalha(true);
            } else {
                inimigoAtual = vivos.get(0);
                log("Próximo inimigo: " + inimigoAtual.getNome());

                atualizarUI();
                habilitarInput();
            }
            return;
        }

        habilitarInput();
    }

    // =========================
    // BATTLE END
    // =========================
    private boolean finalizado = false;

private void finalizarBatalha(boolean vitoria) {

    if (finalizado) return; // 🔒 impede execução dupla
    finalizado = true;

    hboxSkills.setDisable(true);

    PauseTransition pausa = new PauseTransition(Duration.seconds(2));

    pausa.setOnFinished(e -> {

        Runnable acaoFinal;

        if (vitoria) {
            int waveConcluida = gs.getCiclo().getWaveAtual();

            // Pontos alimentam a Arvore de Habilidades.
            gs.avancarWave();

            // Moedas alimentam apenas a loja normal da run.
            int moedasGanhas = 3 + (waveConcluida / 2);
            gs.getProgressao().ganharMoedas(moedasGanhas);
            log("🪙 Você ganhou " + moedasGanhas + " moedas para gastar nesta run.");

            if (gs.getCiclo().getWaveAtual() > 10) {
                acaoFinal = () -> Navegar.ir("bossfinal");
            } else {
                acaoFinal = () -> Navegar.ir("area");
            }

        } else {
            gs.finalizarRun();
            acaoFinal = () -> Navegar.ir("end");
        }

        javafx.application.Platform.runLater(() -> {
            try {
                acaoFinal.run();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    });

    pausa.play();
}

    // =========================
    // UI UPDATE
    // =========================
    private void atualizarUI() {
        int wave = gs.getCiclo().getWaveAtual();
        boolean isBoss = gs.getCiclo().isBossWave();

        lblWave.setText("Wave " + wave + (isBoss ? " ⚠ BOSS" : ""));

        // Player
        lblPlayerName.setText(player.getNome());
        lblPlayerHp.setText(player.getHp() + " / " + player.getMaxHp());

        pbPlayerHp.setProgress((double) player.getHp() / player.getMaxHp());
        colorirBarra(pbPlayerHp, (double) player.getHp() / player.getMaxHp());

        // Enemy
        lblEnemyName.setText(inimigoAtual.getNome());
        lblEnemyHp.setText(inimigoAtual.getHp() + " / " + inimigoAtual.getMaxHp());

        pbEnemyHp.setProgress((double) inimigoAtual.getHp() / inimigoAtual.getMaxHp());
        colorirBarra(pbEnemyHp, (double) inimigoAtual.getHp() / inimigoAtual.getMaxHp());

        // Turno e pontos
        lblTurno.setText("Turno: " + turno);

        if (lblPontos != null) {
            lblPontos.setText("Pontos: " + gs.getProgressao().getPontos() + " | Moedas: " + gs.getProgressao().getMoedas());
        }
    }

    // =========================
    // UI HELPERS
    // =========================
    private void colorirBarra(ProgressBar pb, double ratio) {
        String cor = ratio > 0.6 ? "#44BB44"
                : ratio > 0.3 ? "#EE9900"
                : "#CC2222";

        pb.setStyle("-fx-accent: " + cor + ";");
    }

    private void log(String msg) {
        taLog.appendText(msg + "\n");
        gs.addLog(msg);
    }

    private void setStatus(String msg) {
        lblStatus.setText(msg);
    }

    private void habilitarInput() {
        aguardandoInput = true;
        hboxSkills.setDisable(false);
        setStatus("Escolha sua skill para atacar.");
    }
}