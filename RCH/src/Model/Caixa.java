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
 * Classe Caixa - Herda de Usuario
 * Tem acesso apenas a: caixa
 */
public class Caixa extends Usuario {
    private static final List<String> PERMISSOES = Arrays.asList("CAIXA");
    
    public Caixa() {
        super();
        this.tipoUsuario = TipoUsuario.CAIXA;
    }
    
    public Caixa(String nome, String endereco, String bilheteIdentidade, 
                 String telefone, String email) {
        super(nome, endereco, bilheteIdentidade, telefone, email, TipoUsuario.CAIXA);
    }
    
    @Override
    public boolean temPermissao(String funcionalidade) {
        return PERMISSOES.contains(funcionalidade.toUpperCase());
    }
    
    /**
     * Método específico do caixa para processar pagamentos
     */
    public boolean processarPagamento(String ordemId, String tipoPagamento, double valor) {
        // Lógica específica de processamento de pagamento
        System.out.println("Processando pagamento da ordem " + ordemId + 
                          " via " + tipoPagamento + " no valor de " + valor);
        return true;
    }
}
