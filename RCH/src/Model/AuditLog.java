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
 * Classe AuditLog - Registra todas as ações realizadas no sistema
 */
public class AuditLog {
    private String id;
    private String usuarioId;
    private String usuarioNome;
    private String acao;
    private String entidade;
    private String entidadeId;
    private String detalhes;
    private LocalDateTime dataHora;
    
    public enum TipoAcao {
        CRIAR, EDITAR, APAGAR, PROCESSAR, CANCELAR, REINTEGRAR, LOGIN, LOGOUT
    }
    
    public AuditLog() {
        this.dataHora = LocalDateTime.now();
        this.id = gerarId();
    }
    
    public AuditLog(String usuarioId, String usuarioNome, String acao, 
                    String entidade, String entidadeId, String detalhes) {
        this();
        this.usuarioId = usuarioId;
        this.usuarioNome = usuarioNome;
        this.acao = acao;
        this.entidade = entidade;
        this.entidadeId = entidadeId;
        this.detalhes = detalhes;
    }
    
    private String gerarId() {
        return "LOG" + System.currentTimeMillis();
    }
    
    public String getDataHoraFormatada() {
        return dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
    
    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
    
    public String getUsuarioNome() { return usuarioNome; }
    public void setUsuarioNome(String usuarioNome) { this.usuarioNome = usuarioNome; }
    
    public String getAcao() { return acao; }
    public void setAcao(String acao) { this.acao = acao; }
    
    public String getEntidade() { return entidade; }
    public void setEntidade(String entidade) { this.entidade = entidade; }
    
    public String getEntidadeId() { return entidadeId; }
    public void setEntidadeId(String entidadeId) { this.entidadeId = entidadeId; }
    
    public String getDetalhes() { return detalhes; }
    public void setDetalhes(String detalhes) { this.detalhes = detalhes; }
    
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    
    @Override
    public String toString() {
        return String.format("[%s] %s - %s %s (ID: %s) - %s", 
                getDataHoraFormatada(), usuarioNome, acao, entidade, entidadeId, detalhes);
    }
}
