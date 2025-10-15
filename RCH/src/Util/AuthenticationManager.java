package Util;

import Model.Usuario;
import javax.swing.*;
import java.awt.*;

/**
 * Gerenciador de autenticação e controle de acesso
 * Verifica permissões em todos os pontos do sistema
 */
public class AuthenticationManager {
    
    private static Usuario usuarioLogado;
    
    /**
     * Define o usuário logado no sistema
     */
    public static void setUsuarioLogado(Usuario usuario) {
        usuarioLogado = usuario;
    }
    
    /**
     * Retorna o usuário atualmente logado
     */
    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
    
    /**
     * Verifica se há um usuário logado
     */
    public static boolean isLogado() {
        return usuarioLogado != null;
    }
    
    /**
     * Verifica se o usuário tem permissão para acessar uma funcionalidade
     */
    public static boolean temPermissao(String funcionalidade) {
        if (!isLogado()) {
            return false;
        }
        return usuarioLogado.temPermissao(funcionalidade);
    }
    
    /**
     * Verifica permissão e mostra mensagem de erro se não tiver acesso
     */
    public static boolean verificarPermissao(Component parent, String funcionalidade, String nomeFuncionalidade) {
        if (!temPermissao(funcionalidade)) {
            JOptionPane.showMessageDialog(parent,
                "Você não tem permissão para acessar: " + nomeFuncionalidade + "\n" +
                "Tipo de usuário: " + usuarioLogado.getTipoUsuario(),
                "Acesso Negado",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    /**
     * Desabilita componente se usuário não tiver permissão
     */
    public static void aplicarPermissao(JComponent component, String funcionalidade) {
        if (!temPermissao(funcionalidade)) {
            component.setEnabled(false);
            component.setToolTipText("Você não tem permissão para esta funcionalidade");
        }
    }
    
    /**
     * Faz logout do sistema
     */
    public static void logout() {
        usuarioLogado = null;
    }
    
    /**
     * Retorna o ID do usuário logado
     */
    public static String getUsuarioId() {
        return isLogado() ? usuarioLogado.getId() : "SISTEMA";
    }
    
    /**
     * Retorna o nome do usuário logado
     */
    public static String getUsuarioNome() {
        return isLogado() ? usuarioLogado.getNome() : "Sistema";
    }
    
    /**
     * Verifica se o usuário é administrador
     */
    public static boolean isAdministrador() {
        return isLogado() && usuarioLogado.getTipoUsuario() == Usuario.TipoUsuario.ADMINISTRADOR;
    }
    
    /**
     * Verifica se o usuário é gerente
     */
    public static boolean isGerente() {
        return isLogado() && usuarioLogado.getTipoUsuario() == Usuario.TipoUsuario.GERENTE;
    }
    
    /**
     * Verifica se o usuário é caixa
     */
    public static boolean isCaixa() {
        return isLogado() && usuarioLogado.getTipoUsuario() == Usuario.TipoUsuario.CAIXA;
    }
    
    /**
     * Verifica se o usuário é vendedor
     */
    public static boolean isVendedor() {
        return isLogado() && usuarioLogado.getTipoUsuario() == Usuario.TipoUsuario.VENDEDOR;
    }
}
