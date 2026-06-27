package com.mycompany.ForgottenPages.model;

/**
 * Guarda a progressao geral do jogador.
 *
 * Pontos = usados na Arvore de Habilidades.
 * Moedas = usadas na Loja normal da run.
 *
 * Itens, skills e buffs temporarios da run agora ficam no Deck do Player.
 */
public class Progressao {
    private int pontos;
    private int moedas;

    // Melhorias principais da Arvore de Habilidades
    private int vidaNivel;
    private int clashNivel;
    private int danoNivel;

    public Progressao(int pontos) {
        this.pontos = Math.max(0, pontos);
        this.moedas = 0;
        this.vidaNivel = 0;
        this.clashNivel = 0;
        this.danoNivel = 0;
    }

    // =========================
    // PONTOS - ARVORE DE HABILIDADES
    // =========================
    public void ganharPonto(int valor) {
        pontos += Math.max(0, valor);
    }

    public boolean gastarPonto(int quantidade) {
        if (quantidade <= 0) return true;
        if (pontos < quantidade) return false;
        pontos -= quantidade;
        return true;
    }

    // =========================
    // MOEDAS - LOJA NORMAL DA RUN
    // =========================
    public void ganharMoedas(int valor) {
        moedas += Math.max(0, valor);
    }

    public boolean gastarMoedas(int quantidade) {
        if (quantidade <= 0) return true;
        if (moedas < quantidade) return false;
        moedas -= quantidade;
        return true;
    }

    // Compatibilidade com codigo antigo que usava moeda no singular
    public int getMoeda() {
        return moedas;
    }

    public void setMoeda(int moeda) {
        moedas = Math.max(0, moeda);
    }

    public int getMoedas() {
        return moedas;
    }

    public void setMoedas(int moedas) {
        this.moedas = Math.max(0, moedas);
    }

    public int getDanoNivel() {
        return danoNivel;
    }

    public void setDanoNivel(int danoNivel) {
        this.danoNivel = Math.max(0, danoNivel);
    }

    public int getVidaNivel() {
        return vidaNivel;
    }

    public void setVidaNivel(int vidaNivel) {
        this.vidaNivel = Math.max(0, vidaNivel);
    }

    public int getClashNivel() {
        return clashNivel;
    }

    public void setClashNivel(int clashNivel) {
        this.clashNivel = Math.max(0, clashNivel);
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = Math.max(0, pontos);
    }
}
