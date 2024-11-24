import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class main extends JFrame {
    private JTextField adresseField;
    private JTextField masqueField;
    private JTextArea resultArea;
    private JButton calculerButton;
    private Reseau reseau;
    private HistoriqueCalculs historique;

    public main() {
        reseau = new Reseau();
        historique = new HistoriqueCalculs();
        initialiserUI();
    }

    public void initialiserUI() {
        setTitle("Calculateur d'Adresse Réseau");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 248, 255)); // Couleur bleu clair

        // Configuration des champs de texte
        adresseField = new JTextField(20);
        masqueField = new JTextField(20);
        configurerChamp(adresseField);
        configurerChamp(masqueField);

        // Zone de résultats stylisée
        resultArea = new JTextArea(12, 35);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        resultArea.setBackground(new Color(250, 250, 250));
        resultArea.setForeground(Color.DARK_GRAY);
        resultArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 149, 237), 2), // Bleu cornflower
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        JScrollPane scroll = new JScrollPane(resultArea);

        // Bouton stylisé
        calculerButton = new JButton("Calculer");
        calculerButton.setBackground(new Color(100, 149, 237));
        calculerButton.setForeground(Color.WHITE);
        calculerButton.setFont(new Font("Arial", Font.BOLD, 16));
        calculerButton.setFocusPainted(false);
        calculerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        calculerButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Panneau pour les champs de saisie
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(240, 248, 255));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Adresse IP : "), gbc);

        gbc.gridx = 1;
        inputPanel.add(adresseField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Masque : "), gbc);

        gbc.gridx = 1;
        inputPanel.add(masqueField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(calculerButton, gbc);

        // Panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scroll, BorderLayout.CENTER);

        add(mainPanel);

        // Action au clic
        calculerButton.addActionListener(e -> {
            String adresse = adresseField.getText();
            String masque = masqueField.getText();

            if (!reseau.validerAdresse(adresse)) {
                mettreAJourResultats("Adresse IP invalide !");
                return;
            }

            if (!validerMasque(masque)) {
                mettreAJourResultats("Masque de sous-réseau invalide !");
                return;
            }

            reseau.calculerPlageAdresse(adresse, masque);
            historique.ajouterCalcul(adresse, reseau.getClasseNom());
            String result = "Classe de l'adresse : " + reseau.getClasseNom() + "\n" +
                    "Plage d'adresses disponibles : " + reseau.getPlage();
            mettreAJourResultats(result);
        });
    }

    private void configurerChamp(JTextField champ) {
        champ.setFont(new Font("Arial", Font.PLAIN, 14));
        champ.setBackground(Color.WHITE);
        champ.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(173, 216, 230), 2), // Bleu léger
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    public void mettreAJourResultats(String result) {
        resultArea.setText(result);
    }

    public boolean validerMasque(String masque) {
        String regex = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        return masque.matches(regex);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            main app = new main();
            app.setVisible(true);
        });
    }
}
