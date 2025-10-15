-- Script para inserir dados iniciais
-- Sistema de Gestão de Vendas

USE gestao_vendas;

-- Inserir Administrador padrão
-- Usuário: Admin, Senha: 000000
INSERT INTO usuarios (id, nome, endereco, bilhete_identidade, telefone, email, usuario, senha, tipo_usuario, conta_bloqueada, primeira_vez, ativo)
VALUES ('ADMIN', 'Administrador do Sistema', 'Sede Principal', '000000000A', '+258 84 000 0000', 'admin@gestaovendas.com', 'Admin', '000000', 'ADMINISTRADOR', FALSE, TRUE, TRUE);

-- Inserir alguns produtos de exemplo
INSERT INTO produtos (id, codigo, descricao, preco, quantidade_stock, stock_minimo, stock_maximo, ativo)
VALUES 
('PROD0001', 'P001', 'Arroz Branco 5kg', 250.00, 100, 20, 500, TRUE),
('PROD0002', 'P002', 'Óleo de Cozinha 1L', 150.00, 80, 15, 300, TRUE),
('PROD0003', 'P003', 'Açúcar 1kg', 80.00, 150, 30, 600, TRUE),
('PROD0004', 'P004', 'Farinha de Trigo 1kg', 90.00, 120, 25, 500, TRUE),
('PROD0005', 'P005', 'Feijão Preto 1kg', 120.00, 90, 20, 400, TRUE);

-- Inserir alguns clientes de exemplo
INSERT INTO clientes (id, nome, telefone, email, endereco, tipo_cliente, bilhete_identidade, nuit, ativo)
VALUES 
('CS0001', 'João Manuel Silva', '+258 84 123 4567', 'joao.silva@email.com', 'Av. Julius Nyerere, 123', 'SINGULAR', '110012345678A', NULL, TRUE),
('CS0002', 'Maria Santos Costa', '+258 85 234 5678', 'maria.costa@email.com', 'Rua da Resistência, 456', 'SINGULAR', '110023456789B', NULL, TRUE),
('CE0001', 'Empresa ABC Lda', '+258 21 300 000', 'contato@empresaabc.co.mz', 'Av. 25 de Setembro, 789', 'EMPRESA', NULL, '400123456', TRUE),
('CE0002', 'Comercial XYZ Sarl', '+258 21 400 000', 'info@comercialxyz.co.mz', 'Av. Samora Machel, 321', 'EMPRESA', NULL, '400234567', TRUE);

-- Inserir um gerente de exemplo
-- Usuário: JSilva, Senha: 000000
INSERT INTO usuarios (id, nome, endereco, bilhete_identidade, telefone, email, usuario, senha, tipo_usuario, conta_bloqueada, primeira_vez, ativo)
VALUES ('GE0001', 'José Silva', 'Maputo', '110034567890C', '+258 84 345 6789', 'jose.silva@gestaovendas.com', 'JSilva', '000000', 'GERENTE', FALSE, TRUE, TRUE);

-- Inserir um vendedor de exemplo
-- Usuário: ACosta, Senha: 000000
INSERT INTO usuarios (id, nome, endereco, bilhete_identidade, telefone, email, usuario, senha, tipo_usuario, conta_bloqueada, primeira_vez, ativo)
VALUES ('VN0001', 'Ana Costa', 'Matola', '110045678901D', '+258 85 456 7890', 'ana.costa@gestaovendas.com', 'ACosta', '000000', 'VENDEDOR', FALSE, TRUE, TRUE);

-- Inserir um caixa de exemplo
-- Usuário: PMachel, Senha: 000000
INSERT INTO usuarios (id, nome, endereco, bilhete_identidade, telefone, email, usuario, senha, tipo_usuario, conta_bloqueada, primeira_vez, ativo)
VALUES ('CX0001', 'Pedro Machel', 'Maputo', '110056789012E', '+258 86 567 8901', 'pedro.machel@gestaovendas.com', 'PMachel', '000000', 'CAIXA', FALSE, TRUE, TRUE);

-- Log de criação inicial
INSERT INTO log_operacoes (tipo_operacao, descricao, usuario_id)
VALUES ('INICIALIZACAO', 'Sistema inicializado com dados padrão', 'ADMIN');
