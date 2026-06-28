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
public class Batalha {
    private List<Player> players;

    public Batalha(List<Player> players, List<Inimigo> enemies, int turn) {
        this.players = players;
        this.enemies = enemies;
        this.turn = turn;
    }
    private List<Inimigo> enemies;

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Inimigo> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Inimigo> enemies) {
        this.enemies = enemies;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    private int turn;
}
