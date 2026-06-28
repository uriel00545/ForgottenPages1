package com.mycompany.ForgottenPages.model;

public class Luz {

    private int atual;
    private int maxima;
    private int limiteMaximo;

    public Luz() {
        this(3, 5);
    }

    public Luz(int maximaInicial, int limiteMaximo) {
        this.maxima = maximaInicial;
        this.atual = maximaInicial;
        this.limiteMaximo = limiteMaximo;
    }

    public int getAtual() {
        return atual;
    }

    public int getMaxima() {
        return maxima;
    }

    public int getLimiteMaximo() {
        return limiteMaximo;
    }

    public boolean temLuz(int custo) {
        return atual >= custo;
    }

    public boolean gastar(int custo) {
        if (custo < 0) {
            return false;
        }

        if (!temLuz(custo)) {
            return false;
        }

        atual -= custo;
        return true;
    }

    public void recuperar(int quantidade) {
        if (quantidade <= 0) {
            return;
        }

        atual = Math.min(maxima, atual + quantidade);
    }

    public boolean aumentarMaxima(int quantidade) {
        if (quantidade <= 0) {
            return false;
        }

        int antes = maxima;
        maxima = Math.min(limiteMaximo, maxima + quantidade);

        if (maxima > antes) {
            atual = maxima;
            return true;
        }

        return false;
    }

    public void reiniciarCheia() {
        atual = maxima;
    }

    public void resetar() {
        maxima = 3;
        atual = 3;
        limiteMaximo = 5;
    }
}