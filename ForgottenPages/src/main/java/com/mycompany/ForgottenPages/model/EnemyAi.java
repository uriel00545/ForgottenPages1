/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ForgottenPages.model;

import java.util.List;

/**
 *
 * @author jaeau
 */
public interface EnemyAi{
     CombatAction chooseAction(
        Inimigo enemy,
        List<Personagem> aliados,
        List<Personagem> inimigos
    );

    
}
