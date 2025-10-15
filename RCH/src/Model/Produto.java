/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author anicamassas
 */
public class Produto extends UniversalObject {
    private String codigo;
    private String descricao;
    private double preco;
    private int quantidadeStock;
    private int stockMinimo;
    private int stockMaximo;
    private String categoria;
    private String unidadeMedida;
    
    public Produto() {
        super();
    }
    
    public Produto(String codigo, String descricao, double preco, int quantidadeStock) {
        super();
        this.codigo = codigo;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeStock = quantidadeStock;
        this.stockMinimo = 10; // Valor padrão
        this.stockMaximo = 1000; // Valor padrão
    }
    
    /**
     * Verifica se o produto está com stock baixo
     */
    public boolean isStockBaixo() {
        return quantidadeStock <= stockMinimo;
    }
    
    /**
     * Verifica se o produto está sem stock
     */
    public boolean isSemStock() {
        return quantidadeStock <= 0;
    }
    
    /**
     * Reduz o stock do produto
     */
    public boolean reduzirStock(int quantidade) {
        if (quantidadeStock >= quantidade) {
            quantidadeStock -= quantidade;
            this.dataModificacao = java.time.LocalDateTime.now();
            return true;
        }
        return false;
    }
    
    /**
     * Aumenta o stock do produto
     */
    public void aumentarStock(int quantidade) {
        quantidadeStock += quantidade;
        this.dataModificacao = java.time.LocalDateTime.now();
    }
    
    // Getters e Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
    
    public int getQuantidadeStock() { return quantidadeStock; }
    public void setQuantidadeStock(int quantidadeStock) { this.quantidadeStock = quantidadeStock; }
    
    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }
    
    public int getStockMaximo() { return stockMaximo; }
    public void setStockMaximo(int stockMaximo) { this.stockMaximo = stockMaximo; }
    
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    
    public String getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }
    
    @Override
    public String toString() {
        return "Produto{" +
                "id='" + id + '\'' +
                ", codigo='" + codigo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                ", quantidadeStock=" + quantidadeStock +
                ", ativo=" + ativo +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Produto produto = (Produto) obj;
        return codigo != null ? codigo.equals(produto.codigo) : produto.codigo == null;
    }
    
    @Override
    public int hashCode() {
        return codigo != null ? codigo.hashCode() : 0;
    }
    
}
