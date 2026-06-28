package com.mycompany.ForgottenPages.model;

import com.mycompany.ForgottenPages.util.CalculaMoeda;

public class Clash {

    public enum Resultado { ATACANTE_VENCE, DEFENSOR_VENCE, EMPATE }

    private final Skill atacante;
    private final Skill defensor;

    private int valorAtacante;
    private int valorDefensor;
    private Resultado resultado;

    public Clash(Skill atacante, Skill defensor) {
        this.atacante = atacante;
        this.defensor = defensor;
    }

    public Resultado resolver() {
        valorAtacante = CalculaMoeda.calcular(atacante);
        valorDefensor = CalculaMoeda.calcular(defensor);

        if (valorAtacante > valorDefensor) {
            resultado = Resultado.ATACANTE_VENCE;
        } else if (valorDefensor > valorAtacante) {
            resultado = Resultado.DEFENSOR_VENCE;
        } else {
            resultado = Resultado.EMPATE;
        }
        return resultado;
    }

    
    public String aplicar(Personagem usuarioAtk, Personagem usuarioDef) {
        // garante que resolver() sempre roda antes de aplicar dano
        resolver();

        StringBuilder log = new StringBuilder();
        log.append(String.format("Clash: %s [%d] vs %s [%d] -> ",
            atacante.getNome(), valorAtacante,
            defensor.getNome(), valorDefensor));

        switch (resultado) {
            case ATACANTE_VENCE -> {
                int dano = Math.max(0, valorAtacante);
                usuarioDef.setHp(Math.max(0, usuarioDef.getHp() - dano));
                log.append(String.format("Atacante vence! %s recebe %d de dano.",
                    usuarioDef.getNome(), dano));
            }
            case DEFENSOR_VENCE -> {
                int dano = Math.max(0, valorDefensor);
                usuarioAtk.setHp(Math.max(0, usuarioAtk.getHp() - dano));
                log.append(String.format("Defensor contra-ataca! %s recebe %d de dano.",
                    usuarioAtk.getNome(), dano));
            }
            case EMPATE -> {
                int dano = Math.max(0, valorAtacante / 2);
                usuarioAtk.setHp(Math.max(0, usuarioAtk.getHp() - dano));
                usuarioDef.setHp(Math.max(0, usuarioDef.getHp() - dano)); 
                log.append(String.format("Empate! Ambos recebem %d de dano.", dano));
            }
        }
        return log.toString();
    }

    public int getValorAtacante()  { return valorAtacante; }
    public int getValorDefensor()  { return valorDefensor; }
    public Resultado getResultado(){ return resultado; }
    public Skill getAtacante()     { return atacante; }
    public Skill getDefensor()     { return defensor; }
}
