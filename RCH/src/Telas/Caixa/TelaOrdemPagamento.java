/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Telas.Caixa;

import Controller.CaixaService;
import Controller.VendasService;
import Model.Ordem;
import Model.Usuario;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author anicamassas
 */
public class TelaOrdemPagamento extends javax.swing.JFrame {
    public TelaOrdemPagamento(Model.Usuario usuario, Controller.CaixaService caixaService, Controller.VendasService vendasService, Controller.ControleGestorService controleGestorService) {
        this.usuario = usuario;
        this.caixaService = caixaService;
        this.vendasService = vendasService;
        this.controleGestorService = controleGestorService;
        initComponents();
    }


    private Usuario usuarioLogado;
    private CaixaService caixaService;
    private VendasService vendasService;
    private DefaultTableModel tableModel;

    /**
     * Creates new form TelaOrdemPagamento
     */
    public TelaOrdemPagamento(Usuario usuario, CaixaService caixaService, VendasService vendasService) {
        this.usuarioLogado = usuario;
        this.caixaService = caixaService;
        this.vendasService = vendasService;
        initComponents();
        atualizarInformacoesUsuario();
        configurarTabela();
        carregarOrdensNaoProcessadas();
    } // Added missing closing brace for constructor

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        BuscarOrdemBtn = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        FacturarBtn = new javax.swing.JButton();
        CancelarBtn = new javax.swing.JButton();
        VoltarBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        jLabel1.setText("Sistema Online | Usuario:");

        jLabel4.setText("Data:  | Hora: ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(168, 168, 168))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        BuscarOrdemBtn.setText("Buscar Ordem");
        BuscarOrdemBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarOrdemBtnActionPerformed(evt);
            }
        });

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        FacturarBtn.setText("Facturar");
        FacturarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FacturarBtnActionPerformed(evt);
            }
        });

        CancelarBtn.setText("Cancelar");
        CancelarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelarBtnActionPerformed(evt);
            }
        });

        VoltarBtn.setText("Voltar");
        VoltarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VoltarBtnActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome do Cliente", "Tipo Cliente", "Hora da Ordem", "preço"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(26, 26, 26)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(CancelarBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                                .addComponent(VoltarBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(FacturarBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(BuscarOrdemBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BuscarOrdemBtn)
                        .addGap(26, 26, 26)
                        .addComponent(FacturarBtn)
                        .addGap(27, 27, 27)
                        .addComponent(CancelarBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(VoltarBtn))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(88, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(66, 66, 66)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BuscarOrdemBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarOrdemBtnActionPerformed
        buscarOrdem();
    }//GEN-LAST:event_BuscarOrdemBtnActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void FacturarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FacturarBtnActionPerformed
        processarPagamento();
    }//GEN-LAST:event_FacturarBtnActionPerformed

    private void CancelarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelarBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CancelarBtnActionPerformed

    private void VoltarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VoltarBtnActionPerformed
        TelaCaixaOpcoes telaCaixaOpcoes = new TelaCaixaOpcoes(usuarioLogado, caixaService, vendasService);
        telaCaixaOpcoes.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_VoltarBtnActionPerformed

    private void atualizarInformacoesUsuario() {
        jLabel1.setText("Sistema Online | Usuário: " + usuarioLogado.getNome() + " (" + usuarioLogado.getId() + ")");
    }

    private void configurarTabela() {
        String[] colunas = {"Código", "Cliente", "Data", "Total (MT)", "Status"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable2.setModel(tableModel); // Fixed table reference from jTable1 to jTable2
    }

    private void carregarOrdensNaoProcessadas() {
        tableModel.setRowCount(0);
        List<Ordem> ordens = vendasService.getOrdensNaoProcessadas();
        
        for (Ordem ordem : ordens) {
            Object[] row = {
                ordem.getCodigo(),
                ordem.getClienteNome(),
                ordem.getDataCriacao().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                String.format("%.2f", ordem.getValorTotal()),
                ordem.getStatus()
            };
            tableModel.addRow(row);
        }
    }

    private void buscarOrdem() {
        String codigo = jTextField1.getText().trim();
        
        if (codigo.isEmpty()) {
            carregarOrdensNaoProcessadas();
            return;
        }
        
        Ordem ordem = vendasService.buscarOrdem(codigo);
        
        if (ordem != null) {
            tableModel.setRowCount(0);
            Object[] row = {
                ordem.getCodigo(),
                ordem.getClienteNome(),
                ordem.getDataCriacao().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                String.format("%.2f", ordem.getValorTotal()),
                ordem.getStatus()
            };
            tableModel.addRow(row);
        } else {
            JOptionPane.showMessageDialog(this, "Ordem não encontrada!", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void processarPagamento() {
        int selectedRow = jTable2.getSelectedRow(); // Fixed table reference from jTable1 to jTable2
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma ordem na tabela!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String codigoOrdem = (String) tableModel.getValueAt(selectedRow, 0);
        Ordem ordem = vendasService.buscarOrdem(codigoOrdem);
        
        if (ordem == null) {
            JOptionPane.showMessageDialog(this, "Ordem não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (ordem.getStatus() == Ordem.StatusOrdem.PAGO) {
            JOptionPane.showMessageDialog(this, "Esta ordem já foi paga!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            boolean sucesso = vendasService.processarPagamento(ordem.getCodigo());
            
            if (sucesso) {
                JOptionPane.showMessageDialog(this, 
                    "Pagamento processado com sucesso!\nOrdem: " + ordem.getCodigo() + 
                    "\nValor: " + String.format("%.2f MT", ordem.getValorTotal()), 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
                carregarOrdensNaoProcessadas();
                jTextField1.setText("");
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Erro ao processar pagamento!", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao processar pagamento: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BuscarOrdemBtn;
    private javax.swing.JButton CancelarBtn;
    private javax.swing.JButton FacturarBtn;
    private javax.swing.JButton VoltarBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
