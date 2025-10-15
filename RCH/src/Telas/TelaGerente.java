package Telas;

import Controller.CaixaService;
import Controller.ControleGestorService;
import Controller.RelatoriosService;
import Controller.StockService;
import Controller.VendasService;
import Model.Usuario;
import javax.swing.JOptionPane;
import Telas.vendas.TelaVendas;
import Telas.Caixa.TelaCaixaOpcoes;
import Telas.stock.TelaStockOpcoes;
import Telas.Cach.TelaFechoCaixa;

public class TelaGerente extends javax.swing.JFrame {
    
    private Usuario usuarioLogado;
    private ControleGestorService controleGestorService;
    private VendasService vendasService;
    private StockService stockService;
    private CaixaService caixaService;
    private RelatoriosService relatoriosService;
    
    public TelaGerente(Usuario usuario, ControleGestorService controleGestorService) {
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

        jMenuItem1 = new javax.swing.JMenuItem();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        salesBtn = new javax.swing.JButton();
        stockBtn = new javax.swing.JButton();
        casherBtn = new javax.swing.JButton();
        reportsBtn = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Gestão de Vendas - Gerente");

        salesBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Telas/IconsDB/icons8-sale-64.png"))); // NOI18N
        salesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salesBtnActionPerformed(evt);
            }
        });

        stockBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Telas/IconsDB/icons8-produto-64.png"))); // NOI18N
        stockBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockBtnActionPerformed(evt);
            }
        });

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

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Sistema Online | Usuário");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel2.setText("Sales");

        jLabel3.setText("Stock");

        jLabel4.setText("Casher");

        jLabel5.setText("Reports");

        jDesktopPane1.setLayer(salesBtn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(stockBtn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(casherBtn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(reportsBtn, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel4, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addContainerGap(81, Short.MAX_VALUE)
                        .addComponent(salesBtn)
                        .addGap(64, 64, 64)
                        .addComponent(stockBtn))
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(17, 17, 17)))
                .addGap(84, 84, 84)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(114, 114, 114)
                        .addComponent(jLabel5)
                        .addGap(93, 93, 93))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(casherBtn)
                        .addGap(82, 82, 82)
                        .addComponent(reportsBtn)
                        .addGap(83, 83, 83))))
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap(229, Short.MAX_VALUE)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(reportsBtn)
                    .addComponent(casherBtn)
                    .addComponent(stockBtn)
                    .addComponent(salesBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(181, 181, 181)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void salesBtnActionPerformed(java.awt.event.ActionEvent evt) {
        TelaVendas telaVendas = new TelaVendas(usuarioLogado, vendasService, stockService);
        telaVendas.setVisible(true);
        this.dispose();
    }

    private void stockBtnActionPerformed(java.awt.event.ActionEvent evt) {
        TelaStockOpcoes telaStock = new TelaStockOpcoes(usuarioLogado, stockService);
        telaStock.setVisible(true);
        this.dispose();
    }
    
    private void casherBtnActionPerformed(java.awt.event.ActionEvent evt) {
        TelaCaixaOpcoes telaCaixa = new TelaCaixaOpcoes(usuarioLogado, caixaService, vendasService);
        telaCaixa.setVisible(true);
        this.dispose();
    }

    private void reportsBtnActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO: Implementar tela de relatórios
        JOptionPane.showMessageDialog(this, "Módulo de Relatórios em desenvolvimento", 
                                    "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    private void fechoCaixaBtnActionPerformed(java.awt.event.ActionEvent evt) {
        TelaFechoCaixa telaFechoCaixa = new TelaFechoCaixa(usuarioLogado, caixaService);
        telaFechoCaixa.setVisible(true);
        this.dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton casherBtn;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton reportsBtn;
    private javax.swing.JButton salesBtn;
    private javax.swing.JButton stockBtn;
    // End of variables declaration//GEN-END:variables
}
