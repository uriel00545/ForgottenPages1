package com.mycompany.ForgottenPages.model;

import java.util.ArrayList;
import java.util.List;

/** Cria o Player padrão para início de partida. */
public class PlayerFactory {

    public static Player criarPadrao() {
        Player p = new Player("Sinnercheiro", 100, 100);

        Deck deck = new Deck();
        List<Skill> skills = new ArrayList<>();
        skills.add(new Skill("Estocada", 8, 4, 2, Skilltype.ATTACK, DamageType.PIERCE));
        skills.add(new Skill("Cutilada", 6, 3, 3, Skilltype.ATTACK, DamageType.SLASH));
        skills.add(new Skill("Pancada", 10, 5, 1, Skilltype.ATTACK, DamageType.BLUNT));
        skills.add(new Skill("Defender", 0, 6, 2, Skilltype.DEFENSE, DamageType.BLUNT));
        skills.add(new Skill("Esquivar", 0, 4, 2, Skilltype.EVADE, DamageType.SLASH));
        deck.setSkills(skills);

        p.setDeck(deck);
        return p;
    }
}
