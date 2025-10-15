/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Interfaces;

import java.util.List;

/**
 *
 * @author massas
 * 
 * Interface para operações de gerenciamento (CRUD)
 */
public interface Gerenciavel <T> {
    boolean criar(T objeto);
    T buscar(String id);
    List<T> listarTodos();
    boolean atualizar(T objeto);
    boolean excluir(String id);
    boolean reintegrar(String id);
}
