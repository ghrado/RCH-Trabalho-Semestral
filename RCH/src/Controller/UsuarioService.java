package Controller;

import Model.Interfaces.Gerenciavel;
import Model.Usuario;
import Util.AuditService;
import Util.DatabaseConnection;
import Util.GeradorID;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe UsuarioService - Gerencia usuários do sistema com persistência em banco de dados
 */
public class UsuarioService implements Gerenciavel<Usuario> {
    private GeradorID geradorID;
    private AuditService auditService;
    private Connection connection;
    
    public UsuarioService() {
        this.geradorID = GeradorID.getInstance();
        this.auditService = AuditService.getInstance();
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    @Override
    public boolean criar(Usuario usuario) {
        if (usuario == null) return false;
        
        String sql = "INSERT INTO usuarios (id, nome, username, senha, tipo, ativo, criado_por) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Gerar ID se não tiver
            if (usuario.getId() == null || usuario.getId().isEmpty()) {
                String id = geradorID.gerarID("USR");
                usuario.setId(id);
            }
            
            stmt.setString(1, usuario.getId());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getUsername());
            stmt.setString(4, usuario.getSenha());
            stmt.setString(5, usuario.getTipoUsuario().toString());
            stmt.setInt(6, usuario.isAtivo() ? 1 : 0);
            stmt.setString(7, usuario.getCriadoPor());
            
            int rows = stmt.executeUpdate();
            
            if (rows > 0) {
                auditService.registrarAcao(usuario.getCriadoPor(), "", "Criar", "Usuario", usuario.getId(), 
                    "Usuário criado: " + usuario.getNome());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao criar usuário: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    @Override
    public Usuario buscar(String id) {
        String sql = "SELECT * FROM usuarios WHERE id = ? AND ativo = 1";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return construirUsuarioFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
        }
        
        return null;
    }
    
    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE ativo = 1 ORDER BY nome";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                usuarios.add(construirUsuarioFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
        }
        
        return usuarios;
    }
    
    @Override
    public boolean atualizar(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) return false;
        
        String sql = "UPDATE usuarios SET nome = ?, username = ?, senha = ?, tipo = ?, data_modificacao = CURRENT_TIMESTAMP, modificado_por = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getUsername());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getTipoUsuario().toString());
            stmt.setString(5, usuario.getModificadoPor());
            stmt.setString(6, usuario.getId());
            
            int rows = stmt.executeUpdate();
            
            if (rows > 0) {
                auditService.registrarAcao(usuario.getModificadoPor(), "", "Atualizar", "Usuario", usuario.getId(), 
                    "Usuário atualizado: " + usuario.getNome());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean excluir(String id) {
        String sql = "UPDATE usuarios SET ativo = 0, data_modificacao = CURRENT_TIMESTAMP WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            int rows = stmt.executeUpdate();
            
            if (rows > 0) {
                geradorID.adicionarIdParaReutilizacao("USR", id);
                auditService.registrarAcao("", "", "Excluir", "Usuario", id, "Usuário excluído");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao excluir usuário: " + e.getMessage());
        }
        
        return false;
    }
    
    @Override
    public boolean reintegrar(String id) {
        String sql = "UPDATE usuarios SET ativo = 1, data_modificacao = CURRENT_TIMESTAMP WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            int rows = stmt.executeUpdate();
            
            if (rows > 0) {
                geradorID.removerIdDeReutilizacao("USR", id);
                auditService.registrarAcao("", "", "Reintegrar", "Usuario", id, "Usuário reintegrado");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao reintegrar usuário: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Autentica um usuário
     */
    public Usuario autenticar(String username, String senha) {
        String sql = "SELECT * FROM usuarios WHERE username = ? AND senha = ? AND ativo = 1";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Usuario usuario = construirUsuarioFromResultSet(rs);
                auditService.registrarAcao(usuario.getId(), "", "Login", "Sistema", "", 
                    "Usuário autenticado: " + usuario.getNome());
                return usuario;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao autenticar usuário: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Busca usuários por nome
     */
    public List<Usuario> buscarPorNome(String nome) {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE nome LIKE ? AND ativo = 1";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                usuarios.add(construirUsuarioFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuários por nome: " + e.getMessage());
        }
        
        return usuarios;
    }
    
    /**
     * Busca usuários por tipo
     */
    public List<Usuario> buscarPorTipo(String tipo) {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE tipo = ? AND ativo = 1";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tipo);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                usuarios.add(construirUsuarioFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuários por tipo: " + e.getMessage());
        }
        
        return usuarios;
    }
    
    /**
     * Altera senha de um usuário
     */
    public boolean alterarSenha(String id, String senhaAntiga, String senhaNova) {
        String sql = "UPDATE usuarios SET senha = ?, data_modificacao = CURRENT_TIMESTAMP WHERE id = ? AND senha = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, senhaNova);
            stmt.setString(2, id);
            stmt.setString(3, senhaAntiga);
            
            int rows = stmt.executeUpdate();
            
            if (rows > 0) {
                auditService.registrarAcao(id, "", "Alterar Senha", "Usuario", id, "Senha alterada");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao alterar senha: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Obtém usuários excluídos
     */
    public List<Usuario> getUsuariosExcluidos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE ativo = 0";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                usuarios.add(construirUsuarioFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários excluídos: " + e.getMessage());
        }
        
        return usuarios;
    }
    
    /**
     * Constrói objeto Usuario a partir do ResultSet
     */
    private Usuario construirUsuarioFromResultSet(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario() {
            @Override
            public boolean temPermissao(String funcionalidade) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        };
        usuario.setId(rs.getString("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setUsername(rs.getString("username"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setTipoUsuario(Usuario.TipoUsuario.valueOf(rs.getString("tipo")));
        usuario.setAtivo(rs.getInt("ativo") == 1);
        usuario.setCriadoPor(rs.getString("criado_por"));
        usuario.setModificadoPor(rs.getString("modificado_por"));
        return usuario;
    }
    
    /**
     * Registra ação de usuário
     */
    public void registrarAcaoUsuario(String usuarioId, String acao, String detalhes) {
        Usuario usuario = buscar(usuarioId);
        if (usuario != null) {
            auditService.registrarAcao(usuarioId, "", acao, "Usuario", usuarioId, detalhes);
        }
    }
}
