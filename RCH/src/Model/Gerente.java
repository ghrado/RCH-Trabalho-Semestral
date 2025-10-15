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
 * Classe Gerente - Herda de Usuario
 * Tem acesso a: fecho de caixa, vendas, stock, caixa e relatórios
 */
public class Gerente extends Usuario{
    private static final List<String> PERMISSOES = Arrays.asList(
        "FECHO_CAIXA", "VENDAS", "STOCK", "CAIXA", "RELATORIOS"
    );
    
    public Gerente() {
        super();
        this.tipoUsuario = TipoUsuario.GERENTE;
    }
    
    public Gerente(String nome, String endereco, String bilheteIdentidade, 
                   String telefone, String email) {
        super(nome, endereco, bilheteIdentidade, telefone, email, TipoUsuario.GERENTE);
    }
    
    @Override
    public boolean temPermissao(String funcionalidade) {
        return PERMISSOES.contains(funcionalidade.toUpperCase());
    }
    
    /**
     * Método específico do gerente para aprovar operações especiais
     */
    public boolean aprovarOperacao(String tipoOperacao) {
        // Lógica de aprovação específica do gerente
        return true;
    }
    
}
