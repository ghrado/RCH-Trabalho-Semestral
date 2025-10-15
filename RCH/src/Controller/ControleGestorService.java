/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Administrador;
import Model.Caixa;
import Model.Gerente;
import Model.Interfaces.Gerenciavel;
import Model.Usuario;
import Model.Vendedor;
import Util.GeradorID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 *
 * @author anicamassas
 */
public class ControleGestorService implements Gerenciavel<Usuario> {
    private Map<String, Usuario> usuarios;
    private Vector<Usuario> usuariosVector;
    private ArrayList<Usuario> usuariosArrayList;
    private GeradorID geradorId;
    private Stack<String> notificacoes; // Stack para notificações
    
    public ControleGestorService() {
        this.usuarios = new HashMap<>();
        this.usuariosVector = new Vector<>();
        this.usuariosArrayList = new ArrayList<>();
        this.geradorId = GeradorID.getInstance();
        this.notificacoes = new Stack<>();
        
        // Criar administrador padrão
        criarAdministradorPadrao();
    }
    
    private void criarAdministradorPadrao() {
        
        // Administrador
        Administrador admin = new Administrador("Administrador", "Sistema Central", "000000000", 
                                               "840000000", "admin@rch.co.mz");
        admin.setId("Admin");
        admin.setUsuario("admin");
        usuarios.put(admin.getId(), admin);
        usuariosVector.add(admin);
        usuariosArrayList.add(admin);
        
        // Gerente - Calisa
        Gerente calisa = new Gerente("Calisa Muianga", "Av. Julius Nyerere, 1234", "110045678901A",
                                     "845551234", "calisa@rch.co.mz");
        calisa.setId("GE001");
        calisa.setUsuario("calisa.muianga");
        usuarios.put(calisa.getId(), calisa);
        usuariosVector.add(calisa);
        usuariosArrayList.add(calisa);
        
        // Caixa - Rogeria
        Caixa rogeria = new Caixa("Rogeria Sitoe", "Bairro Central, Rua 25", "110056789012B",
                                  "845552345", "rogeria@rch.co.mz");
        rogeria.setId("CX001");
        rogeria.setUsuario("rogeria.sitoe");
        usuarios.put(rogeria.getId(), rogeria);
        usuariosVector.add(rogeria);
        usuariosArrayList.add(rogeria);
        
        // Vendedor - Henrique
        Vendedor henrique = new Vendedor("Henrique Macie", "Matola, Av. da Independencia", "110067890123C",
                                         "845553456", "henrique@rch.co.mz");
        henrique.setId("VN001");
        henrique.setUsuario("henrique.macie");
        usuarios.put(henrique.getId(), henrique);
        usuariosVector.add(henrique);
        usuariosArrayList.add(henrique);
    }
    
    // Implementação da interface Gerenciavel
    @Override
    public boolean criar(Usuario usuario) {
        if (usuario == null) return false;
        
        String tipo = usuario.getTipoUsuario().getPrefixo();
        String id = geradorId.gerarID(tipo);
        usuario.setId(id);
        
        usuarios.put(id, usuario);
        usuariosVector.add(usuario);
        usuariosArrayList.add(usuario);
        return true;
    }
    
