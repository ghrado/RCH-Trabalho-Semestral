/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Fatura;
import Model.Ordem;
import Model.Pagamento;
import Util.AuditService;
import Util.GeradorID;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 *
 * @author massas
 * 
 * Classe CaixaService - Gerencia operações de caixa e pagamentos
 */
public class CaixaService {
    private Map<String, Pagamento> pagamentos;
    private Vector<Pagamento> pagamentosVector;
    private GeradorID geradorID;
    private AuditService auditService;
    private VendasService vendasService;
    private boolean caixaFechado;
    
    public CaixaService(VendasService vendasService) {
        this.pagamentos = new HashMap<>();
        this.pagamentosVector = new Vector<>();
        this.geradorID = GeradorID.getInstance();
        this.auditService = AuditService.getInstance();
        this.vendasService = vendasService;
        this.caixaFechado = false;
    }
    
    /**
     * Processa um pagamento para uma ordem
     */
    public boolean processarPagamento(String ordemCodigo, Pagamento.TipoPagamento tipoPagamento, 
                                      double valorPago, String usuarioId) {
        if (caixaFechado) {
            return false; // Caixa está fechado
        }
        
        Ordem ordem = vendasService.buscarOrdem(ordemCodigo);
        
        if (ordem == null) {
            return false;
        }
        
        if (ordem.getStatus() == Ordem.StatusOrdem.PAGO) {
            return false; // Ordem já paga
        }
        
        // Criar pagamento
        String pagamentoId = geradorID.gerarID("PAG");
        Pagamento pagamento = new Pagamento(pagamentoId, ordemCodigo, tipoPagamento, 
                                            ordem.getValorTotal(), valorPago, usuarioId);
        
        // Calcular troco
        double troco = valorPago - ordem.getValorTotal();
        pagamento.setTroco(troco);
        
        // Salvar pagamento
        pagamentos.put(pagamentoId, pagamento);
        pagamentosVector.add(pagamento);
        
        // Atualizar status da ordem
        ordem.setStatus(Ordem.StatusOrdem.PAGO);
        
        // Registrar ação
        auditService.registrarAcao(usuarioId, "", "PAGAMENTO", "Ordem", ordemCodigo, 
            "Pagamento processado: " + tipoPagamento + " - Valor: " + valorPago);
        
        return true;
    }
    
    /**
     * Busca um pagamento por ID
     */
    public Pagamento buscarPagamento(String id) {
        return pagamentos.get(id);
    }
    
    /**
     * Lista todos os pagamentos
     */
    public List<Pagamento> listarTodosPagamentos() {
        return new ArrayList<>(pagamentosVector);
    }
    
    /**
     * Busca pagamentos por ordem
     */
    public List<Pagamento> buscarPagamentosPorOrdem(String ordemCodigo) {
        return pagamentosVector.stream()
                .filter(p -> p.getOrdemCodigo().equals(ordemCodigo))
                .collect(Collectors.toList());
    }
    
    /**
     * Busca pagamentos por data
     */
    public List<Pagamento> buscarPagamentosPorData(LocalDate data) {
        return pagamentosVector.stream()
                .filter(p -> p.getDataPagamento().toLocalDate().equals(data))
                .collect(Collectors.toList());
    }
    
