/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Cliente;
import Model.Fatura;
import Model.Interfaces.Relatorio;
import Model.Usuario;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author anicamassas
 */
public class RelatoriosService implements Relatorio<Object> {
    private VendasService vendasService;
    private StockService stockService;
    private CaixaService caixaService;
    private ControleGestorService controleGestorService;
    
    public RelatoriosService(VendasService vendasService, StockService stockService, 
                           CaixaService caixaService, ControleGestorService controleGestorService) {
        this.vendasService = vendasService;
        this.stockService = stockService;
        this.caixaService = caixaService;
        this.controleGestorService = controleGestorService;
    }
    
    // Implementação da interface Relatorio
    @Override
    public List<Object> gerarRelatorioDiario(LocalDate data) {
        List<Object> relatorio = new ArrayList<>();
        relatorio.add("=== RELATÓRIO DIÁRIO - " + data + " ===");
        
        // Vendas do dia
        double totalVendas = caixaService.getTotalVendasPorData(data);
        relatorio.add("Total de Vendas: " + totalVendas);
        
        // Pagamentos por tipo
        Map<Fatura.TipoPagamento, Double> pagamentos = caixaService.getLancamentosPorData(data);
        relatorio.add("Pagamentos por Tipo:");
        pagamentos.forEach((tipo, valor) -> relatorio.add("  " + tipo.getDescricao() + ": " + valor));
        
        return relatorio;
    }
    
    @Override
    public List<Object> gerarRelatorioSemanal(LocalDate dataInicio, LocalDate dataFim) {
        List<Object> relatorio = new ArrayList<>();
        relatorio.add("=== RELATÓRIO SEMANAL - " + dataInicio + " a " + dataFim + " ===");
        
        // Implementar lógica semanal
        double totalSemanal = 0.0;
        LocalDate dataAtual = dataInicio;
        while (!dataAtual.isAfter(dataFim)) {
            totalSemanal += caixaService.getTotalVendasPorData(dataAtual);
            dataAtual = dataAtual.plusDays(1);
        }
        
        relatorio.add("Total de Vendas da Semana: " + totalSemanal);
        return relatorio;
    }
    
    @Override
    public List<Object> gerarRelatorioMensal(int mes, int ano) {
        List<Object> relatorio = new ArrayList<>();
        relatorio.add("=== RELATÓRIO MENSAL - " + mes + "/" + ano + " ===");
        
        LocalDate inicioMes = LocalDate.of(ano, mes, 1);
        LocalDate fimMes = inicioMes.with(TemporalAdjusters.lastDayOfMonth());
        
        return gerarRelatorioSemanal(inicioMes, fimMes);
    }
    
