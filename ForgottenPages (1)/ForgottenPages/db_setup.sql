-- Execute este script no PostgreSQL antes de rodar o jogo
-- psql -U postgres -d postgres -f db_setup.sql

CREATE DATABASE dungeonmaster;
\c dungeonmaster

CREATE TABLE IF NOT EXISTS jogadores (
    id           SERIAL PRIMARY KEY,
    nome         VARCHAR(80) UNIQUE NOT NULL,
    melhor_wave  INT DEFAULT 0,
    total_runs   INT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS upgrades_jogador (
    id            SERIAL PRIMARY KEY,
    jogador_id    INT REFERENCES jogadores(id) ON DELETE CASCADE,
    tipo          VARCHAR(40),
    nivel         INT DEFAULT 1
);
