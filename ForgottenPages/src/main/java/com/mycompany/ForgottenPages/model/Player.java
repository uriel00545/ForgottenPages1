package com.mycompany.ForgottenPages.model;

import java.util.List;

public class Player extends Personagem {

    private Deck deck = new Deck();
    private Luz luz = new Luz();

    public Player(String nome, int hp, int maxHp) {
        super(nome, hp, maxHp);
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck != null ? deck : new Deck();
    }

    public Luz getLuz() {
        return luz;
    }

    public void setLuz(Luz luz) {
        this.luz = luz != null ? luz : new Luz();
    }

    public List<Skill> getSkills() {
        return deck.getSkills();
    }

    public void setSkills(List<Skill> skills) {
        deck.setSkills(skills);
    }
}