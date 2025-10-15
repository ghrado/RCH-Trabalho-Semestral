/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author massas
 * Classe Fatura - Representa uma fatura gerada após o pagamento
 */
public class Fatura extends UniversalObject {
    private String codigo;
    private String ordemId;
    private String vendedorId;
    private String vendedorNome;
    private String caixaId;
    private String caixaNome;
    private String clienteId;
    private String clienteNome;
    private double valorTotal;
    private TipoPagamento tipoPagamento;
    private LocalDateTime dataProcessamento;
    
    public enum TipoPagamento {
        MULTI_REDES("Multi Redes"),
        EMOLA("E-Mola"),
        MPESA("M-Pesa"),
        DINHEIRO("Dinheiro"),
        CHEQUE("Cheque"),
        NOTA_CREDITO("Nota de Crédito");
        
        private String descricao;
        
        TipoPagamento(String descricao) {
            this.descricao = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }
    
    public Fatura() {
        super();
        this.dataProcessamento = LocalDateTime.now();
        this.codigo = gerarCodigoFatura();
    }
    
    public Fatura(String ordemId, String vendedorId, String vendedorNome, 
                  String caixaId, String caixaNome, String clienteId, String clienteNome,
                  double valorTotal, TipoPagamento tipoPagamento) {
        this();
        this.ordemId = ordemId;
        this.vendedorId = vendedorId;
        this.vendedorNome = vendedorNome;
        this.caixaId = caixaId;
        this.caixaNome = caixaNome;
        this.clienteId = clienteId;
        this.clienteNome = clienteNome;
        this.valorTotal = valorTotal;
        this.tipoPagamento = tipoPagamento;
    }
    
    /**
     * Gera o código da fatura no formato YYYYMMDD0000
     */
    private String gerarCodigoFatura() {
        String dataAtual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // Aqui seria implementada a lógica para incrementar o número sequencial
        // Por simplicidade, usando um número aleatório
        int sequencial = (int) (Math.random() * 9999) + 1;
        return dataAtual + String.format("%04d", sequencial);
    }
    
    /**
     * Obtém a data de processamento formatada
     */
    public String getDataProcessamentoFormatada() {
        return dataProcessamento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
    
    // Getters e Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    
    public String getOrdemId() { return ordemId; }
    public void setOrdemId(String ordemId) { this.ordemId = ordemId; }
    
    public String getVendedorId() { return vendedorId; }
    public void setVendedorId(String vendedorId) { this.vendedorId = vendedorId; }
    
    public String getVendedorNome() { return vendedorNome; }
    public void setVendedorNome(String vendedorNome) { this.vendedorNome = vendedorNome; }
    
    public String getCaixaId() { return caixaId; }
    public void setCaixaId(String caixaId) { this.caixaId = caixaId; }
    
    public String getCaixaNome() { return caixaNome; }
    public void setCaixaNome(String caixaNome) { this.caixaNome = caixaNome; }
    
    public String getClienteId() { return clienteId; }
    public void setClienteId(String clienteId) { this.clienteId = clienteId; }
    
    public String getClienteNome() { return clienteNome; }
    public void setClienteNome(String clienteNome) { this.clienteNome = clienteNome; }
    
    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }
    
    public TipoPagamento getTipoPagamento() { return tipoPagamento; }
    public void setTipoPagamento(TipoPagamento tipoPagamento) { this.tipoPagamento = tipoPagamento; }
    
    public LocalDateTime getDataProcessamento() { return dataProcessamento; }
    public void setDataProcessamento(LocalDateTime dataProcessamento) { this.dataProcessamento = dataProcessamento; }
    
    @Override
    public String toString() {
        return "Fatura{" +
                "codigo='" + codigo + '\'' +
                ", ordemId='" + ordemId + '\'' +
                ", clienteNome='" + clienteNome + '\'' +
                ", valorTotal=" + valorTotal +
                ", tipoPagamento=" + tipoPagamento +
                ", dataProcessamento=" + getDataProcessamentoFormatada() +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Fatura fatura = (Fatura) obj;
        return codigo != null ? codigo.equals(fatura.codigo) : fatura.codigo == null;
    }
    
    @Override
    public int hashCode() {
        return codigo != null ? codigo.hashCode() : 0;
    }
    
}
