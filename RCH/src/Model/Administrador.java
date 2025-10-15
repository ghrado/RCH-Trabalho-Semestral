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
 */
public class Administrador extends Usuario{

   private static final List<String> PERMISSOES = Arrays.asList(
        "FECHO_CAIXA", "CONTROLE_GESTOR", "VENDAS", "STOCK", "CAIXA", "RELATORIOS"
    );
    
    public Administrador() {
        super();
        this.tipoUsuario = TipoUsuario.ADMINISTRADOR;
        this.id = "Admin";
        this.usuario = "admin";
    }
    
    public Administrador(String nome, String endereco, String bilheteIdentidade, 
                        String telefone, String email) {
        super(nome, endereco, bilheteIdentidade, telefone, email, TipoUsuario.ADMINISTRADOR);
        this.id = "Admin";
        this.usuario = "admin";
    }
    
    @Override
    public boolean temPermissao(String funcionalidade) {
        return PERMISSOES.contains(funcionalidade.toUpperCase());
    }
    
    /**
     * Método específico do administrador para resetar senhas de usuários
     */
    public void resetarSenhaUsuario(Usuario usuario) {
        usuario.desbloquearConta();
    }
    
    /**
     * Método para notificar sobre contas bloqueadas
     */
    public void receberNotificacaoContaBloqueada(String usuarioId) {
        System.out.println("NOTIFICAÇÃO: Conta do usuário " + usuarioId + " foi bloqueada.");
    }
}
