/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDateTime;

/**
 *
 * @author massas
 * 
 * Classe Pagamento - Representa um pagamento de uma ordem
 */
public class Pagamento extends UniversalObject {
    private String ordemCodigo;
    private TipoPagamento tipoPagamento;
    private double valorTotal;
    private double valorPago;
    private double troco;
    private LocalDateTime dataPagamento;
    private String usuarioId;
    private String usuarioNome;

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public enum TipoPagamento {
        DINHEIRO, CARTAO, TRANSFERENCIA
    }
    
    public Pagamento() {
        super();
        this.dataPagamento = LocalDateTime.now();
    }
    
    public Pagamento(String id, String ordemCodigo, TipoPagamento tipoPagamento, 
                    double valorTotal, double valorPago, String usuarioId) {
        super();
        this.setId(id);
        this.ordemCodigo = ordemCodigo;
        this.tipoPagamento = tipoPagamento;
        this.valorTotal = valorTotal;
        this.valorPago = valorPago;
        this.troco = valorPago - valorTotal;
        this.dataPagamento = LocalDateTime.now();
        this.usuarioId = usuarioId;
    }
    
    // Getters e Setters
    public String getOrdemCodigo() { return ordemCodigo; }
    public void setOrdemCodigo(String ordemCodigo) { this.ordemCodigo = ordemCodigo; }
    
    public TipoPagamento getTipoPagamento() { return tipoPagamento; }
    public void setTipoPagamento(TipoPagamento tipoPagamento) { this.tipoPagamento = tipoPagamento; }
    
    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }
    
    public double getValorPago() { return valorPago; }
    public void setValorPago(double valorPago) { 
        this.valorPago = valorPago;
        this.troco = valorPago - valorTotal;
    }
    
    public double getTroco() { return troco; }
    public void setTroco(double troco) { this.troco = troco; }
    
    public LocalDateTime getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDateTime dataPagamento) { this.dataPagamento = dataPagamento; }
    
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
    
    public String getUsuarioNome() { return usuarioNome; }
    public void setUsuarioNome(String usuarioNome) { this.usuarioNome = usuarioNome; }
    
    @Override
    public String toString() {
        return "Pagamento{" +
                "id='" + getId() + '\'' +
                ", ordemCodigo='" + ordemCodigo + '\'' +
                ", tipoPagamento=" + tipoPagamento +
                ", valorTotal=" + valorTotal +
                ", valorPago=" + valorPago +
                ", troco=" + troco +
                ", dataPagamento=" + dataPagamento +
                '}';
    }
}