    @Override
    public Usuario buscar(String id) {
        return usuarios.get(id);
    }
    
    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuariosArrayList);
    }
    
    @Override
    public boolean atualizar(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) return false;
        
        Usuario existente = usuarios.get(usuario.getId());
        if (existente != null) {
            usuarios.put(usuario.getId(), usuario);
            
            // Atualizar no Vector
            int indexVector = usuariosVector.indexOf(existente);
            if (indexVector >= 0) {
                usuariosVector.set(indexVector, usuario);
            }
            
            // Atualizar no ArrayList
            int indexArrayList = usuariosArrayList.indexOf(existente);
            if (indexArrayList >= 0) {
                usuariosArrayList.set(indexArrayList, usuario);
            }
            
            return true;
        }
        return false;
    }
    
    @Override
    public boolean excluir(String id) {
        Usuario usuario = usuarios.get(id);
        if (usuario != null && !usuario.getId().equals("Admin")) { // Não permitir excluir admin
            usuario.setAtivo(false);
            usuariosVector.remove(usuario);
            usuariosArrayList.remove(usuario);
            
            // Adicionar ID para reutilização
            String tipo = usuario.getTipoUsuario().getPrefixo();
            geradorId.adicionarIdParaReutilizacao(tipo, id);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean reintegrar(String id) {
        Usuario usuario = usuarios.get(id);
        if (usuario != null && !usuario.isAtivo()) {
            usuario.setAtivo(true);
            usuariosVector.add(usuario);
            usuariosArrayList.add(usuario);
            
            // Remover ID da reutilização
            String tipo = usuario.getTipoUsuario().getPrefixo();
            geradorId.removerIdDeReutilizacao(tipo, id);
            return true;
        }
        return false;
    }
    
    // Métodos específicos para controle de usuários
    public Usuario criarGerente(String nome, String endereco, String bilheteIdentidade, 
                               String telefone, String email) {
        Gerente gerente = new Gerente(nome, endereco, bilheteIdentidade, telefone, email);
        if (criar(gerente)) {
            return gerente;
        }
        return null;
    }
    
    public Usuario criarVendedor(String nome, String endereco, String bilheteIdentidade, 
                                String telefone, String email) {
        Vendedor vendedor = new Vendedor(nome, endereco, bilheteIdentidade, telefone, email);
        if (criar(vendedor)) {
            return vendedor;
        }
        return null;
    }
    
    public Usuario criarCaixa(String nome, String endereco, String bilheteIdentidade, 
                             String telefone, String email) {
        Caixa caixa = new Caixa(nome, endereco, bilheteIdentidade, telefone, email);
        if (criar(caixa)) {
            return caixa;
        }
        return null;
    }
    
    // Autenticação
    public Usuario autenticar(String usuario, String senha) {
        // Buscar por usuário ou ID
        Usuario usuarioEncontrado = null;
        
        for (Usuario u : usuariosArrayList) {
            if (u.isAtivo() && (u.getUsuario().equals(usuario) || u.getId().equals(usuario))) {
                usuarioEncontrado = u;
                break;
            }
        }
        
        if (usuarioEncontrado != null && usuarioEncontrado.autenticar(usuario, senha)) {
            return usuarioEncontrado;
        }
        
        // Se falhou na autenticação e usuário existe, verificar se deve bloquear
        if (usuarioEncontrado != null && usuarioEncontrado.isContaBloqueada()) {
            adicionarNotificacao("Conta do usuário " + usuarioEncontrado.getId() + " foi bloqueada.");
        }
        
        return null;
    }
    
    public Usuario buscarUsuario(String termo) {
        // Buscar por ID primeiro
        Usuario usuario = buscar(termo);
        if (usuario != null && usuario.isAtivo()) {
            return usuario;
        }
        
        // Buscar por nome de usuário
        return usuariosArrayList.stream()
                .filter(u -> u.isAtivo() && u.getUsuario().equals(termo))
                .findFirst()
                .orElse(null);
    }
    
    // Gestão de notificações usando Stack
    public void adicionarNotificacao(String mensagem) {
        notificacoes.push(mensagem);
    }
    
    public String obterProximaNotificacao() {
        return notificacoes.isEmpty() ? null : notificacoes.pop();
    }
    
    public List<String> obterTodasNotificacoes() {
        List<String> todasNotificacoes = new ArrayList<>();
        while (!notificacoes.isEmpty()) {
            todasNotificacoes.add(notificacoes.pop());
        }
        return todasNotificacoes;
    }
    
    public boolean temNotificacoes() {
        return !notificacoes.isEmpty();
    }
    
    // Métodos para obter dados excluídos
    public List<Usuario> getUsuariosExcluidos() {
        return usuarios.values().stream()
                .filter(u -> !u.isAtivo())
                .collect(Collectors.toList());
    }
    
    public List<Usuario> getUsuariosPorTipo(Usuario.TipoUsuario tipo) {
        return usuariosArrayList.stream()
                .filter(u -> u.isAtivo() && u.getTipoUsuario() == tipo)
                .collect(Collectors.toList());
    }
    
    // Resetar senha de usuário (apenas administrador)
    public boolean resetarSenhaUsuario(String usuarioId, Usuario administrador) {
        if (administrador.getTipoUsuario() != Usuario.TipoUsuario.ADMINISTRADOR) {
            return false;
        }
        
        Usuario usuario = buscar(usuarioId);
        if (usuario != null) {
            usuario.desbloquearConta();
            return true;
        }
        return false;
    }
    
    // Método recursivo para validar hierarquia de permissões
    public boolean validarPermissaoHierarquica(Usuario usuario, String funcionalidade) {
        return validarPermissaoHierarquicaRecursivo(usuario, funcionalidade, 0);
    }
    
    private boolean validarPermissaoHierarquicaRecursivo(Usuario usuario, String funcionalidade, int nivel) {
        if (nivel > 3) { // Evitar recursão infinita
            return false;
        }
        
        // Verificar permissão direta
        if (usuario.temPermissao(funcionalidade)) {
            return true;
        }
        
        // Verificar hierarquia (Administrador > Gerente > Vendedor/Caixa)
        switch (usuario.getTipoUsuario()) {
            case ADMINISTRADOR:
                return true; // Admin tem todas as permissões
            case GERENTE:
                // Gerente pode fazer tudo exceto controle de gestor
                return !funcionalidade.equals("CONTROLE_GESTOR");
            case VENDEDOR:
            case CAIXA:
                return validarPermissaoHierarquicaRecursivo(usuario, funcionalidade, nivel + 1);
            default:
                return false;
        }
    }
    
    // Estatísticas de usuários
    public Map<Usuario.TipoUsuario, Integer> getEstatisticasUsuarios() {
        Map<Usuario.TipoUsuario, Integer> estatisticas = new HashMap<>();
        
        for (Usuario.TipoUsuario tipo : Usuario.TipoUsuario.values()) {
            estatisticas.put(tipo, 0);
        }
        
        for (Usuario usuario : usuariosArrayList) {
            if (usuario.isAtivo()) {
                Usuario.TipoUsuario tipo = usuario.getTipoUsuario();
                estatisticas.put(tipo, estatisticas.get(tipo) + 1);
            }
        }
        
        return estatisticas;
    }
    
}
