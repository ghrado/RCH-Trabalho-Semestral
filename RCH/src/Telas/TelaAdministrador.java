package Telas;

import Controller.CaixaService;
import Controller.ControleGestorService;
import Controller.RelatoriosService;
import Controller.StockService;
import Controller.VendasService;
import Model.Usuario;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import Telas.vendas.TelaVendas;
import Telas.Caixa.TelaCaixaOpcoes;
import Telas.stock.TelaStockOpcoes;
import Telas.Controle.TelaControleOpcoes;
import Telas.Cach.TelaFechoCaixa;
import Telas.Relatorios.TelaRelatorios;
import Telas.Relatorios.TelaRelatoriosMenu;

public class TelaAdministrador extends javax.swing.JFrame {
    public TelaAdministrador() {
        initComponents();
    }

    private Usuario usuarioLogado;
    private ControleGestorService controleGestorService;
    private VendasService vendasService;
    private StockService stockService;
    private CaixaService caixaService;
    private RelatoriosService relatoriosService;
    
    public TelaAdministrador(Usuario usuario, ControleGestorService controleGestorService) {
        this.usuarioLogado = usuario;
        this.controleGestorService = controleGestorService;
        this.vendasService = new VendasService();
        this.stockService = new StockService();
        this.caixaService = new CaixaService(vendasService);
        this.relatoriosService = new RelatoriosService(vendasService, stockService, caixaService, controleGestorService);
        
        initComponents();
        atualizarInformacoesUsuario();
    }
    
