
package com.mycompany.ForgottenPages.model;

/**
 *
 * @author aluno
 */
public class Skill {
    private String nome;
    private int basePower;
    private int coinPower;
    private int coinCount;
    private Skilltype tipo;

    private DamageType damageType;

    public Skill(String nome, int basePower, int coinPower, int coinCount, Skilltype tipo, DamageType damageType) {
        this.nome = nome;
        this.basePower = basePower;
        this.coinPower = coinPower;
        this.coinCount = coinCount;
        this.tipo = tipo;
        this.damageType = damageType;
    }

    public Skilltype getTipo() {
        return tipo;
    }

    public void setTipo(Skilltype tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getBasePower() {
        return basePower;
    }

    public void setBasePower(int basePower) {
        this.basePower = basePower;
    }

    public int getCoinPower() {
        return coinPower;
    }

    public void setCoinPower(int coinPower) {
        this.coinPower = coinPower;
    }

    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

}