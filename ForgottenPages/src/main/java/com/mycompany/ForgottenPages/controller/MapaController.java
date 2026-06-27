package com.mycompany.ForgottenPages.controller;

import com.mycompany.ForgottenPages.util.Navegar;
import java.io.IOException;
import javafx.fxml.FXML;

public class MapaController {
    
    
    @FXML
    private void voltar() throws IOException {
    Navegar.ir("area.fxml");
    }
}
