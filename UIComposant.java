import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class UIComposant extends JFrame {
    protected JTextField adresseField;
    protected JTextField masqueField;
    protected JTextArea resultArea;
    protected JButton calculerButton;

    // Méthodes abstraites
    public abstract void initialiserUI();
    public abstract void mettreAJourResultats(String result);
}

