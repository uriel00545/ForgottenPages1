package com.mycompany.ForgottenPages.model;


public class CombatAction {
     private Personagem usuario;
    private Personagem alvo;
    private Skill skill;

    public CombatAction(Personagem usuario, Personagem alvo, Skill skill) {
        this.usuario = usuario;
        this.alvo = alvo;
        this.skill = skill;
    }

    public Personagem getUsuario() {
        return usuario;
    }

    public void setUsuario(Personagem usuario) {
        this.usuario = usuario;
    }

    public Personagem getAlvo() {
        return alvo;
    }

    public void setAlvo(Personagem alvo) {
        this.alvo = alvo;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

   
}
