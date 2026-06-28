package com.mycompany.ForgottenPages.model;

import java.util.ArrayList;
import java.util.List;

public class Ciclo {
    private static final String[] TIPOS_INIMIGOS = {"Goblin", "Esqueleto", "Zumbi"};

    private int waveAtual = 1;

    public Ciclo() {}

    public boolean isBossWave() {
        return waveAtual % 5 == 0;
    }

    public Boss criaBoss() {
        int cicloBoss = Math.max(0, (waveAtual / 5) - 1);
        int hp  = (int)(240 * Math.pow(1.50, cicloBoss)) + (waveAtual * 20);
        int atk = (int)(18  * Math.pow(1.35, cicloBoss)) + (waveAtual * 3);
        int poderDadoAtaque = 5 + (waveAtual / 4);
        int poderDadoDefesa = 3 + (waveAtual / 5);
        int qtdDadosAtaque = 2 + (waveAtual / 10);

        Skill ataque = new Skill(
            "Golpe do Rei Esqueleto", atk, poderDadoAtaque, qtdDadosAtaque,
            Skilltype.ATTACK, DamageType.BLUNT
        );
        Skill defesa = new Skill(
            "Barreira de Ossos", 0, poderDadoDefesa, 1,
            Skilltype.DEFENSE, DamageType.BLUNT
        );

        Boss boss = new Boss(new BasicAi(), "Rei Esqueleto", hp, hp);
        boss.setNome("Rei Esqueleto - Wave " + waveAtual);
        boss.setMaxHp(hp);
        boss.setHp(hp);

        boss.getSkills().add(ataque);
        boss.getSkills().add(defesa);
        return boss;
    }

    public List<Inimigo> criaInimigos() {
        int count = 1 + (waveAtual / 3); // mais inimigos conforme waves avançam
        List<Inimigo> lista = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int hp  = (int)(45 * Math.pow(1.18, waveAtual - 1)) + ((waveAtual - 1) * 8);
            int atk = (int)(9  * Math.pow(1.13, waveAtual - 1)) + ((waveAtual - 1) * 2);
            int poderDadoAtaque = 3 + (waveAtual / 3);
            int poderDadoDefesa = 2 + (waveAtual / 4);
            int qtdDadosAtaque = 1 + (waveAtual / 8);

            String nomeBase = TIPOS_INIMIGOS[i % TIPOS_INIMIGOS.length];
            String nomeInimigo = nomeBase;
            if (i >= TIPOS_INIMIGOS.length) {
                nomeInimigo += " " + ((i / TIPOS_INIMIGOS.length) + 1);
            }

            Skill ataque = new Skill(
                "Ataque do " + nomeBase, atk, poderDadoAtaque, qtdDadosAtaque,
                Skilltype.ATTACK, DamageType.SLASH
            );
            Skill defesa = new Skill(
                "Defesa do " + nomeBase, 0, poderDadoDefesa, 1,
                Skilltype.DEFENSE, DamageType.SLASH
            );

            Inimigo inimigo = new Inimigo(new BasicAi(), nomeInimigo, hp, hp);
            inimigo.setNome(nomeInimigo);
            inimigo.setMaxHp(hp);
            inimigo.setHp(hp);

            inimigo.getSkills().add(ataque);
            inimigo.getSkills().add(defesa);
            lista.add(inimigo);
        }
        return lista;
    }

    public void avancarWave() {
        waveAtual++;
    }

    public int getWaveAtual() { return waveAtual; }
    public void setWaveAtual(int waveAtual) { this.waveAtual = waveAtual; }
}
