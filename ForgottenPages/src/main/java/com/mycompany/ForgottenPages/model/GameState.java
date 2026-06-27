package com.mycompany.ForgottenPages.model;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    private static GameState instance;

    private Player player;
    private final Ciclo ciclo = new Ciclo();
    private List<Inimigo> inimigosAtuais = new ArrayList<>();
    private final List<String> battleLog = new ArrayList<>();
    private Progressao progressao = new Progressao(0);

    // nome digitado no menu para busca/criação no banco
    private String nomeJogador = "Sinner";

    // controla se a partida já acabou
    private boolean gameOver = false;
    private boolean resultadoSalvo = false;

    private GameState() {}

    public static GameState getInstance() {
        if (instance == null) instance = new GameState();
        return instance;
    }

    /**
     * Reseta a run inteira: player, deck, moedas, itens, buffs temporarios,
     * inimigos e ciclo voltam ao começo.
     */
    public static void reset() {
        instance = new GameState();
    }

    public void limparEstadoDeFim() {
        this.gameOver = false;
    }

    public void iniciarWave() {
        if (gameOver) {
            return;
        }

        if (player == null) player = PlayerFactory.criarPadrao();

        battleLog.clear();

        if (ciclo.isBossWave()) {
            inimigosAtuais = new ArrayList<>(List.of(ciclo.criaBoss()));
        } else {
            inimigosAtuais = new ArrayList<>(ciclo.criaInimigos());
        }
    }

    public boolean waveConcluida() {
        return inimigosAtuais.stream().noneMatch(Personagem::Tavivo);
    }

    public void avancarWave() {
        if (gameOver) {
            return;
        }

        progressao.ganharPonto(1);
        ciclo.avancarWave();
    }

    public void marcarGameOver() {
        this.gameOver = true;
    }

    public void finalizarRun() {
        if (!gameOver && player != null) {
            player.getDeck().limparTemporariosDoPlayer(player);
        }
        marcarGameOver();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isResultadoSalvo() {
        return resultadoSalvo;
    }

    public void marcarResultadoSalvo() {
        this.resultadoSalvo = true;
    }

    public Deck getDeck() {
        if (player == null) {
            player = PlayerFactory.criarPadrao();
        }
        return player.getDeck();
    }

    public void addLog(String linha) {
        battleLog.add(linha);
    }

    public List<String> getBattleLog() {
        return battleLog;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player p) {
        this.player = p;
    }

    public Ciclo getCiclo() {
        return ciclo;
    }

    public Progressao getProgressao() {
        return progressao;
    }

    public List<Inimigo> getInimigosAtuais() {
        return inimigosAtuais;
    }

    public void setInimigosAtuais(List<Inimigo> list) {
        inimigosAtuais = list;
    }

    public String getNomeJogador() {
        return nomeJogador;
    }

    public void setNomeJogador(String n) {
        nomeJogador = n;
    }
}
