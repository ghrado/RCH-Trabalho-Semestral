package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe responsável pela conexão com o banco de dados SQLite
 * Implementa o padrão Singleton para garantir uma única instância
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private static final String DB_URL = "jdbc:sqlite:rch_database.db";
    
    private DatabaseConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(DB_URL);
            System.out.println("Conexão com banco de dados estabelecida com sucesso!");
            initializeDatabase();
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
    
    /**
     * Retorna a instância única da conexão (Singleton)
     */
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }
    
    /**
     * Retorna a conexão ativa com o banco de dados
     */
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter conexão: " + e.getMessage());
        }
        return connection;
    }
    
    /**
     * Inicializa as tabelas do banco de dados se não existirem
     */
    private void initializeDatabase() {
        try (Statement stmt = connection.createStatement()) {
            
            // Tabela de Usuários
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS usuarios (
                    id TEXT PRIMARY KEY,
                    nome TEXT NOT NULL,
                    username TEXT UNIQUE NOT NULL,
                    senha TEXT NOT NULL,
                    tipo TEXT NOT NULL CHECK(tipo IN ('Gerente', 'Caixa', 'Vendedor')),
                    ativo INTEGER DEFAULT 1,
                    data_criacao TEXT DEFAULT CURRENT_TIMESTAMP,
                    criado_por TEXT,
                    data_modificacao TEXT,
                    modificado_por TEXT
                )
            """);
            
            // Tabela de Clientes
            stmt.execute("""
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
                )
            """);
            
            // Tabela de Produtos
            stmt.execute("""
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
                )
            """);
            
            // Tabela de Ordens
            stmt.execute("""
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
                )
            """);
            
            // Tabela de Itens de Ordem
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS itens_ordem (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    ordem_id TEXT NOT NULL,
                    produto_id TEXT NOT NULL,
                    quantidade INTEGER NOT NULL,
                    preco_unitario REAL NOT NULL,
                    subtotal REAL NOT NULL,
                    FOREIGN KEY (ordem_id) REFERENCES ordens(id),
                    FOREIGN KEY (produto_id) REFERENCES produtos(id)
                )
            """);
            
            // Tabela de Faturas
            stmt.execute("""
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
                )
            """);
            
            // Tabela de Pagamentos
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS pagamentos (
                    id TEXT PRIMARY KEY,
                    fatura_id TEXT NOT NULL,
                    tipo_pagamento TEXT NOT NULL CHECK(tipo_pagamento IN ('Dinheiro', 'Multi-Redes', 'E-Mola', 'M-pesa', 'Cheque')),
                    valor_pago REAL NOT NULL,
                    troco REAL DEFAULT 0,
                    data_pagamento TEXT DEFAULT CURRENT_TIMESTAMP,
                    processado_por TEXT,
                    FOREIGN KEY (fatura_id) REFERENCES faturas(id)
                )
            """);
            
            // Tabela de Fecho de Caixa
            stmt.execute("""
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
                )
            """);
            
            // Tabela de Auditoria
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS audit_log (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    usuario TEXT NOT NULL,
                    acao TEXT NOT NULL,
                    modulo TEXT NOT NULL,
                    detalhes TEXT,
                    data_hora TEXT DEFAULT CURRENT_TIMESTAMP
                )
            """);
            
            // Criar usuário administrador padrão se não existir
            stmt.execute("""
                INSERT OR IGNORE INTO usuarios (id, nome, username, senha, tipo, criado_por)
                VALUES ('USR001', 'Administrador', 'admin', 'admin123', 'Gerente', 'SISTEMA')
            """);
            
            System.out.println("Estrutura do banco de dados inicializada com sucesso!");
            
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Fecha a conexão com o banco de dados
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Conexão com banco de dados fechada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}
