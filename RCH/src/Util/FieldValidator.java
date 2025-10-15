package Util;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

/**
 * Classe utilitária para validação de campos em tempo real
 * Implementa os eventos obrigatórios: FocusGained, KeyPressed, KeyReleased, 
 * MouseClicked, MouseEntered, MouseExited, MousePressed, MouseMotion
 */
public class FieldValidator {
    public enum ValidationType { NOT_EMPTY, EMAIL, NUMBER }

    
    // Cores para feedback visual
    private static final Color COLOR_ERROR = new Color(255, 200, 200);
    private static final Color COLOR_SUCCESS = new Color(200, 255, 200);
    private static final Color COLOR_NORMAL = Color.WHITE;
    private static final Color BORDER_ERROR = new Color(255, 0, 0);
    private static final Color BORDER_SUCCESS = new Color(0, 200, 0);
    
    // Padrões de validação
    private static final Pattern PATTERN_EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PATTERN_TELEFONE = Pattern.compile("^[0-9]{9}$");
    private static final Pattern PATTERN_BI = Pattern.compile("^[0-9]{12}[A-Z]$");
    private static final Pattern PATTERN_NUIT = Pattern.compile("^[0-9]{9}$");
    
    /**
     * Adiciona validação obrigatória a um campo de texto
     */
    public static void addRequiredValidation(JTextField field, String fieldName) {
        addFocusValidation(field, () -> {
            String text = field.getText().trim();
            if (text.isEmpty()) {
                showError(field, fieldName + " é obrigatório");
                return false;
            }
            showSuccess(field);
            return true;
        });
    }
    
    /**
     * Adiciona validação de email
     */
    public static void addEmailValidation(JTextField field) {
        addFocusValidation(field, () -> {
            String text = field.getText().trim();
            if (text.isEmpty()) {
                showError(field, "Email é obrigatório");
                return false;
            }
            if (!PATTERN_EMAIL.matcher(text).matches()) {
                showError(field, "Email inválido");
                return false;
            }
            showSuccess(field);
            return true;
        });
    }
    
    /**
     * Adiciona validação de telefone (9 dígitos)
     */
    public static void addTelefoneValidation(JTextField field) {
        addFocusValidation(field, () -> {
            String text = field.getText().trim();
            if (text.isEmpty()) {
                showError(field, "Telefone é obrigatório");
                return false;
            }
            if (!PATTERN_TELEFONE.matcher(text).matches()) {
                showError(field, "Telefone deve ter 9 dígitos");
                return false;
            }
            showSuccess(field);
            return true;
        });
        
        // Limitar entrada apenas a números
        addNumericOnly(field, 9);
    }
    
    /**
     * Adiciona validação de BI (12 dígitos + 1 letra)
     */
    public static void addBIValidation(JTextField field) {
        addFocusValidation(field, () -> {
            String text = field.getText().trim().toUpperCase();
            if (text.isEmpty()) {
                showError(field, "BI é obrigatório");
                return false;
            }
            if (!PATTERN_BI.matcher(text).matches()) {
                showError(field, "BI inválido (12 dígitos + 1 letra)");
                return false;
            }
            showSuccess(field);
            return true;
        });
    }
    
    /**
     * Adiciona validação de NUIT (9 dígitos)
     */
    public static void addNUITValidation(JTextField field) {
        addFocusValidation(field, () -> {
            String text = field.getText().trim();
            if (text.isEmpty()) {
                showError(field, "NUIT é obrigatório");
                return false;
            }
            if (!PATTERN_NUIT.matcher(text).matches()) {
                showError(field, "NUIT deve ter 9 dígitos");
                return false;
            }
            showSuccess(field);
            return true;
        });
        
        addNumericOnly(field, 9);
    }
    
