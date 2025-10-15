/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Interfaces.Gerenciavel;
import Model.Produto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 *
 * @author massas
 * 
 * Classe StockService - Gerencia produtos e stock
 */
public class StockService implements Gerenciavel<Produto> {
    private Map<String, Produto> produtos;
    private Vector<Produto> produtosVector; // Usando Vector conforme solicitado
    private ArrayList<Produto> produtosArrayList; // Usando ArrayList conforme solicitado
    
    public StockService() {
        this.produtos = new HashMap<>();
        this.produtosVector = new Vector<>();
        this.produtosArrayList = new ArrayList<>();
    }
    
    // Implementação da interface Gerenciavel
    @Override
    public boolean criar(Produto produto) {
        if (produto == null || produto.getCodigo() == null) return false;
        
        // Verificar se já existe produto com o mesmo código
        if (buscarPorCodigo(produto.getCodigo()) != null) {
            return false; // Código já existe
        }
        
        String id = UUID.randomUUID().toString();
        produto.setId(id);
        
        produtos.put(id, produto);
        produtosVector.add(produto);
        produtosArrayList.add(produto);
        return true;
    }
    
    @Override
    public Produto buscar(String id) {
        return produtos.get(id);
    }
    
    @Override
    public List<Produto> listarTodos() {
        return new ArrayList<>(produtosArrayList);
    }
    
    @Override
    public boolean atualizar(Produto produto) {
        if (produto == null || produto.getId() == null) return false;
        
        Produto existente = produtos.get(produto.getId());
        if (existente != null) {
            produtos.put(produto.getId(), produto);
            
            // Atualizar no Vector
            int indexVector = produtosVector.indexOf(existente);
            if (indexVector >= 0) {
                produtosVector.set(indexVector, produto);
            }
            
            // Atualizar no ArrayList
            int indexArrayList = produtosArrayList.indexOf(existente);
            if (indexArrayList >= 0) {
                produtosArrayList.set(indexArrayList, produto);
            }
            
            return true;
        }
        return false;
    }
    
    @Override
    public boolean excluir(String id) {
        Produto produto = produtos.get(id);
        if (produto != null) {
            produto.setAtivo(false);
            produtosVector.remove(produto);
            produtosArrayList.remove(produto);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean reintegrar(String id) {
        Produto produto = produtos.get(id);
        if (produto != null && !produto.isAtivo()) {
            produto.setAtivo(true);
            produtosVector.add(produto);
            produtosArrayList.add(produto);
            return true;
        }
        return false;
    }
    
    // Métodos específicos para produtos
    public Produto buscarPorCodigo(String codigo) {
        return produtosVector.stream()
                .filter(p -> p.isAtivo() && p.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }
    
    public List<Produto> buscarPorDescricao(String descricao) {
        return produtosArrayList.stream()
                .filter(p -> p.isAtivo() && p.getDescricao().toLowerCase().contains(descricao.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    public List<Produto> buscarProduto(String termo) {
        List<Produto> resultados = new ArrayList<>();
        
        // Buscar por código exato
        Produto porCodigo = buscarPorCodigo(termo);
        if (porCodigo != null) {
            resultados.add(porCodigo);
        }
        
        // Buscar por descrição
        List<Produto> porDescricao = buscarPorDescricao(termo);
        for (Produto p : porDescricao) {
            if (!resultados.contains(p)) {
                resultados.add(p);
            }
        }
        
        return resultados;
    }
    
    // Gestão de Stock
    public boolean definirStockMinimo(String produtoId, int stockMinimo) {
        Produto produto = buscar(produtoId);
        if (produto != null) {
            produto.setStockMinimo(stockMinimo);
            return true;
        }
        return false;
    }
    
    public boolean definirStockMaximo(String produtoId, int stockMaximo) {
        Produto produto = buscar(produtoId);
        if (produto != null) {
            produto.setStockMaximo(stockMaximo);
            return true;
        }
        return false;
    }
    
    public List<Produto> getProdutosStockBaixo() {
        return produtosArrayList.stream()
                .filter(p -> p.isAtivo() && p.isStockBaixo())
                .collect(Collectors.toList());
    }
    
    public List<Produto> getProdutosSemStock() {
        return produtosArrayList.stream()
                .filter(p -> p.isAtivo() && p.isSemStock())
                .collect(Collectors.toList());
    }
    
    public boolean reduzirStock(String produtoId, int quantidade) {
        Produto produto = buscar(produtoId);
        if (produto != null && produto.isAtivo()) {
            return produto.reduzirStock(quantidade);
        }
        return false;
    }
    
    public boolean aumentarStock(String produtoId, int quantidade) {
        Produto produto = buscar(produtoId);
        if (produto != null && produto.isAtivo()) {
            produto.aumentarStock(quantidade);
            return true;
        }
        return false;
    }
    
    public boolean verificarDisponibilidade(String produtoId, int quantidadeDesejada) {
        Produto produto = buscar(produtoId);
        if (produto != null && produto.isAtivo()) {
            return produto.getQuantidadeStock() >= quantidadeDesejada;
        }
        return false;
    }
    
    // Método para obter produtos excluídos
    public List<Produto> getProdutosExcluidos() {
        return produtos.values().stream()
                .filter(p -> !p.isAtivo())
                .collect(Collectors.toList());
    }
    
    // Método recursivo para calcular valor total do stock
    public double calcularValorTotalStock() {
        return calcularValorTotalStockRecursivo(produtosArrayList, 0);
    }
    
    private double calcularValorTotalStockRecursivo(List<Produto> produtos, int index) {
        if (index >= produtos.size()) {
            return 0.0;
        }
        
        Produto produto = produtos.get(index);
        double valorProduto = produto.isAtivo() ? 
            produto.getPreco() * produto.getQuantidadeStock() : 0.0;
        
        return valorProduto + calcularValorTotalStockRecursivo(produtos, index + 1);
    }
    
}
