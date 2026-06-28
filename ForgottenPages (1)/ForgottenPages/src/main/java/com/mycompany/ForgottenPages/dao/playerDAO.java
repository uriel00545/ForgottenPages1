package com.mycompany.ForgottenPages.dao;

import com.mycompany.ForgottenPages.model.Player;
import com.mycompany.ForgottenPages.model.PlayerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DDL esperado (rode uma vez no banco):
 *
 * CREATE TABLE IF NOT EXISTS jogadores (
 *     id        SERIAL PRIMARY KEY,
 *     nome      VARCHAR(80) UNIQUE NOT NULL,
 *     melhor_wave  INT DEFAULT 0,
 *     total_runs   INT DEFAULT 0
 * );
 *
 * CREATE TABLE IF NOT EXISTS upgrades_jogador (
 *     id            SERIAL PRIMARY KEY,
 *     jogador_id    INT REFERENCES jogadores(id),
 *     tipo          VARCHAR(40),
 *     nivel         INT DEFAULT 1
 * );
 */
public class playerDAO {

    /** Busca por nome; cria registro se não existir. Retorna Player pronto. */
    public static Player buscarOuCriar(String nome) {
        try (Connection conn = Conexao.getConexao()) {

            // tenta buscar
            PreparedStatement sel = conn.prepareStatement(
                "SELECT id FROM jogadores WHERE nome = ?");
            sel.setString(1, nome);
            ResultSet rs = sel.executeQuery();

            if (!rs.next()) {
                // cria novo jogador
                PreparedStatement ins = conn.prepareStatement(
                    "INSERT INTO jogadores (nome) VALUES (?)");
                ins.setString(1, nome);
                ins.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("[playerDAO] erro ao buscar/criar: " + e.getMessage());
        }

        Player p = PlayerFactory.criarPadrao();
        p.setNome(nome);
        return p;
    }


    /** Carrega a soma dos upgrades permanentes comprados na Arvore de Habilidades. */
    public static Map<String, Integer> carregarUpgrades(String nomeJogador) {
        Map<String, Integer> upgrades = new HashMap<>();
        String sql = """
            SELECT u.tipo, COALESCE(SUM(u.nivel), 0) AS nivel_total
              FROM upgrades_jogador u
              JOIN jogadores j ON j.id = u.jogador_id
             WHERE j.nome = ?
             GROUP BY u.tipo
            """;

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nomeJogador);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                upgrades.put(rs.getString("tipo"), rs.getInt("nivel_total"));
            }
        } catch (SQLException e) {
            System.err.println("[playerDAO] erro ao carregar upgrades: " + e.getMessage());
        }

        return upgrades;
    }

    /** Salva resultado de uma run (wave atingida). */
    public static void salvarResultadoRun(String nome, int waveAtingida) {
        String sql = """
            UPDATE jogadores
               SET total_runs   = total_runs + 1,
                   melhor_wave  = GREATEST(melhor_wave, ?)
             WHERE nome = ?
            """;
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, waveAtingida);
            ps.setString(2, nome);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[playerDAO] erro ao salvar run: " + e.getMessage());
        }
    }

    /** Salva upgrade adquirido. */
    public static void salvarUpgrade(String nomeJogador, String tipo, int nivel) {
        String sqlId = "SELECT id FROM jogadores WHERE nome = ?";
        String sqlUp = """
            INSERT INTO upgrades_jogador (jogador_id, tipo, nivel) VALUES (?, ?, ?)
            ON CONFLICT DO NOTHING
            """;
        try (Connection conn = Conexao.getConexao()) {
            PreparedStatement ps1 = conn.prepareStatement(sqlId);
            ps1.setString(1, nomeJogador);
            ResultSet rs = ps1.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                PreparedStatement ps2 = conn.prepareStatement(sqlUp);
                ps2.setInt(1, id);
                ps2.setString(2, tipo);
                ps2.setInt(3, nivel);
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("[playerDAO] erro ao salvar upgrade: " + e.getMessage());
        }
    }

    /** Top 10 para o ranking. Retorna lista de String "nome - wave X". */
    public static List<String[]> ranking() {
    List<String[]> lista = new ArrayList<>();

    String sql = """
        SELECT nome, melhor_wave, total_runs
          FROM jogadores
         ORDER BY melhor_wave DESC, total_runs DESC, nome ASC
         LIMIT 10
        """;

    try (Connection conn = Conexao.getConexao();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            lista.add(new String[]{
                    rs.getString("nome"),
                    String.valueOf(rs.getInt("melhor_wave")),
                    String.valueOf(rs.getInt("total_runs"))
            });
        }

    } catch (SQLException e) {
        System.err.println("[playerDAO] erro ao buscar ranking: " + e.getMessage());
    }

    return lista;
}
    
}
