package com.mycompany.ForgottenPages.controller;

import com.mycompany.ForgottenPages.dao.playerDAO;
import com.mycompany.ForgottenPages.util.Navegar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

/**
 * Controller do ranking de jogadores.
 * Exibe estatísticas de runs salvas no banco.
 */
public class RankingController {

    // =========================
    // UI
    // =========================
    @FXML private TableView<RankEntry> tabelaRanking;
    @FXML private TableColumn<RankEntry, String> colNome;
    @FXML private TableColumn<RankEntry, Integer> colWave;
    @FXML private TableColumn<RankEntry, Integer> colRuns;

    // =========================
    // INIT
    // =========================
    @FXML
    public void initialize() {
        configurarColunas();
        carregarRanking();
    }

    // =========================
    // TABLE CONFIG
    // =========================
    private void configurarColunas() {
        if (colNome != null)
            colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        if (colWave != null)
            colWave.setCellValueFactory(new PropertyValueFactory<>("wave"));

        if (colRuns != null)
            colRuns.setCellValueFactory(new PropertyValueFactory<>("runs"));
    }

    // =========================
    // DATA LOAD
    // =========================
    private void carregarRanking() {
    List<String[]> dados = playerDAO.ranking();

    ObservableList<RankEntry> lista = FXCollections.observableArrayList();

    for (int i = 0; i < dados.size(); i++) {
        String[] row = dados.get(i);

        lista.add(new RankEntry(
                i + 1,
                row[0],
                Integer.parseInt(row[1]),
                Integer.parseInt(row[2])
        ));
    }

    lista.sort((a, b) -> Integer.compare(b.getWave(), a.getWave()));

    if (tabelaRanking != null) {
        tabelaRanking.setItems(lista);
    }
}
    // =========================
    // NAVIGATION
    // =========================
    @FXML
    private void voltar() {
        Navegar.ir("menu");
    }

    // =========================
    // MODEL
    // =========================
    public static class RankEntry {

        private final int posicao;
        private final String nome;
        private final int wave;
        private final int runs;

        public RankEntry(int posicao, String nome, int wave, int runs) {
            this.posicao = posicao;
            this.nome = nome;
            this.wave = wave;
            this.runs = runs;
        }

        public int getPosicao() {
            return posicao;
        }

        public String getNome() {
            return nome;
        }

        public int getWave() {
            return wave;
        }

        public int getRuns() {
            return runs;
        }
    }
}