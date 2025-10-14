-- Script SQL para criação do banco de dados RCH
-- Sistema de Gestão Comercial

-- Tabela de Usuários
CREATE TABLE IF NOT EXISTS usuarios (
    id TEXT PRIMARY KEY,
    nome TEXT NOT NULL,
    username TEXT UNIQUE NOT NULL,
    senha TEXT NOT NULL,
    tipo TEXT NOT NULL CHECK(tipo IN ('Administrador', 'Gerente', 'Caixa', 'Vendedor')),
    endereco TEXT,
    bilhete_identidade TEXT,
    telefone TEXT,
    email TEXT,
    conta_bloqueada INTEGER DEFAULT 0,
    tentativas_login INTEGER DEFAULT 0,
    ativo INTEGER DEFAULT 1,
    data_criacao TEXT DEFAULT CURRENT_TIMESTAMP,
    criado_por TEXT,
    data_modificacao TEXT,
    modificado_por TEXT
);

-- Tabela de Clientes
CREATE TABLE IF NOT EXISTS clientes (
    id TEXT PRIMARY KEY,
    tipo TEXT NOT NULL CHECK(tipo IN ('Singular', 'Empresa')),
    nome TEXT NOT NULL,
    telefone TEXT,
    email TEXT,
    endereco TEXT,
    nuit TEXT,
    bi TEXT,
    ativo INTEGER DEFAULT 1,
    data_criacao TEXT DEFAULT CURRENT_TIMESTAMP,
    criado_por TEXT,
    data_modificacao TEXT,
    modificado_por TEXT
);

-- Tabela de Produtos
CREATE TABLE IF NOT EXISTS produtos (
    id TEXT PRIMARY KEY,
    nome TEXT NOT NULL,
    descricao TEXT,
    categoria TEXT NOT NULL,
    preco REAL NOT NULL,
    quantidade_disponivel INTEGER DEFAULT 0,
    quantidade_minima INTEGER DEFAULT 10,
    unidade TEXT DEFAULT 'UN',
    ativo INTEGER DEFAULT 1,
    data_criacao TEXT DEFAULT CURRENT_TIMESTAMP,
    criado_por TEXT,
    data_modificacao TEXT,
    modificado_por TEXT
);

-- Tabela de Ordens (Ordens e Cotações)
CREATE TABLE IF NOT EXISTS ordens (
    id TEXT PRIMARY KEY,
    cliente_id TEXT NOT NULL,
    tipo TEXT NOT NULL CHECK(tipo IN ('Ordem', 'Cotacao')),
    status TEXT NOT NULL DEFAULT 'Pendente' CHECK(status IN ('Pendente', 'Processada', 'Cancelada')),
    subtotal REAL NOT NULL,
    iva REAL DEFAULT 0,
    total REAL NOT NULL,
    observacoes TEXT,
    data_criacao TEXT DEFAULT CURRENT_TIMESTAMP,
    criado_por TEXT,
    data_processamento TEXT,
    processado_por TEXT,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

-- Tabela de Itens de Ordem
CREATE TABLE IF NOT EXISTS itens_ordem (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    ordem_id TEXT NOT NULL,
    produto_id TEXT NOT NULL,
    quantidade INTEGER NOT NULL,
    preco_unitario REAL NOT NULL,
    subtotal REAL NOT NULL,
    FOREIGN KEY (ordem_id) REFERENCES ordens(id),
    FOREIGN KEY (produto_id) REFERENCES produtos(id)
);

-- Tabela de Faturas
CREATE TABLE IF NOT EXISTS faturas (
    id TEXT PRIMARY KEY,
    ordem_id TEXT NOT NULL,
    numero_fatura TEXT UNIQUE NOT NULL,
    subtotal REAL NOT NULL,
    iva REAL NOT NULL,
    total REAL NOT NULL,
    status TEXT DEFAULT 'Pendente' CHECK(status IN ('Pendente', 'Paga', 'Cancelada')),
    data_emissao TEXT DEFAULT CURRENT_TIMESTAMP,
    emitido_por TEXT,
    FOREIGN KEY (ordem_id) REFERENCES ordens(id)
);

-- Tabela de Pagamentos
CREATE TABLE IF NOT EXISTS pagamentos (
    id TEXT PRIMARY KEY,
    fatura_id TEXT NOT NULL,
    tipo_pagamento TEXT NOT NULL CHECK(tipo_pagamento IN ('Dinheiro', 'Multi-Redes', 'E-Mola', 'M-pesa', 'Cheque')),
    valor_pago REAL NOT NULL,
    troco REAL DEFAULT 0,
    data_pagamento TEXT DEFAULT CURRENT_TIMESTAMP,
    processado_por TEXT,
    FOREIGN KEY (fatura_id) REFERENCES faturas(id)
);

-- Tabela de Fecho de Caixa
CREATE TABLE IF NOT EXISTS fecho_caixa (
    id TEXT PRIMARY KEY,
    data_fecho TEXT DEFAULT CURRENT_TIMESTAMP,
    total_dinheiro REAL DEFAULT 0,
    total_multiredes REAL DEFAULT 0,
    total_emola REAL DEFAULT 0,
    total_mpesa REAL DEFAULT 0,
    total_cheque REAL DEFAULT 0,
    total_geral REAL NOT NULL,
    quantidade_faturas INTEGER DEFAULT 0,
    fechado_por TEXT,
    observacoes TEXT
);

-- Tabela de Auditoria
CREATE TABLE IF NOT EXISTS audit_log (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    usuario TEXT NOT NULL,
    acao TEXT NOT NULL,
    modulo TEXT NOT NULL,
    detalhes TEXT,
    data_hora TEXT DEFAULT CURRENT_TIMESTAMP
);

-- Inserir usuários padrão do sistema
INSERT OR IGNORE INTO usuarios (id, nome, username, senha, tipo, endereco, bilhete_identidade, telefone, email, criado_por)
VALUES 
    ('Admin', 'Administrador', 'admin', '000000', 'Administrador', 'Sistema Central', '000000000', '840000000', 'admin@rch.co.mz', 'SISTEMA'),
    ('GE001', 'Calisa Muianga', 'calisa.muianga', '000000', 'Gerente', 'Av. Julius Nyerere, 1234', '110045678901A', '845551234', 'calisa@rch.co.mz', 'SISTEMA'),
    ('CX001', 'Rogeria Sitoe', 'rogeria.sitoe', '000000', 'Caixa', 'Bairro Central, Rua 25', '110056789012B', '845552345', 'rogeria@rch.co.mz', 'SISTEMA'),
    ('VN001', 'Henrique Macie', 'henrique.macie', '000000', 'Vendedor', 'Matola, Av. da Independencia', '110067890123C', '845553456', 'henrique@rch.co.mz', 'SISTEMA');

-- Inserir categorias de produtos padrão (exemplos)
INSERT OR IGNORE INTO produtos (id, nome, descricao, categoria, preco, quantidade_disponivel, criado_por)
VALUES 
    ('VF001', 'Varão 8mm', 'Varão de ferro 8mm', 'varao', 150.00, 100, 'SISTEMA'),
    ('CAN001', 'Tubo PVC 25mm', 'Tubo PVC para canalização 25mm', 'canalizacao', 85.00, 50, 'SISTEMA'),
    ('ELE001', 'Cabo Elétrico 2.5mm', 'Cabo elétrico 2.5mm', 'eletrico', 120.00, 200, 'SISTEMA');
