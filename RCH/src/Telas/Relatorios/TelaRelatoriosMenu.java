package Telas.Relatorios;
public class TelaRelatoriosMenu extends javax.swing.JFrame {
    private Model.Usuario usuario;
    private Controller.RelatoriosService relatoriosService;
    private Controller.ControleGestorService controleGestorService;

    public TelaRelatoriosMenu() { initComponents(); }
    public TelaRelatoriosMenu(Model.Usuario usuario) { this.usuario = usuario; initComponents(); }
    public TelaRelatoriosMenu(Model.Usuario usuario, Controller.RelatoriosService relatoriosService, Controller.ControleGestorService controleGestorService) {
        this.usuario = usuario;
        this.relatoriosService = relatoriosService;
        this.controleGestorService = controleGestorService;
        initComponents();
    }
    private void initComponents(){ setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); setSize(600,400); }
}
