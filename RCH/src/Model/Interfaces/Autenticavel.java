/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Interfaces;

/**
 *
 * @author massas
 * 
 * Interface para objetos que podem ser autenticados
 */
public interface Autenticavel {
    boolean autenticar(String usuario, String senha);
    void alterarSenha(String novaSenha);
    boolean isContaBloqueada();
    void bloquearConta();
    void desbloquearConta();
    
}
