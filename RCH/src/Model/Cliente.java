/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author massas
 * 
 * Classe abstrata Cliente - Base para ClienteSingular e ClienteEmpresa
 */
public abstract class Cliente extends UniversalObject {
     protected String nome;
    protected String telefone;
    protected String email;
    protected String endereco;
    protected TipoCliente tipoCliente;
    
    public enum TipoCliente {
        SINGULAR("CS"),
        EMPRESA("CE");
        
        private String prefixo;
        
        TipoCliente(String prefixo) {
            this.prefixo = prefixo;
        }
        
        public String getPrefixo() {
            return prefixo;
        }
    }
    
    public Cliente() {
        super();
    }
    
    public Cliente(String nome, String telefone, String email, String endereco, TipoCliente tipoCliente) {
        super();
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
        this.tipoCliente = tipoCliente;
    }
    
    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    
    public TipoCliente getTipoCliente() { return tipoCliente; }
    public void setTipoCliente(TipoCliente tipoCliente) { this.tipoCliente = tipoCliente; }
    
    @Override
    public String toString() {
        return "Cliente{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", tipoCliente=" + tipoCliente +
                ", ativo=" + ativo +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cliente cliente = (Cliente) obj;
        return id != null ? id.equals(cliente.id) : cliente.id == null;
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
    
}
