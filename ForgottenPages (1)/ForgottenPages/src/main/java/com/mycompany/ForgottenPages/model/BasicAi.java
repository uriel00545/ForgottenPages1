package com.mycompany.ForgottenPages.model;

import java.util.List;

public class BasicAi implements EnemyAi {

    @Override
    public CombatAction chooseAction(
        Inimigo inimigo,
        List<Personagem> aliados,
        List<Personagem> inimigos
    ) { 
        
         if (inimigo.getSkills().isEmpty()) {
        throw new IllegalStateException(
            inimigo.getNome() + " não possui skills."
        ); }
         
        // Escolhe um alvo aleatório entre os inimigos (players)
        Personagem alvo = inimigos.get((int)(Math.random() * inimigos.size()));

        // Escolhe uma skill aleatória do inimigo
        Skill skill = inimigo.getSkills().get((int)(Math.random() * inimigo.getSkills().size()));

        return new CombatAction(inimigo, alvo, skill);
    }
}