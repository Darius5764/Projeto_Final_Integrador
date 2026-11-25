SET NAMES utf8mb4;
CREATE DATABASE portal_ecl CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE portal_ecl;

CREATE TABLE curso (
   id_curso             INT AUTO_INCREMENT PRIMARY KEY,
   nome                 VARCHAR(150) NOT NULL,
   area                 VARCHAR(100),
   modalidade           VARCHAR(50) NOT NULL,
   componentes          TEXT,
   perfil_saida         TEXT,
   saidas_profissionais TEXT,
   fonte                VARCHAR(255) NOT NULL,
   data_verificacao     DATE NOT NULL,
   ativo                BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE utilizador (
   id            INT AUTO_INCREMENT PRIMARY KEY,
   nome          VARCHAR(100) NOT NULL,
   identificador VARCHAR(50) NOT NULL UNIQUE,
   password_hash VARCHAR(255) NOT NULL,
   perfil        ENUM('GESTOR','TECNICO','FORMADOR','FORMANDO') NOT NULL,
   ativo         BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE sala (
   id       INT AUTO_INCREMENT PRIMARY KEY,
   codigo   VARCHAR(20) NOT NULL UNIQUE,
   edificio VARCHAR(50),
   piso     VARCHAR(10)
);

CREATE TABLE pre_inscricao (
   id          INT AUTO_INCREMENT PRIMARY KEY,
   id_curso    INT NOT NULL,
   nome        VARCHAR(120) NOT NULL,
   email       VARCHAR(150) NOT NULL,
   telefone    VARCHAR(20),
   observacoes VARCHAR(500),
   estado      ENUM('RECEBIDA','EM_ANALISE','CONTACTADA') NOT NULL DEFAULT 'RECEBIDA',
   criado_em   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   CONSTRAINT fk_pi_curso FOREIGN KEY (id_curso) REFERENCES curso(id_curso)
);

CREATE TABLE ocorrencia (
   id                INT AUTO_INCREMENT PRIMARY KEY,
   id_utilizador     INT NULL,
   nome_reportante   VARCHAR(100) NOT NULL,
   perfil_reportante ENUM('FORMANDO','FORMADOR') NOT NULL,
   id_sala           INT NOT NULL,
   categoria         ENUM('PC','PROJETOR','REDE','WIFI','IMPRESSORA','SOFTWARE','MOBILIARIO','OUTRO') NOT NULL,
   descricao         VARCHAR(1000) NOT NULL,
   impede_aula       BOOLEAN NOT NULL DEFAULT FALSE,
   prioridade        ENUM('BAIXA','NORMAL','ELEVADA') NOT NULL,
   estado            ENUM('ABERTA','EM_ANALISE','EM_RESOLUCAO','RESOLVIDA') NOT NULL DEFAULT 'ABERTA',
   resolucao         TEXT,
   criado_em         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   atualizado_em     DATETIME NULL ON UPDATE CURRENT_TIMESTAMP,
   resolvido_em      DATETIME NULL,
   CONSTRAINT fk_oc_utilizador FOREIGN KEY (id_utilizador) REFERENCES utilizador(id),
   CONSTRAINT fk_oc_sala       FOREIGN KEY (id_sala)       REFERENCES sala(id)
);

CREATE TABLE historico_estado (
   id              INT AUTO_INCREMENT PRIMARY KEY,
   tipo            ENUM('PRE_INSCRICAO','OCORRENCIA') NOT NULL,
   id_registo      INT NOT NULL,
   estado_anterior VARCHAR(30),
   estado_novo     VARCHAR(30) NOT NULL,
   id_utilizador   INT NOT NULL,
   alterado_em     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   CONSTRAINT fk_he_utilizador FOREIGN KEY (id_utilizador) REFERENCES utilizador(id)
);


SELECT * FROM ocorrencia;

DELETE FROM pre_inscricao WHERE id = 1;