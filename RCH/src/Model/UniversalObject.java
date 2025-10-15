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
 * 
 * Classe Universal - Base para todos os objetos do sistema
 */
public abstract class UniversalObject {
    protected String id;
    protected LocalDateTime dataCriacao;
    protected LocalDateTime dataModificacao;
    protected boolean ativo;
    
    public UniversalObject() {
        this.dataCriacao = LocalDateTime.now();
        this.dataModificacao = LocalDateTime.now();
        this.ativo = true;
    }
    
    public UniversalObject(String id) {
        this();
        this.id = id;
    }
    
    // Getters e Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    public LocalDateTime getDataModificacao() {
        return dataModificacao;
    }
    
    public void setDataModificacao(LocalDateTime dataModificacao) {
        this.dataModificacao = dataModificacao;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
        this.dataModificacao = LocalDateTime.now();
    }
    
    public String getDataCriacaoFormatada() {
        return dataCriacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
    
    public String getDataModificacaoFormatada() {
        return dataModificacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
    
    // Métodos abstratos que devem ser implementados pelas subclasses
    @Override
    public abstract String toString();
    @Override
    public abstract boolean equals(Object obj);
    @Override
    public abstract int hashCode();
    
}
