/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Telas.Controle;

import Controller.ControleGestorService;
import Model.Usuario;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author massas
 */
public class TelaGerenciarUsuario extends javax.swing.JFrame {

    private Usuario usuarioLogado;
    private ControleGestorService controleGestorService;
    private DefaultTableModel tableModel;

    /**
     * Creates new form TelaGerenciarUsuario
     */
    public TelaGerenciarUsuario(Usuario usuario, ControleGestorService controleGestorService) {
        this.usuarioLogado = usuario;
        this.controleGestorService = controleGestorService;
        initComponents();
        atualizarInformacoesUsuario();
        configurarTabela();
        carregarUsuarios();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        EditarBtn = new javax.swing.JButton();
        ApagarBtn = new javax.swing.JButton();
        ApagadosBtn = new javax.swing.JButton();
        VoltarBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        UsuarioTb = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        jLabel1.setText("Sistema Online | Usuario:");

        jLabel4.setText("Data:  | Hora: ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(143, 143, 143))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        EditarBtn.setText("Editar");
        EditarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditarBtnActionPerformed(evt);
            }
        });

        ApagarBtn.setText("Apagar");
        ApagarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApagarBtnActionPerformed(evt);
            }
        });

        ApagadosBtn.setText("Apagados");
        ApagadosBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApagadosBtnActionPerformed(evt);
            }
        });

        VoltarBtn.setText("Voltar");
        VoltarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VoltarBtnActionPerformed(evt);
            }
        });

        UsuarioTb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nome", "Tipo"
            }
        ));
        jScrollPane1.setViewportView(UsuarioTb);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ApagadosBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(VoltarBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ApagarBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(EditarBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(51, 51, 51))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(124, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(EditarBtn)
                        .addGap(42, 42, 42)
                        .addComponent(ApagarBtn)
                        .addGap(31, 31, 31)
                        .addComponent(ApagadosBtn)
                        .addGap(33, 33, 33)
                        .addComponent(VoltarBtn))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void EditarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditarBtnActionPerformed
        editarUsuario();
    }//GEN-LAST:event_EditarBtnActionPerformed

    private void ApagarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApagarBtnActionPerformed
        excluirUsuario();
    }//GEN-LAST:event_ApagarBtnActionPerformed

    private void ApagadosBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApagadosBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ApagadosBtnActionPerformed

    private void VoltarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VoltarBtnActionPerformed
        TelaControleOpcoes telaControleOpcoes = new TelaControleOpcoes(usuarioLogado, controleGestorService);
        telaControleOpcoes.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_VoltarBtnActionPerformed

    private void atualizarInformacoesUsuario() {
        jLabel1.setText("Sistema Online | Usuário: " + usuarioLogado.getNome() + " (" + usuarioLogado.getId() + ")");
    }

    private void configurarTabela() {
        String[] colunas = {"ID", "Nome", "Tipo", "Status"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int columnIndex) {
                return false;
            }
        };
        UsuarioTb.setModel(tableModel);
    }

    private void carregarUsuarios() {
        tableModel.setRowCount(0);
        List<Usuario> usuarios = controleGestorService.listarTodos();
        for (Usuario usuario : usuarios) {
            if (usuario.isAtivo()) {
                Object[] row = {
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getTipoUsuario(),
                    usuario.isAtivo() ? "Ativo" : "Inativo"
                };
                tableModel.addRow(row);
            }
        }
    }

    private void cadastrarUsuario() {
        javax.swing.JTextField idField = new javax.swing.JTextField();
        javax.swing.JTextField nomeField = new javax.swing.JTextField();
        javax.swing.JPasswordField senhaField = new javax.swing.JPasswordField();
        javax.swing.JComboBox<String> tipoCombo = new javax.swing.JComboBox<>(
            new String[]{"GERENTE", "VENDEDOR", "CAIXA"}
        );
        
        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new javax.swing.JLabel("ID:"), gbc);
        gbc.gridx = 1;
        panel.add(idField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new javax.swing.JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        panel.add(nomeField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new javax.swing.JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        panel.add(senhaField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new javax.swing.JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        panel.add(tipoCombo, gbc);
        
        int option = JOptionPane.showConfirmDialog(this, panel, "Cadastrar Usuário", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            String id = idField.getText().trim();
            String nome = nomeField.getText().trim();
            String senha = new String(senhaField.getPassword());
            String tipoStr = (String) tipoCombo.getSelectedItem();
            
            if (id.isEmpty() || nome.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Usuario.TipoUsuario tipo = Usuario.TipoUsuario.valueOf(tipoStr);
            Usuario usuario = new Usuario(id, nome, senha, tipo);
            
            if (controleGestorService.criar(usuario)) {
                controleGestorService.registrarAcaoUsuario(usuario.getId(), "CADASTRO", 
                    "Usuário cadastrado por " + usuarioLogado.getNome());
                
                JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
                carregarUsuarios();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar usuário! ID já existe.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarUsuario() {
        int selectedRow = UsuarioTb.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário na tabela!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String usuarioId = (String) tableModel.getValueAt(selectedRow, 0);
        Usuario usuario = controleGestorService.buscar(usuarioId);
        
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Usuário não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        javax.swing.JTextField nomeField = new javax.swing.JTextField(usuario.getNome());
        javax.swing.JComboBox<String> tipoCombo = new javax.swing.JComboBox<>(
            new String[]{"GERENTE", "VENDEDOR", "CAIXA"}
        );
        tipoCombo.setSelectedItem(usuario.getTipoUsuario().toString());
        
        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new javax.swing.JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        panel.add(nomeField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new javax.swing.JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        panel.add(tipoCombo, gbc);
        
        int option = JOptionPane.showConfirmDialog(this, panel, "Editar Usuário", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText().trim();
            String tipoStr = (String) tipoCombo.getSelectedItem();
            
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            usuario.setNome(nome);
            usuario.setTipoUsuario(Usuario.TipoUsuario.valueOf(tipoStr));
            
            if (controleGestorService.atualizar(usuario)) {
                JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso!");
                carregarUsuarios();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar usuário!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void excluirUsuario() {
        int selectedRow = UsuarioTb.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário na tabela!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String usuarioId = (String) tableModel.getValueAt(selectedRow, 0);
        Usuario usuario = controleGestorService.buscar(usuarioId);
        
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Usuário não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (usuario.getId().equals(usuarioLogado.getId())) {
            JOptionPane.showMessageDialog(this, "Você não pode excluir seu próprio usuário!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirmacao = JOptionPane.showConfirmDialog(this, 
            "Tem certeza que deseja excluir o usuário " + usuario.getNome() + "?", 
            "Confirmar Exclusão", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            if (controleGestorService.excluir(usuario.getId())) {
                JOptionPane.showMessageDialog(this, "Usuário excluído com sucesso!");
                carregarUsuarios();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir usuário!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ApagadosBtn;
    private javax.swing.JButton ApagarBtn;
    private javax.swing.JButton EditarBtn;
    private javax.swing.JTable UsuarioTb;
    private javax.swing.JButton VoltarBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    
}
