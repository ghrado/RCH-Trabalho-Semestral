-- Script para criar o banco de dados MySQL
-- Sistema de Gestão de Vendas

CREATE DATABASE IF NOT EXISTS gestao_vendas
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE gestao_vendas;

-- Tabela de Usuários (Administrador, Gerente, Vendedor, Caixa)
CREATE TABLE IF NOT EXISTS usuarios (
    id VARCHAR(20) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    endereco VARCHAR(200),
    bilhete_identidade VARCHAR(50) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(100),
    usuario VARCHAR(50) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    tipo_usuario ENUM('ADMINISTRADOR', 'GERENTE', 'VENDEDOR', 'CAIXA') NOT NULL,
    conta_bloqueada BOOLEAN DEFAULT FALSE,
    primeira_vez BOOLEAN DEFAULT TRUE,
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_usuario (usuario),
    INDEX idx_tipo (tipo_usuario),
    INDEX idx_ativo (ativo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de Clientes (Singular e Empresa)
CREATE TABLE IF NOT EXISTS clientes (
    id VARCHAR(20) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(100),
    endereco VARCHAR(200),
    tipo_cliente ENUM('SINGULAR', 'EMPRESA') NOT NULL,
    bilhete_identidade VARCHAR(50),
    nuit VARCHAR(50),
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_tipo_cliente (tipo_cliente),
    INDEX idx_nome (nome),
    INDEX idx_ativo (ativo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de Produtos
CREATE TABLE IF NOT EXISTS produtos (
    id VARCHAR(20) PRIMARY KEY,
    codigo VARCHAR(50) UNIQUE NOT NULL,
    descricao VARCHAR(200) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    quantidade_stock INT NOT NULL DEFAULT 0,
    stock_minimo INT DEFAULT 10,
    stock_maximo INT DEFAULT 1000,
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_codigo (codigo),
    INDEX idx_descricao (descricao),
    INDEX idx_ativo (ativo),
    INDEX idx_stock (quantidade_stock)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de Ordens
CREATE TABLE IF NOT EXISTS ordens (
    id VARCHAR(20) PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    cliente_id VARCHAR(20) NOT NULL,
    cliente_nome VARCHAR(100) NOT NULL,
    vendedor_id VARCHAR(20) NOT NULL,
    vendedor_nome VARCHAR(100) NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL DEFAULT 0.00,
    status ENUM('PENDENTE', 'PROCESSANDO', 'PAGO', 'CANCELADO') DEFAULT 'PENDENTE',
    tipo ENUM('VENDA', 'COTACAO') DEFAULT 'VENDA',
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    FOREIGN KEY (vendedor_id) REFERENCES usuarios(id),
    INDEX idx_codigo (codigo),
    INDEX idx_cliente (cliente_id),
    INDEX idx_vendedor (vendedor_id),
    INDEX idx_status (status),
    INDEX idx_data (data_criacao)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de Itens da Ordem
CREATE TABLE IF NOT EXISTS itens_ordem (
    id VARCHAR(20) PRIMARY KEY,
    ordem_id VARCHAR(20) NOT NULL,
    produto_id VARCHAR(20) NOT NULL,
    produto_codigo VARCHAR(50) NOT NULL,
    produto_descricao VARCHAR(200) NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (ordem_id) REFERENCES ordens(id) ON DELETE CASCADE,
    FOREIGN KEY (produto_id) REFERENCES produtos(id),
    INDEX idx_ordem (ordem_id),
    INDEX idx_produto (produto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de Faturas
CREATE TABLE IF NOT EXISTS faturas (
    id VARCHAR(20) PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    ordem_id VARCHAR(20) NOT NULL,
    vendedor_id VARCHAR(20) NOT NULL,
    vendedor_nome VARCHAR(100) NOT NULL,
    caixa_id VARCHAR(20) NOT NULL,
    caixa_nome VARCHAR(100) NOT NULL,
    cliente_id VARCHAR(20) NOT NULL,
    cliente_nome VARCHAR(100) NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL,
    tipo_pagamento ENUM('MULTI_REDES', 'EMOLA', 'MPESA', 'DINHEIRO', 'CHEQUE', 'NOTA_CREDITO') NOT NULL,
    data_processamento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ativo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (ordem_id) REFERENCES ordens(id),
    FOREIGN KEY (vendedor_id) REFERENCES usuarios(id),
    FOREIGN KEY (caixa_id) REFERENCES usuarios(id),
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    INDEX idx_codigo (codigo),
    INDEX idx_ordem (ordem_id),
    INDEX idx_vendedor (vendedor_id),
    INDEX idx_caixa (caixa_id),
    INDEX idx_cliente (cliente_id),
    INDEX idx_tipo_pagamento (tipo_pagamento),
    INDEX idx_data (data_processamento)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de Fecho de Caixa
CREATE TABLE IF NOT EXISTS fecho_caixa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    caixa_id VARCHAR(20) NOT NULL,
    caixa_nome VARCHAR(100) NOT NULL,
    data_fecho DATE NOT NULL,
    hora_fecho TIME NOT NULL,
    total_vendas DECIMAL(10, 2) NOT NULL,
    total_multi_redes DECIMAL(10, 2) DEFAULT 0.00,
    total_emola DECIMAL(10, 2) DEFAULT 0.00,
    total_mpesa DECIMAL(10, 2) DEFAULT 0.00,
    total_dinheiro DECIMAL(10, 2) DEFAULT 0.00,
    total_cheque DECIMAL(10, 2) DEFAULT 0.00,
    total_nota_credito DECIMAL(10, 2) DEFAULT 0.00,
    quantidade_faturas INT NOT NULL,
    observacoes TEXT,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (caixa_id) REFERENCES usuarios(id),
    INDEX idx_caixa (caixa_id),
    INDEX idx_data_fecho (data_fecho)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de Dados Apagados (para reintegração)
CREATE TABLE IF NOT EXISTS dados_apagados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo_objeto ENUM('CLIENTE', 'PRODUTO', 'ORDEM', 'USUARIO') NOT NULL,
    objeto_id VARCHAR(20) NOT NULL,
    dados_json TEXT NOT NULL,
    usuario_exclusao VARCHAR(20) NOT NULL,
    data_exclusao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_tipo (tipo_objeto),
    INDEX idx_objeto_id (objeto_id),
    INDEX idx_data (data_exclusao)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de Log de Operações
CREATE TABLE IF NOT EXISTS log_operacoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo_operacao VARCHAR(50) NOT NULL,
    descricao TEXT NOT NULL,
    usuario_id VARCHAR(20) NOT NULL,
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_usuario (usuario_id),
    INDEX idx_tipo (tipo_operacao),
    INDEX idx_data (data_hora)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
