/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author massas
 */
public class GeradorID {
    private static GeradorID instance;
    private Map<String, Integer> contadores;
    private Map<String, Stack<String>> idsReutilizaveis;
    
    private GeradorID() {
        contadores = new HashMap<>();
        idsReutilizaveis = new HashMap<>();
        inicializarContadores();
    }
    
    public static synchronized GeradorID getInstance() {
        if (instance == null) {
            instance = new GeradorID();
        }
        return instance;
    }
    
    private void inicializarContadores() {
        contadores.put("CS", 1); // Cliente Singular
        contadores.put("CE", 1); // Cliente Empresa
        contadores.put("GE", 1); // Gerente
        contadores.put("CX", 1); // Caixa
        contadores.put("VN", 1); // Vendedor
        contadores.put("ORDEM", 1); // Ordem
        
        contadores.put("VF", 1);  // Varao
        contadores.put("CAN", 1); // Canalizacao
        contadores.put("ELE", 1); // Eletrico
        contadores.put("BAR", 1); // Barrote
        contadores.put("PRA", 1); // Prancha
        contadores.put("PRE", 1); // Prego
        contadores.put("FER", 1); // Ferramentas
        contadores.put("CN", 1);  // Cimento
        contadores.put("DIV", 1); // Diversos
        
        // Inicializar stacks para IDs reutilizáveis
        idsReutilizaveis.put("CS", new Stack<>());
        idsReutilizaveis.put("CE", new Stack<>());
        idsReutilizaveis.put("GE", new Stack<>());
        idsReutilizaveis.put("CX", new Stack<>());
        idsReutilizaveis.put("VN", new Stack<>());
        idsReutilizaveis.put("ORDEM", new Stack<>());
        
        idsReutilizaveis.put("VF", new Stack<>());
        idsReutilizaveis.put("CAN", new Stack<>());
        idsReutilizaveis.put("ELE", new Stack<>());
        idsReutilizaveis.put("BAR", new Stack<>());
        idsReutilizaveis.put("PRA", new Stack<>());
        idsReutilizaveis.put("PRE", new Stack<>());
        idsReutilizaveis.put("FER", new Stack<>());
        idsReutilizaveis.put("CN", new Stack<>());
        idsReutilizaveis.put("DIV", new Stack<>());
    }
    
    /**
     * Gera um novo ID para o tipo especificado
     */
    public synchronized String gerarID(String tipo) {
        Stack<String> stackReutilizaveis = idsReutilizaveis.get(tipo);
        
        // Se há IDs reutilizáveis, usar o último da pilha
        if (stackReutilizaveis != null && !stackReutilizaveis.isEmpty()) {
            return stackReutilizaveis.pop();
        }
        
        // Caso contrário, gerar novo ID
        Integer contador = contadores.get(tipo);
        if (contador == null) {
            contador = 1;
        }
        
        String novoId;
        if (tipo.equals("ORDEM")) {
            novoId = String.format("%04d", contador);
        } else if (isProdutoClass(tipo)) {
            // Product classes use 2 digits: VF00, CAN00, etc.
            novoId = tipo + String.format("%02d", contador);
        } else {
            novoId = tipo + String.format("%04d", contador);
        }
        
        contadores.put(tipo, contador + 1);
        return novoId;
    }
    
    private boolean isProdutoClass(String tipo) {
        return tipo.equals("VF") || tipo.equals("CAN") || tipo.equals("ELE") || 
               tipo.equals("BAR") || tipo.equals("PRA") || tipo.equals("PRE") || 
               tipo.equals("FER") || tipo.equals("CN") || tipo.equals("DIV");
    }
    
    /**
     * Adiciona um ID para reutilização (quando um objeto é excluído)
     */
    public synchronized void adicionarIdParaReutilizacao(String tipo, String id) {
        Stack<String> stackReutilizaveis = idsReutilizaveis.get(tipo);
        if (stackReutilizaveis != null && !stackReutilizaveis.contains(id)) {
            stackReutilizaveis.push(id);
        }
    }
    
    /**
     * Remove um ID da lista de reutilizáveis (quando um objeto é reintegrado)
     */
    public synchronized void removerIdDeReutilizacao(String tipo, String id) {
        Stack<String> stackReutilizaveis = idsReutilizaveis.get(tipo);
        if (stackReutilizaveis != null) {
            stackReutilizaveis.remove(id);
        }
    }
    
    /**
     * Método recursivo para validar formato de ID
     */
    public boolean validarFormatoId(String id, String tipo) {
        return validarFormatoIdRecursivo(id, tipo, 0);
    }
    
    private boolean validarFormatoIdRecursivo(String id, String tipo, int posicao) {
        if (id == null || id.isEmpty()) {
            return false;
        }
        
        if (tipo.equals("ORDEM")) {
            // Para ordens, deve ter exatamente 4 dígitos
            if (id.length() != 4) return false;
            if (posicao >= id.length()) return true;
            
            char c = id.charAt(posicao);
            if (!Character.isDigit(c)) return false;
            
            return validarFormatoIdRecursivo(id, tipo, posicao + 1);
        } else if (isProdutoClass(tipo)) {
            String prefixoEsperado = tipo;
            if (id.length() != prefixoEsperado.length() + 2) return false;
            
            if (posicao < prefixoEsperado.length()) {
                if (id.charAt(posicao) != prefixoEsperado.charAt(posicao)) return false;
                return validarFormatoIdRecursivo(id, tipo, posicao + 1);
            } else if (posicao < id.length()) {
                char c = id.charAt(posicao);
                if (!Character.isDigit(c)) return false;
                return validarFormatoIdRecursivo(id, tipo, posicao + 1);
            }
            
            return true;
        } else {
            // Para outros tipos, deve começar com o prefixo + 4 dígitos
            String prefixoEsperado = tipo;
            if (id.length() != prefixoEsperado.length() + 4) return false;
            
            if (posicao < prefixoEsperado.length()) {
                if (id.charAt(posicao) != prefixoEsperado.charAt(posicao)) return false;
                return validarFormatoIdRecursivo(id, tipo, posicao + 1);
            } else if (posicao < id.length()) {
                char c = id.charAt(posicao);
                if (!Character.isDigit(c)) return false;
                return validarFormatoIdRecursivo(id, tipo, posicao + 1);
            }
            
            return true;
        }
    }
    
}
