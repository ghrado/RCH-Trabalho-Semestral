/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Telas.dialogs;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Map;
import Controller.RelatoriosService;
/**
 *
 * @author massas
 */
public class RelatoriosDialog extends JDialog {
     private RelatoriosService relatoriosService;
    private JTextArea txtRelatorio;
    
    public RelatoriosDialog(Frame parent, RelatoriosService relatoriosService) {
        super(parent, "Relatórios", true);
        this.relatoriosService = relatoriosService;
        
        initComponents();
        
        setSize(800, 600);
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Painel superior - Opções de Relatório
        JPanel painelOpcoes = criarPainelOpcoes();
        add(painelOpcoes, BorderLayout.NORTH);
        
        // Painel central - Área de Texto
        JPanel painelRelatorio = criarPainelRelatorio();
        add(painelRelatorio, BorderLayout.CENTER);
        
        // Painel inferior - Botões
        JPanel painelBotoes = criarPainelBotoes();
        add(painelBotoes, BorderLayout.SOUTH);
    }
    
    private JPanel criarPainelOpcoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painel.setBorder(BorderFactory.createTitledBorder("Tipo de Relatório"));
        
        JButton btnDiario = new JButton("Relatório Diário");
        btnDiario.addActionListener(e -> gerarRelatorioDiario());
        painel.add(btnDiario);
        
        JButton btnSemanal = new JButton("Relatório Semanal");
        btnSemanal.addActionListener(e -> gerarRelatorioSemanal());
        painel.add(btnSemanal);
        
        JButton btnMensal = new JButton("Relatório Mensal");
        btnMensal.addActionListener(e -> gerarRelatorioMensal());
        painel.add(btnMensal);
        
        JButton btnConsolidado = new JButton("Relatório Consolidado");
        btnConsolidado.addActionListener(e -> gerarRelatorioConsolidado());
        painel.add(btnConsolidado);
        
        return painel;
    }
    
    private JPanel criarPainelRelatorio() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Resultado"));
        
        txtRelatorio = new JTextArea();
        txtRelatorio.setEditable(false);
        txtRelatorio.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(txtRelatorio);
        painel.add(scrollPane, BorderLayout.CENTER);
        
        return painel;
    }
    
    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton btnImprimir = new JButton("Imprimir");
        btnImprimir.addActionListener(e -> imprimirRelatorio());
        painel.add(btnImprimir);
        
        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        painel.add(btnFechar);
        
        return painel;
    }
    
    private void gerarRelatorioDiario() {
        Map<String, Object> relatorio = (Map<String, Object>) relatoriosService.gerarRelatorioDiario(LocalDate.now());
        exibirRelatorio("RELATÓRIO DIÁRIO", relatorio);
    }
    
    private void gerarRelatorioSemanal() {
        Map<String, Object> relatorio = relatoriosService.gerarRelatorioSemanal(LocalDate.now());
        exibirRelatorio("RELATÓRIO SEMANAL", relatorio);
    }
    
    private void gerarRelatorioMensal() {
        Map<String, Object> relatorio = relatoriosService.gerarRelatorioMensal(LocalDate.now());
        exibirRelatorio("RELATÓRIO MENSAL", relatorio);
    }
    
    private void gerarRelatorioConsolidado() {
        Map<String, Object> relatorio = relatoriosService.gerarRelatorioConsolidado();
        exibirRelatorio("RELATÓRIO CONSOLIDADO", relatorio);
    }
    
    private void exibirRelatorio(String titulo, Map<String, Object> dados) {
        StringBuilder sb = new StringBuilder();
        sb.append("=".repeat(60)).append("\n");
        sb.append(titulo).append("\n");
        sb.append("=".repeat(60)).append("\n\n");
        
        for (Map.Entry<String, Object> entry : dados.entrySet()) {
            sb.append(String.format("%-30s: %s\n", entry.getKey(), entry.getValue()));
        }
        
        sb.append("\n").append("=".repeat(60)).append("\n");
        
        txtRelatorio.setText(sb.toString());
    }
    
    private void imprimirRelatorio() {
        JOptionPane.showMessageDialog(this, "Funcionalidade de impressão será implementada!", 
                                    "Informação", JOptionPane.INFORMATION_MESSAGE);
    }
}
