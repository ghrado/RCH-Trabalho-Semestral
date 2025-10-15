/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import Model.AuditLog;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author massas
 * 
 * Serviço para gerenciar logs de auditoria
 */
public class AuditService {
    private static AuditService instance;
    private List<AuditLog> logs;
    
    private AuditService() {
        this.logs = new ArrayList<>();
    }
    
    public static synchronized AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }
    
    /**
     * Registra uma ação no sistema
     */
    public void registrarAcao(String usuarioId, String usuarioNome, String acao,
                             String entidade, String entidadeId, String detalhes) {
        AuditLog log = new AuditLog(usuarioId, usuarioNome, acao, entidade, entidadeId, detalhes);
        logs.add(log);
    }
    
    /**
     * Obtém todos os logs
     */
    public List<AuditLog> getTodosLogs() {
        return new ArrayList<>(logs);
    }
    
    /**
     * Obtém logs por usuário
     */
    public List<AuditLog> getLogsPorUsuario(String usuarioId) {
        return logs.stream()
                .filter(log -> log.getUsuarioId().equals(usuarioId))
                .collect(Collectors.toList());
    }
    
    /**
     * Obtém logs por entidade
     */
    public List<AuditLog> getLogsPorEntidade(String entidade) {
        return logs.stream()
                .filter(log -> log.getEntidade().equals(entidade))
                .collect(Collectors.toList());
    }
    
    /**
     * Obtém logs por entidade e ID
     */
    public List<AuditLog> getLogsPorEntidadeEId(String entidade, String entidadeId) {
        return logs.stream()
                .filter(log -> log.getEntidade().equals(entidade) && 
                             log.getEntidadeId().equals(entidadeId))
                .collect(Collectors.toList());
    }
    
    /**
     * Obtém logs por ação
     */
    public List<AuditLog> getLogsPorAcao(String acao) {
        return logs.stream()
                .filter(log -> log.getAcao().equals(acao))
                .collect(Collectors.toList());
    }
}
