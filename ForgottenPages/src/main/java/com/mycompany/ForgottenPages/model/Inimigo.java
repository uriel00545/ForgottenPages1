
package com.mycompany.ForgottenPages.model;

import java.util.List;
import java.util.ArrayList;


public class Inimigo extends Personagem{
    private EnemyAi ai;
    private List<Skill> skills = new ArrayList();

    public Inimigo(EnemyAi ai, String nome, int hp, int maxHp) {
        super(nome, hp, maxHp);
        this.ai = ai;
    }
    
   


    public EnemyAi getAi() {
        return ai;
    }

    public void setAi(EnemyAi ai) {
        this.ai = ai;
    }

     public List<Skill> getSkills() {
        return skills;
    }
     
    public void setSkills(List skills) {
        this.skills = skills;
    }

    
    
    
}
