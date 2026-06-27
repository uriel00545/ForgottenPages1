package com.mycompany.ForgottenPages.model;

import java.util.List;

public class Player extends Personagem {

    private Deck deck = new Deck();

    public Player(String nome, int hp, int maxHp) {
        super(nome, hp, maxHp);
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck != null ? deck : new Deck();
    }

    /**
     * Mantido por compatibilidade com os controllers antigos.
     * As skills agora ficam dentro do Deck.
     */
    public List<Skill> getSkills() {
        return deck.getSkills();
    }

    public void setSkills(List<Skill> skills) {
        deck.setSkills(skills);
    }
}
