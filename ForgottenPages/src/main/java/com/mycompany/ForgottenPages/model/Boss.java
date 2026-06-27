package com.mycompany.ForgottenPages.model;

import java.util.List;

public class Boss extends Inimigo {

    public Boss(EnemyAi ai, String nome, int hp, int maxHp) {
        super(ai, nome, hp, maxHp);
    }

  
    
    
    @Override
    public void setMaxHp(int maxHp) {
        super.setMaxHp(maxHp);
    }
}
