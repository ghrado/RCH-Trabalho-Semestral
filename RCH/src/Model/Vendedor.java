/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author massas
 * 
 * Classe Vendedor - Herda de Usuario
 * Tem acesso apenas a: vendas
 */
public class Vendedor extends Usuario {
    private static final List<String> PERMISSOES = Arrays.asList("VENDAS");
    
    public Vendedor() {
        super();
        this.tipoUsuario = TipoUsuario.VENDEDOR;
    }
    
    public Vendedor(String nome, String endereco, String bilheteIdentidade, 
                    String telefone, String email) {
        super(nome, endereco, bilheteIdentidade, telefone, email, TipoUsuario.VENDEDOR);
    }
    
    @Override
    public boolean temPermissao(String funcionalidade) {
        return PERMISSOES.contains(funcionalidade.toUpperCase());
    }
    
    /**
     * Método específico do vendedor para receber alertas de stock
     */
    public void receberAlertaStock(String produtoId, int quantidadeAtual, int minimoStock) {
        if (quantidadeAtual <= minimoStock) {
            System.out.println("ALERTA: Produto " + produtoId + " com stock baixo! " +
                             "Quantidade atual: " + quantidadeAtual + 
                             ", Mínimo: " + minimoStock);
        }
    }
    
}
