/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author massas
 * 
 * Classe ItemOrdem - Representa um item dentro de uma ordem
 */
public class ItemOrdem extends UniversalObject {
    private String produtoId;
    private String produtoCodigo;
    private String produtoDescricao;
    private Produto produto;
    private int quantidade;
    private double precoUnitario;
    private double subtotal;
    
    public ItemOrdem() {
        super();
    }
    
    public ItemOrdem(String produtoId, String produtoCodigo, String produtoDescricao, 
                     int quantidade, double precoUnitario) {
        super();
        this.produtoId = produtoId;
        this.produtoCodigo = produtoCodigo;
        this.produtoDescricao = produtoDescricao;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subtotal = quantidade * precoUnitario;
    }
    
    public ItemOrdem(Produto produto, int quantidade) {
        super();
        this.produto = produto;
        this.produtoId = produto.getId();
        this.produtoCodigo = produto.getCodigo();
        this.produtoDescricao = produto.getDescricao();
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco();
        this.subtotal = quantidade * precoUnitario;
    }
    
    /**
     * Recalcula o subtotal
     */
    public void calcularSubtotal() {
        this.subtotal = quantidade * precoUnitario;
    }
    
    // Getters e Setters
    public String getProdutoId() { return produtoId; }
    public void setProdutoId(String produtoId) { this.produtoId = produtoId; }
    
    public String getProdutoCodigo() { return produtoCodigo; }
    public void setProdutoCodigo(String produtoCodigo) { this.produtoCodigo = produtoCodigo; }
    
    public String getProdutoDescricao() { return produtoDescricao; }
    public void setProdutoDescricao(String produtoDescricao) { this.produtoDescricao = produtoDescricao; }
    
    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { 
        this.produto = produto;
        if (produto != null) {
            this.produtoId = produto.getId();
            this.produtoCodigo = produto.getCodigo();
            this.produtoDescricao = produto.getDescricao();
            this.precoUnitario = produto.getPreco();
            calcularSubtotal();
        }
    }
    
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { 
        this.quantidade = quantidade;
        calcularSubtotal();
    }
    
    public double getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(double precoUnitario) { 
        this.precoUnitario = precoUnitario;
        calcularSubtotal();
    }
    
    public double getSubtotal() { return subtotal; }
    
    @Override
    public String toString() {
        return "ItemOrdem{" +
                "produtoCodigo='" + produtoCodigo + '\'' +
                ", produtoDescricao='" + produtoDescricao + '\'' +
                ", quantidade=" + quantidade +
                ", precoUnitario=" + precoUnitario +
                ", subtotal=" + subtotal +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ItemOrdem itemOrdem = (ItemOrdem) obj;
        return produtoId != null ? produtoId.equals(itemOrdem.produtoId) : itemOrdem.produtoId == null;
    }
    
    @Override
    public int hashCode() {
        return produtoId != null ? produtoId.hashCode() : 0;
    }
    
}
