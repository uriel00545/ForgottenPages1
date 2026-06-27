
package com.mycompany.ForgottenPages.model;

/**
 *
 * @author jaeau
 */
public abstract class Personagem {
     private String nome;
     
    private int hp;
    private int maxHp;

    public Personagem(String nome, int hp, int maxHp) {
        this.nome = nome;
        this.hp = hp;
        this.maxHp = maxHp;
    }
    
   

    public boolean Tavivo() {
        return hp > 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    
    
    
}
