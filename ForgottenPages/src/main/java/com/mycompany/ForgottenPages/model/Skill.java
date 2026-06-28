package com.mycompany.ForgottenPages.model;

public class Skill {

    private String nome;
    private int basePower;
    private int coinPower;
    private int coinCount;
    private int custo;
    private Skilltype tipo;
    private DamageType damageType;

    public Skill(String nome, int basePower, int coinPower, int coinCount, Skilltype tipo, DamageType damageType) {
        this(nome, basePower, coinPower, coinCount, 1, tipo, damageType);
    }

    public Skill(String nome, int basePower, int coinPower, int coinCount, int custo, Skilltype tipo, DamageType damageType) {
        this.nome = nome;
        this.basePower = basePower;
        this.coinPower = coinPower;
        this.coinCount = coinCount;
        this.custo = Math.max(0, custo);
        this.tipo = tipo;
        this.damageType = damageType;
    }

    public String getNome() {
        return nome;
    }

    public int getBasePower() {
        return basePower;
    }

    public int getCoinPower() {
        return coinPower;
    }

    public int getCoinCount() {
        return coinCount;
    }

    public int getCusto() {
        return custo;
    }

    public Skilltype getTipo() {
        return tipo;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setBasePower(int basePower) {
        this.basePower = basePower;
    }

    public void setCoinPower(int coinPower) {
        this.coinPower = coinPower;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public void setCusto(int custo) {
        this.custo = Math.max(0, custo);
    }

    public void setTipo(Skilltype tipo) {
        this.tipo = tipo;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }
}