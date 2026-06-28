/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ForgottenPages.util;

import com.mycompany.ForgottenPages.model.Skill;

/**
 *
 * @author jaeau
 */
public class CalculaMoeda {
    public static int calcular(Skill skill) {

        int result = skill.getBasePower();

        for(int i = 0; i < skill.getCoinCount(); i++) {

            if(Math.random() < 0.5) {
                result += skill.getCoinPower();
            }
        }

        return result;
    }
}