    @Override
    public boolean exportarParaPDF(List<Object> dados, String nomeArquivo) {
        // Implementação simplificada - em um sistema real, usaria bibliotecas como iText
        try {
            System.out.println("Exportando relatório para PDF: " + nomeArquivo);
            dados.forEach(System.out::println);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public void exibirGrafico(List<Object> dados) {
        // Implementação simplificada - em um sistema real, usaria bibliotecas de gráficos
        System.out.println("Exibindo gráfico com dados:");
        dados.forEach(System.out::println);
    }
    
    // Relatórios específicos
    public List<Object> relatorioVendas(LocalDate inicio, LocalDate fim) {
        List<Object> relatorio = new ArrayList<>();
        relatorio.add("=== RELATÓRIO DE VENDAS ===");
        
        LocalDateTime inicioDateTime = inicio.atStartOfDay();
        LocalDateTime fimDateTime = fim.atTime(23, 59, 59);
        
        List<Fatura> faturas = caixaService.getFaturasPorPeriodo(inicioDateTime, fimDateTime);
        
        double total = faturas.stream().mapToDouble(Fatura::getValorTotal).sum();
        relatorio.add("Total de Vendas: " + total);
        relatorio.add("Quantidade de Faturas: " + faturas.size());
        
        return relatorio;
    }
    
    public List<Object> relatorioEmpresasInscritas() {
        List<Object> relatorio = new ArrayList<>();
        relatorio.add("=== RELATÓRIO DE EMPRESAS INSCRITAS ===");
        
        List<Cliente> empresas = vendasService.listarTodos().stream()
                .filter(c -> c.getTipoCliente() == Cliente.TipoCliente.EMPRESA && c.isAtivo())
                .collect(Collectors.toList());
        
        relatorio.add("Total de Empresas: " + empresas.size());
        empresas.forEach(e -> relatorio.add("  " + e.getId() + " - " + e.getNome()));
        
        return relatorio;
    }
    
    public List<Object> relatorioClientesSingularesInscritos() {
        List<Object> relatorio = new ArrayList<>();
        relatorio.add("=== RELATÓRIO DE CLIENTES SINGULARES INSCRITOS ===");
        
        List<Cliente> clientes = vendasService.listarTodos().stream()
                .filter(c -> c.getTipoCliente() == Cliente.TipoCliente.SINGULAR && c.isAtivo())
                .collect(Collectors.toList());
        
        relatorio.add("Total de Clientes Singulares: " + clientes.size());
        clientes.forEach(c -> relatorio.add("  " + c.getId() + " - " + c.getNome()));
        
        return relatorio;
    }
    
    public List<Object> relatorioPagamentosPorTipo(Fatura.TipoPagamento tipoPagamento, 
                                                  LocalDate inicio, LocalDate fim) {
        List<Object> relatorio = new ArrayList<>();
        relatorio.add("=== RELATÓRIO DE PAGAMENTOS " + tipoPagamento.getDescricao().toUpperCase() + " ===");
        
        LocalDateTime inicioDateTime = inicio.atStartOfDay();
        LocalDateTime fimDateTime = fim.atTime(23, 59, 59);
        
        List<Fatura> faturas = caixaService.getFaturasPorTipoPagamentoEPeriodo(
            tipoPagamento, inicioDateTime, fimDateTime);
        
        double total = faturas.stream().mapToDouble(Fatura::getValorTotal).sum();
        relatorio.add("Total: " + total);
        relatorio.add("Quantidade de Transações: " + faturas.size());
        
        return relatorio;
    }
    
    public List<Object> relatorioClienteComMaisCompras() {
        List<Object> relatorio = new ArrayList<>();
        relatorio.add("=== RELATÓRIO DE CLIENTE COM MAIS COMPRAS ===");
        
        Map<String, Integer> comprasPorCliente = new HashMap<>();
        Map<String, Double> valorPorCliente = new HashMap<>();
        
        List<Fatura> todasFaturas = caixaService.listarFaturas();
        
        for (Fatura fatura : todasFaturas) {
            String clienteId = fatura.getClienteId();
            comprasPorCliente.put(clienteId, comprasPorCliente.getOrDefault(clienteId, 0) + 1);
            valorPorCliente.put(clienteId, valorPorCliente.getOrDefault(clienteId, 0.0) + fatura.getValorTotal());
        }
        
        // Encontrar cliente com mais compras
        String clienteTopCompras = comprasPorCliente.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
        
        if (clienteTopCompras != null) {
            Cliente cliente = vendasService.buscar(clienteTopCompras);
            relatorio.add("Cliente com mais compras: " + (cliente != null ? cliente.getNome() : clienteTopCompras));
            relatorio.add("Quantidade de compras: " + comprasPorCliente.get(clienteTopCompras));
            relatorio.add("Valor total: " + valorPorCliente.get(clienteTopCompras));
        }
        
        return relatorio;
    }
    
    public List<Object> relatorioVendedorComMaisVendas() {
        List<Object> relatorio = new ArrayList<>();
        relatorio.add("=== RELATÓRIO DE VENDEDOR COM MAIS VENDAS ===");
        
        Map<String, Integer> vendasPorVendedor = new HashMap<>();
        Map<String, Double> valorPorVendedor = new HashMap<>();
        
        List<Fatura> todasFaturas = caixaService.listarFaturas();
        
        for (Fatura fatura : todasFaturas) {
            String vendedorId = fatura.getVendedorId();
            vendasPorVendedor.put(vendedorId, vendasPorVendedor.getOrDefault(vendedorId, 0) + 1);
            valorPorVendedor.put(vendedorId, valorPorVendedor.getOrDefault(vendedorId, 0.0) + fatura.getValorTotal());
        }
        
        // Encontrar vendedor com mais vendas
        String vendedorTopVendas = vendasPorVendedor.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
        
        if (vendedorTopVendas != null) {
            Usuario vendedor = controleGestorService.buscarUsuario(vendedorTopVendas);
            relatorio.add("Vendedor com mais vendas: " + (vendedor != null ? vendedor.getNome() : vendedorTopVendas));
            relatorio.add("Quantidade de vendas: " + vendasPorVendedor.get(vendedorTopVendas));
            relatorio.add("Valor total: " + valorPorVendedor.get(vendedorTopVendas));
        }
        
        return relatorio;
    }
    
    // Método recursivo para gerar relatório consolidado
    public List<Object> gerarRelatorioConsolidado(LocalDate inicio, LocalDate fim) {
        return gerarRelatorioConsolidadoRecursivo(inicio, fim, new ArrayList<>(), 0);
    }
    
    /**
     * Gera relatório consolidado para o mês atual
     */
    public Map<String, Object> gerarRelatorioConsolidado() {
        LocalDate hoje = LocalDate.now();
        LocalDate inicioMes = hoje.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate fimMes = hoje.with(TemporalAdjusters.lastDayOfMonth());
        
        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("Período", inicioMes + " a " + fimMes);
        relatorio.put("Data Geração", hoje);
        
        // Adicionar dados consolidados
        List<Object> dadosConsolidados = gerarRelatorioConsolidado(inicioMes, fimMes);
        relatorio.put("Dados", dadosConsolidados);
        
        return relatorio;
    }
    
    /**
     * Gera relatório semanal para a data especificada
     */
    public Map<String, Object> gerarRelatorioSemanal(LocalDate data) {
        LocalDate inicioSemana = data.minusDays(data.getDayOfWeek().getValue() - 1);
        LocalDate fimSemana = inicioSemana.plusDays(6);
        
        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("Período", inicioSemana + " a " + fimSemana);
        relatorio.put("Data Geração", LocalDate.now());
        
        List<Object> dadosSemana = gerarRelatorioSemanal(inicioSemana, fimSemana);
        relatorio.put("Dados", dadosSemana);
        
        return relatorio;
    }
    
    /**
     * Gera relatório mensal para a data especificada
     */
    public Map<String, Object> gerarRelatorioMensal(LocalDate data) {
        LocalDate inicioMes = data.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate fimMes = data.with(TemporalAdjusters.lastDayOfMonth());
        
        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("Período", inicioMes + " a " + fimMes);
        relatorio.put("Data Geração", LocalDate.now());
        
        List<Object> dadosMes = gerarRelatorioMensal(data.getMonthValue(), data.getYear());
        relatorio.put("Dados", dadosMes);
        
        return relatorio;
    }
    
    private List<Object> gerarRelatorioConsolidadoRecursivo(LocalDate inicio, LocalDate fim, 
                                                           List<Object> relatorio, int secao) {
        if (secao >= 7) { // 7 seções diferentes
            return relatorio;
        }
        
        switch (secao) {
            case 0:
                relatorio.addAll(relatorioVendas(inicio, fim));
                break;
            case 1:
                relatorio.addAll(relatorioEmpresasInscritas());
                break;
            case 2:
                relatorio.addAll(relatorioClientesSingularesInscritos());
                break;
            case 3:
                relatorio.addAll(relatorioPagamentosPorTipo(Fatura.TipoPagamento.MULTI_REDES, inicio, fim));
                break;
            case 4:
                relatorio.addAll(relatorioPagamentosPorTipo(Fatura.TipoPagamento.MPESA, inicio, fim));
                break;
            case 5:
                relatorio.addAll(relatorioClienteComMaisCompras());
                break;
            case 6:
                relatorio.addAll(relatorioVendedorComMaisVendas());
                break;
        }
        
        relatorio.add(""); // Linha em branco entre seções
        return gerarRelatorioConsolidadoRecursivo(inicio, fim, relatorio, secao + 1);
    }
    
}