    /**
     * Adiciona validação de senha (mínimo 6 caracteres)
     */
    public static void addPasswordValidation(JPasswordField field) {
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String password = new String(field.getPassword());
                if (password.length() < 6) {
                    showError(field, "Senha deve ter no mínimo 6 caracteres");
                } else {
                    showSuccess(field);
                }
            }
            
            @Override
            public void focusGained(FocusEvent e) {
                field.setBackground(COLOR_NORMAL);
            }
        });
        
        addAllMouseEvents(field);
    }
    
    /**
     * Adiciona validação personalizada com FocusListener
     */
    private static void addFocusValidation(JTextField field, ValidationFunction validator) {
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validator.validate();
            }
            
            @Override
            public void focusGained(FocusEvent e) {
                field.setBackground(COLOR_NORMAL);
                field.setBorder(UIManager.getBorder("TextField.border"));
            }
        });
        
        // Adicionar eventos de teclado
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Limpar erro ao começar a digitar
                if (field.getBackground().equals(COLOR_ERROR)) {
                    field.setBackground(COLOR_NORMAL);
                    field.setBorder(UIManager.getBorder("TextField.border"));
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                // Validação em tempo real (opcional)
            }
        });
        
        addAllMouseEvents(field);
    }
    
    /**
     * Adiciona todos os eventos de mouse obrigatórios
     */
    private static void addAllMouseEvents(JComponent component) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Evento MouseClicked
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                // Evento MouseEntered - pode adicionar efeito hover
                component.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                // Evento MouseExited
                component.setCursor(Cursor.getDefaultCursor());
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                // Evento MousePressed
            }
        });
        
        component.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // Evento MouseMotion
            }
        });
    }
    
    /**
     * Limita campo para aceitar apenas números
     */
    private static void addNumericOnly(JTextField field, int maxLength) {
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
                if (field.getText().length() >= maxLength && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });
    }
    
    /**
     * Mostra erro visual no campo
     */
    private static void showError(JComponent field, String message) {
        field.setBackground(COLOR_ERROR);
        field.setBorder(BorderFactory.createLineBorder(BORDER_ERROR, 2));
        field.setToolTipText(message);
    }
    
    /**
     * Mostra sucesso visual no campo
     */
    private static void showSuccess(JComponent field) {
        field.setBackground(COLOR_SUCCESS);
        field.setBorder(BorderFactory.createLineBorder(BORDER_SUCCESS, 1));
        field.setToolTipText(null);
        
        // Voltar ao normal após 1 segundo
        Timer timer = new Timer(1000, e -> {
            field.setBackground(COLOR_NORMAL);
            field.setBorder(UIManager.getBorder("TextField.border"));
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    /**
     * Valida todos os campos de um formulário
     */
    public static boolean validateAllFields(Container container) {
        boolean allValid = true;
        
        for (Component comp : container.getComponents()) {
            if (comp instanceof JTextField) {
                JTextField field = (JTextField) comp;
                if (field.getBackground().equals(COLOR_ERROR)) {
                    allValid = false;
                }
            } else if (comp instanceof Container) {
                if (!validateAllFields((Container) comp)) {
                    allValid = false;
                }
            }
        }
        
        return allValid;
    }
    
    @FunctionalInterface
    private interface ValidationFunction {
        boolean validate();
    }

    public void addRealTimeValidation(final javax.swing.JTextPane field, final String fieldName, final ValidationType type) {
        if (field == null) return;
        field.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void validate() {
                String text = field.getText();
                boolean ok = true;
                switch (type) {
                    case EMAIL:
                        ok = text != null && text.matches("^\S+@\S+\.\S+$");
                        break;
                    case NUMBER:
                        ok = text != null && text.matches("^[0-9]+(\.[0-9]+)?$");
                        break;
                    default:
                        ok = text != null && !text.trim().isEmpty();
                }
                field.setBackground(ok ? java.awt.Color.WHITE : java.awt.Color.PINK);
            }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { validate(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { validate(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { validate(); }
        });
    }

}
