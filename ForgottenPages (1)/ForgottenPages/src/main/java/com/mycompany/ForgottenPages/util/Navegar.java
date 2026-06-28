

package com.mycompany.ForgottenPages.util;

import com.mycompany.ForgottenPages.main.App;
import java.io.IOException;
import javafx.scene.control.Alert;


public class Navegar {
   
    public static void ir(String tela) {
        try {
            App.setRoot(tela);
            
        } catch (IOException e) {
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de navegação");
            alert.setHeaderText("Não foi possível carregar: " + tela);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}
