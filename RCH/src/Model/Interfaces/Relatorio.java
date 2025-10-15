/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Interfaces;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author massas
 * 
 * Interface para geração de relatórios
 */
public interface Relatorio <T> {
    List<T> gerarRelatorioDiario(LocalDate data);
    List<T> gerarRelatorioSemanal(LocalDate dataInicio, LocalDate dataFim);
    List<T> gerarRelatorioMensal(int mes, int ano);
    boolean exportarParaPDF(List<T> dados, String nomeArquivo);
    void exibirGrafico(List<T> dados);
    
}