    /**
     * Busca pagamentos por período
     */
    public List<Pagamento> buscarPagamentosPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return pagamentosVector.stream()
                .filter(p -> !p.getDataPagamento().isBefore(inicio) && 
                           !p.getDataPagamento().isAfter(fim))
                .collect(Collectors.toList());
    }
    
    /**
     * Busca pagamentos por tipo
     */
    public List<Pagamento> buscarPagamentosPorTipo(Pagamento.TipoPagamento tipo) {
        return pagamentosVector.stream()
                .filter(p -> p.getTipoPagamento() == tipo)
                .collect(Collectors.toList());
    }
    
    /**
     * Calcula total de pagamentos por período
     */
    public double calcularTotalPagamentos(LocalDateTime inicio, LocalDateTime fim) {
        return buscarPagamentosPorPeriodo(inicio, fim).stream()
                .mapToDouble(Pagamento::getValorTotal)
                .sum();
    }
    
    /**
     * Calcula total de pagamentos por tipo e período
     */
    public double calcularTotalPagamentosPorTipo(Pagamento.TipoPagamento tipo, 
                                                 LocalDateTime inicio, LocalDateTime fim) {
        return buscarPagamentosPorPeriodo(inicio, fim).stream()
                .filter(p -> p.getTipoPagamento() == tipo)
                .mapToDouble(Pagamento::getValorTotal)
                .sum();
    }
    
    /**
     * Gera relatório de caixa para um período
     */
    public Map<String, Object> gerarRelatorioCaixa(LocalDateTime inicio, LocalDateTime fim) {
        Map<String, Object> relatorio = new HashMap<>();
        
        List<Pagamento> pagamentosPeriodo = buscarPagamentosPorPeriodo(inicio, fim);
        
        double totalDinheiro = pagamentosPeriodo.stream()
                .filter(p -> p.getTipoPagamento() == Pagamento.TipoPagamento.DINHEIRO)
                .mapToDouble(Pagamento::getValorTotal)
                .sum();
        
        double totalCartao = pagamentosPeriodo.stream()
                .filter(p -> p.getTipoPagamento() == Pagamento.TipoPagamento.CARTAO)
                .mapToDouble(Pagamento::getValorTotal)
                .sum();
        
        double totalTransferencia = pagamentosPeriodo.stream()
                .filter(p -> p.getTipoPagamento() == Pagamento.TipoPagamento.TRANSFERENCIA)
                .mapToDouble(Pagamento::getValorTotal)
                .sum();
        
        double totalGeral = totalDinheiro + totalCartao + totalTransferencia;
        
        relatorio.put("totalDinheiro", totalDinheiro);
        relatorio.put("totalCartao", totalCartao);
        relatorio.put("totalTransferencia", totalTransferencia);
        relatorio.put("totalGeral", totalGeral);
        relatorio.put("quantidadePagamentos", pagamentosPeriodo.size());
        relatorio.put("pagamentos", pagamentosPeriodo);
        
        return relatorio;
    }
    
    /**
     * Registra ação de caixa
     */
    public void registrarAcaoCaixa(String pagamentoId, String acao, String usuarioId, String detalhes) {
        Pagamento pagamento = buscarPagamento(pagamentoId);
        if (pagamento != null) {
            auditService.registrarAcao(usuarioId, "", acao, "Pagamento", pagamentoId, detalhes);
        }
    }

    /**
     * Gera resumo de fecho de caixa com totais por tipo de pagamento
     */
    public Map<String, Double> gerarResumoFechoCaixa() {
        Map<String, Double> resumo = new HashMap<>();
        LocalDate hoje = LocalDate.now();
        
        List<Pagamento> pagamentosHoje = buscarPagamentosPorData(hoje);
        
        // Calcular totais por tipo de pagamento
        double totalMultiRedes = pagamentosHoje.stream()
                .filter(p -> p.getTipoPagamento() == Pagamento.TipoPagamento.CARTAO)
                .mapToDouble(Pagamento::getValorTotal)
                .sum();
        
        double totalDinheiro = pagamentosHoje.stream()
                .filter(p -> p.getTipoPagamento() == Pagamento.TipoPagamento.DINHEIRO)
                .mapToDouble(Pagamento::getValorTotal)
                .sum();
        
        double totalEMola = pagamentosHoje.stream()
                .filter(p -> p.getTipoPagamento() == Pagamento.TipoPagamento.TRANSFERENCIA)
                .mapToDouble(Pagamento::getValorTotal)
                .sum();
        
        // M-Pesa e Cheque podem ser adicionados quando o enum Pagamento.TipoPagamento for expandido
        double totalMPesa = 0.0;
        double totalCheque = 0.0;
        
        double totalGeral = totalMultiRedes + totalDinheiro + totalEMola + totalMPesa + totalCheque;
        
        resumo.put("Multi-Redes", totalMultiRedes);
        resumo.put("Dinheiro", totalDinheiro);
        resumo.put("E-Mola", totalEMola);
        resumo.put("M-Pesa", totalMPesa);
        resumo.put("Cheque", totalCheque);
        resumo.put("TOTAL", totalGeral);
        
        return resumo;
    }
    
    /**
     * Fecha o caixa do dia
     */
    public boolean fecharCaixa(String usuarioId) {
        if (caixaFechado) {
            return false; // Caixa já fechado
        }
        
        try {
            Map<String, Double> resumo = gerarResumoFechoCaixa();
            
            // Registrar fecho de caixa no audit
            auditService.registrarAcao(usuarioId, "", "FECHO_CAIXA", "Caixa", 
                LocalDate.now().toString(), 
                "Caixa fechado - Total: " + resumo.get("TOTAL"));
            
            caixaFechado = true;
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Reabre o caixa (para novo dia ou correção)
     */
    public void reabrirCaixa() {
        caixaFechado = false;
    }
    
    /**
     * Verifica se o caixa está fechado
     */
    public boolean isCaixaFechado() {
        return caixaFechado;
    }

    List<Fatura> getFaturasPorTipoPagamentoEPeriodo(Fatura.TipoPagamento tipoPagamento, LocalDateTime inicioDateTime, LocalDateTime fimDateTime) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    List<Fatura> listarFaturas() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    double getTotalVendasPorData(LocalDate data) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    Map<Fatura.TipoPagamento, Double> getLancamentosPorData(LocalDate data) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    List<Fatura> getFaturasPorPeriodo(LocalDateTime inicioDateTime, LocalDateTime fimDateTime) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
