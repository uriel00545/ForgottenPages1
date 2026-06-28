package com.mycompany.ForgottenPages.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Guarda as informacoes temporarias da run do jogador.
 *
 * O deck concentra as skills que aparecem no inventario e os buffs/itens
 * comprados na loja normal. Como o Deck pertence ao Player/GameState da run,
 * tudo que esta aqui e perdido quando GameState.reset() cria uma nova partida.
 */
public class Deck {

    private static final int BONUS_VIDA_POR_NIVEL = 2;
    private static final int BONUS_DANO_POR_NIVEL = 1;
    private static final int INTERVALO_BONUS_CLASH = 10;

    private final List<Skill> skills = new ArrayList<>();

    private int pocoesVida;
    private int vidaRunNivel;
    private int danoRunNivel;
    private int clashRunNivel;

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> novasSkills) {
        skills.clear();
        if (novasSkills != null) {
            skills.addAll(novasSkills);
        }
    }

    public void adicionarSkill(Skill skill) {
        if (skill != null) {
            skills.add(skill);
        }
    }

    public void adicionarPocaoVida(int quantidade) {
        pocoesVida += Math.max(0, quantidade);
    }

    public boolean gastarPocaoVida() {
        if (pocoesVida <= 0) {
            return false;
        }
        pocoesVida--;
        return true;
    }

    public int getPocoesVida() {
        return pocoesVida;
    }

    public void setPocoesVida(int pocoesVida) {
        this.pocoesVida = Math.max(0, pocoesVida);
    }

    public int getVidaRunNivel() {
        return vidaRunNivel;
    }

    public void setVidaRunNivel(int vidaRunNivel) {
        this.vidaRunNivel = Math.max(0, vidaRunNivel);
    }

    public int getDanoRunNivel() {
        return danoRunNivel;
    }

    public void setDanoRunNivel(int danoRunNivel) {
        this.danoRunNivel = Math.max(0, danoRunNivel);
    }

    public int getClashRunNivel() {
        return clashRunNivel;
    }

    public void setClashRunNivel(int clashRunNivel) {
        this.clashRunNivel = Math.max(0, clashRunNivel);
    }

    /**
     * Remove do Player os efeitos comprados na loja normal e limpa itens da run.
     * Isso e chamado quando a run termina para garantir que nada da loja normal
     * continue existindo depois da morte/fim de jogo.
     */
    public void limparTemporariosDoPlayer(Player player) {
        if (player != null) {
            int bonusVida = getBonusVidaTotal();
            int bonusDano = getBonusDanoTotal();
            int bonusClash = getBonusClashTotal();
            if (bonusVida > 0) {
                player.setMaxHp(Math.max(1, player.getMaxHp() - bonusVida));
                player.setHp(Math.min(player.getHp(), player.getMaxHp()));
            }

            for (Skill skill : skills) {
                if (skill.getTipo() == Skilltype.ATTACK && bonusDano > 0) {
                    skill.setBasePower(Math.max(0, skill.getBasePower() - bonusDano));
                }

                if (bonusClash > 0) {
                    skill.setCoinPower(Math.max(0, skill.getCoinPower() - bonusClash));
                }

            }
        }

        pocoesVida = 0;
        vidaRunNivel = 0;
        danoRunNivel = 0;
        clashRunNivel = 0;
    }

    public int getBonusVidaTotal() {
        return vidaRunNivel * BONUS_VIDA_POR_NIVEL;
    }

    public int getBonusDanoTotal() {
        return danoRunNivel * BONUS_DANO_POR_NIVEL;
    }

    public int getBonusClashTotal() {
        return clashRunNivel / INTERVALO_BONUS_CLASH;
    }
}