    private void atualizarInformacoesUsuario() {
        jLabel1.setText("Sistema Online | Usuário: " + usuarioLogado.getNome() + " (" + usuarioLogado.getId() + ")");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane2 = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        salesBtn = new javax.swing.JButton();
        managerBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        stockBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        casherBtn = new javax.swing.JButton();
        reportsBtn = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        CashClosingBtn = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Gestão de Vendas - Administrador");
        setPreferredSize(new java.awt.Dimension(700, 550));

        jDesktopPane2.setBackground(new java.awt.Color(153, 204, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Sistema Online | Usuário: ");

        jLabel6.setText("Data:  | Hora: ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel7.setText("Sales");

        salesBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Telas/IconsDB/icons8-sale-64.png"))); // NOI18N
        salesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salesBtnActionPerformed(evt);
            }
        });

        managerBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Telas/IconsDB/icons8-accounting-64.png"))); // NOI18N
        managerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                managerBtnActionPerformed(evt);
            }
        });

        jLabel2.setText("Manager");

        stockBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Telas/IconsDB/icons8-produto-64.png"))); // NOI18N
        stockBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockBtnActionPerformed(evt);
            }
        });

        jLabel3.setText("Stock");

        casherBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Telas/IconsDB/icons8-safe-in-64.png"))); // NOI18N
        casherBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                casherBtnActionPerformed(evt);
            }
        });

        reportsBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Telas/IconsDB/icons8-circle-chart-64.png"))); // NOI18N
        reportsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportsBtnActionPerformed(evt);
            }
        });

        jLabel8.setText("Casher");

        jLabel9.setText("Reports");

        CashClosingBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Telas/IconsDB/icons8-purchase-order-64.png"))); // NOI18N
        CashClosingBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CashClosingBtnActionPerformed(evt);
            }
        });

        jLabel5.setText("Cash Closing");

        jDesktopPane2.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(salesBtn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(managerBtn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(stockBtn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(casherBtn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(reportsBtn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(jLabel8, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(jLabel9, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(CashClosingBtn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane2.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane2Layout = new javax.swing.GroupLayout(jDesktopPane2);
        jDesktopPane2.setLayout(jDesktopPane2Layout);
        jDesktopPane2Layout.setHorizontalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jDesktopPane2Layout.createSequentialGroup()
                .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane2Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(managerBtn)
                            .addComponent(salesBtn))
                        .addGap(65, 65, 65)
                        .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(casherBtn)
                            .addComponent(CashClosingBtn)))
                    .addGroup(jDesktopPane2Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jLabel7)
                        .addGap(101, 101, 101)
                        .addComponent(jLabel8))
                    .addGroup(jDesktopPane2Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jLabel2)
                        .addGap(75, 75, 75)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane2Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jDesktopPane2Layout.createSequentialGroup()
                                .addGap(259, 389, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addGap(127, 127, 127))
                            .addGroup(jDesktopPane2Layout.createSequentialGroup()
                                .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(stockBtn)
                                    .addGroup(jDesktopPane2Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel3))
                                    .addComponent(reportsBtn))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jDesktopPane2Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jDesktopPane2Layout.setVerticalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane2Layout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(salesBtn)
                    .addComponent(casherBtn)
                    .addComponent(stockBtn))
                .addGap(18, 18, 18)
                .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(managerBtn, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(reportsBtn, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(CashClosingBtn, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4))
                    .addGroup(jDesktopPane2Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))))
                .addGap(191, 191, 191)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 851, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jDesktopPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void salesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salesBtnActionPerformed
        TelaVendas telaVendas = new TelaVendas(usuarioLogado, vendasService, stockService);
        telaVendas.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_salesBtnActionPerformed

    private void managerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_managerBtnActionPerformed
        TelaControleOpcoes telaControle = new TelaControleOpcoes(usuarioLogado, controleGestorService);
        telaControle.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_managerBtnActionPerformed

    private void stockBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockBtnActionPerformed
        TelaStockOpcoes telaStock = new TelaStockOpcoes(usuarioLogado, stockService);
        telaStock.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_stockBtnActionPerformed

    private void reportsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportsBtnActionPerformed
        TelaRelatorios telaRelatorio = new TelaRelatorios(usuarioLogado, relatoriosService, controleGestorService);
        telaRelatorio.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_reportsBtnActionPerformed

    private void CashClosingBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CashClosingBtnActionPerformed
        TelaFechoCaixa telaFecho = new TelaFechoCaixa(usuarioLogado, caixaService, controleGestorService);
        telaFecho.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_CashClosingBtnActionPerformed

    private void casherBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_casherBtnActionPerformed
        Telas.Caixa.TelaCaixaOpcoes telaCaixa = new Telas.Caixa.TelaCaixaOpcoes(usuarioLogado, caixaService, vendasService, controleGestorService);
        telaCaixa.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_casherBtnActionPerformed
    
    private void alterarSenha() {
        JPasswordField senhaAtual = new JPasswordField();
        JPasswordField novaSenha = new JPasswordField();
        JPasswordField confirmaSenha = new JPasswordField();
        
        Object[] message = {
            "Senha Atual:", senhaAtual,
            "Nova Senha:", novaSenha,
            "Confirmar Nova Senha:", confirmaSenha
        };
        
        int option = JOptionPane.showConfirmDialog(this, message, "Alterar Senha", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String senhaAtualStr = new String(senhaAtual.getPassword());
            String novaSenhaStr = new String(novaSenha.getPassword());
            String confirmaSenhaStr = new String(confirmaSenha.getPassword());
            
            if (usuarioLogado.autenticar(usuarioLogado.getUsuario(), senhaAtualStr)) {
                if (novaSenhaStr.equals(confirmaSenhaStr) && novaSenhaStr.length() >= 6) {
                    usuarioLogado.alterarSenha(novaSenhaStr);
                    JOptionPane.showMessageDialog(this, "Senha alterada com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(this, "Nova senha inválida ou não confere!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Senha atual incorreta!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void mostrarNotificacoes() {
        if (controleGestorService.temNotificacoes()) {
            StringBuilder sb = new StringBuilder("Notificações:\n\n");
            while (controleGestorService.temNotificacoes()) {
                sb.append("• ").append(controleGestorService.obterProximaNotificacao()).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString(), "Notificações", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Não há notificações.", "Notificações", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CashClosingBtn;
    private javax.swing.JButton casherBtn;
    private javax.swing.JDesktopPane jDesktopPane2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton managerBtn;
    private javax.swing.JButton reportsBtn;
    private javax.swing.JButton salesBtn;
    private javax.swing.JButton stockBtn;
    // End of variables declaration//GEN-END:variables
}
