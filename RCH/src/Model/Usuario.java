/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Model.Interfaces.Autenticavel;

/**
 *
 * @author massas
 * 
 * Classe abstrata para usuários do sistema
 */
public abstract class Usuario extends UniversalObject implements Autenticavel{
    protected String nome;
    protected String endereco;
    protected String bilheteIdentidade;
    protected String telefone;
    protected String email;
    protected String usuario;
    protected String senha;
    protected boolean contaBloqueada;
    protected int tentativasLogin;
    protected TipoUsuario tipoUsuario;

    public String getUsername() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getSenha() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getCriadoPor() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getModificadoPor() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setUsername(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setSenha(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setCriadoPor(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setModificadoPor(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public enum TipoUsuario {
        ADMINISTRADOR("Admin"),
        GERENTE("GE"),
        VENDEDOR("VN"),
        CAIXA("CX");
        
        private String prefixo;
        
        TipoUsuario(String prefixo) {
            this.prefixo = prefixo;
        }
        
        public String getPrefixo() {
            return prefixo;
        }
    }
    
    public Usuario() {
        super();
        this.senha = "000000"; // Senha padrão
        this.contaBloqueada = false;
        this.tentativasLogin = 0;
    }
    
    public Usuario(String nome, String endereco, String bilheteIdentidade, 
                   String telefone, String email, TipoUsuario tipoUsuario) {
        this();
        this.nome = nome;
        this.endereco = endereco;
        this.bilheteIdentidade = bilheteIdentidade;
        this.telefone = telefone;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
        this.usuario = gerarUsuario();
    }
    
    // Implementação da interface Autenticavel
    @Override
    public boolean autenticar(String usuario, String senha) {
        if (contaBloqueada) {
            return false;
        }
        
        boolean autenticado = (this.usuario.equals(usuario) || this.id.equals(usuario)) 
                             && this.senha.equals(senha);
        
        if (autenticado) {
            tentativasLogin = 0;
        } else {
            tentativasLogin++;
            if (tentativasLogin >= 3) {
                bloquearConta();
            }
        }
        
        return autenticado;
    }
    
    @Override
    public void alterarSenha(String novaSenha) {
        if (novaSenha != null && novaSenha.length() >= 6) {
            this.senha = novaSenha;
            this.dataModificacao = java.time.LocalDateTime.now();
        }
    }
    
    @Override
    public boolean isContaBloqueada() {
        return contaBloqueada;
    }
    
    @Override
    public void bloquearConta() {
        this.contaBloqueada = true;
        this.dataModificacao = java.time.LocalDateTime.now();
    }
    
    @Override
    public void desbloquearConta() {
        this.contaBloqueada = false;
        this.tentativasLogin = 0;
        this.senha = "000000"; // Reset para senha padrão
        this.dataModificacao = java.time.LocalDateTime.now();
    }
    
    private String gerarUsuario() {
        if (nome != null && nome.contains(" ")) {
            String[] partes = nome.split(" ");
            return partes[0].toLowerCase() + "." + partes[partes.length - 1].toLowerCase();
        }
        return nome != null ? nome.toLowerCase() : "";
    }
    
    // Métodos abstratos que devem ser implementados pelas subclasses
    public abstract boolean temPermissao(String funcionalidade);
    
    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    
    public String getBilheteIdentidade() { return bilheteIdentidade; }
    public void setBilheteIdentidade(String bilheteIdentidade) { this.bilheteIdentidade = bilheteIdentidade; }
    
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    
    public TipoUsuario getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(TipoUsuario tipoUsuario) { this.tipoUsuario = tipoUsuario; }
    
    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", usuario='" + usuario + '\'' +
                ", tipoUsuario=" + tipoUsuario +
                ", ativo=" + ativo +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario usuario = (Usuario) obj;
        return id != null ? id.equals(usuario.id) : usuario.id == null;
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
    
}
