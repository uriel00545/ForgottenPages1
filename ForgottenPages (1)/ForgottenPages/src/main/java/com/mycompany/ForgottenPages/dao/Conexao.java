package com.mycompany.ForgottenPages.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ConnectionFactory para PostgreSQL.
 * Altere URL, USER e PASSWORD conforme seu banco local.
 */
public class Conexao {

    private static final String URL  = "jdbc:postgresql://localhost:5432/ForgottenPagesBD";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";

    private Conexao() {}

    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    /** Fecha silenciosamente - use em finally ou try-with-resources */
    public static void fechar(Connection conn) {
        if (conn != null) {
            try { conn.close(); } catch (SQLException ignored) {}
        }
    }
}
