/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ForgottenPages.model;

/**
 *
 * @author jaeau
 */
public class EGO {
     private String nome;
    private int sanityCost;
    private Skill skill;

    public EGO(String nome, int sanityCost, Skill skill) {
        this.nome = nome;
        this.sanityCost = sanityCost;
        this.skill = skill;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getSanityCost() {
        return sanityCost;
    }

    public void setSanityCost(int sanityCost) {
        this.sanityCost = sanityCost;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }
}
