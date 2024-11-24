import java.io.*;
import java.util.*;

public class HistoriqueCalculs {
    private List<String> historique;

    public HistoriqueCalculs() {
        historique = new ArrayList<>();
    }

    // Ajouter un calcul à l'historique
    public void ajouterCalcul(String adresse, String classeNom) {
        historique.add("Adresse: " + adresse + " | Classe: " + classeNom);
        enregistrerHistorique();
    }

    // Sauvegarder l'historique dans un fichier texte
    private void enregistrerHistorique() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("historique.txt", true))) {
            for (String entry : historique) {
                writer.write(entry);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Récupérer l'historique à partir du fichier texte
    public String getHistorique() {
        StringBuilder historiqueTexte = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("historique.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                historiqueTexte.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return historiqueTexte.toString();
    }
}
