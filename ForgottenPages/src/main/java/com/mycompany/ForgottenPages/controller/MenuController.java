package com.mycompany.ForgottenPages.controller;

import com.mycompany.ForgottenPages.dao.playerDAO;
import com.mycompany.ForgottenPages.model.GameState;
import com.mycompany.ForgottenPages.model.Player;
import com.mycompany.ForgottenPages.model.Progressao;
import com.mycompany.ForgottenPages.util.EfeitoBotao;
import com.mycompany.ForgottenPages.util.Navegar;

import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Menu principal do jogo.
 * Responsável por iniciar run, navegação e saída.
 */
public class MenuController {

    @FXML private TextField tfNome;

    @FXML private Button jogar;
    @FXML private Button encerrar;
    @FXML private Button ranking;
    @FXML private Button creditos;

    @FXML
    public void initialize() {
        EfeitoBotao.applyLoREffect(jogar);
        EfeitoBotao.applyLoREffect(encerrar);
        EfeitoBotao.applyLoREffect(ranking);
        EfeitoBotao.applyLoREffect(creditos);
    }

    @FXML
    private void jogar() {
        String nome = obterNomeJogador();

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
        String nome = (tfNome != null) ? tfNome.getText().trim() : "";
        return nome.isEmpty() ? "Sinner" : nome;
    }
}
