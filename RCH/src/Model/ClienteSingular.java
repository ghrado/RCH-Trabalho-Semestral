/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author massas
 * Classe ClienteSingular - Herda de Cliente
 * Para clientes individuais
 */
public class ClienteSingular extends Cliente{
    private String bilheteIdentidade;
    
    public ClienteSingular() {
        super();
        this.tipoCliente = TipoCliente.SINGULAR;
    }
    
    public ClienteSingular(String nome, String telefone, String email, String endereco, String bilheteIdentidade) {
        super(nome, telefone, email, endereco, TipoCliente.SINGULAR);
        this.bilheteIdentidade = bilheteIdentidade;
    }
    
    // Getters e Setters
    public String getBilheteIdentidade() {
        return bilheteIdentidade;
    }
    
    public void setBilheteIdentidade(String bilheteIdentidade) {
        this.bilheteIdentidade = bilheteIdentidade;
    }
    
    @Override
    public String toString() {
        return "ClienteSingular{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", bilheteIdentidade='" + bilheteIdentidade + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", ativo=" + ativo +
                '}';
    }
    
}
