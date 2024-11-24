import java.util.regex.*;

public class AdresseIP {
    protected int[] octets = new int[4];
    protected String classe;

    // Validation de l'adresse IP
    public boolean validerAdresse(String ip) {
        String regex = "^([0-9]{1,3}\\.){3}[0-9]{1,3}$"; // Expression régulière pour l'IP
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ip);
        if (matcher.matches()) {
            String[] parts = ip.split("\\.");
            for (int i = 0; i < 4; i++) {
                octets[i] = Integer.parseInt(parts[i]);
                if (octets[i] < 0 || octets[i] > 255) {
                    return false; // Les octets doivent être entre 0 et 255
                }
            }
            return true;
        }
        return false;
    }

    // Déterminer la classe de l'adresse IP
    public void getClasse() {
        if (octets[0] >= 1 && octets[0] <= 127) {
            classe = "Classe A";
        } else if (octets[0] >= 128 && octets[0] <= 191) {
            classe = "Classe B";
        } else if (octets[0] >= 192 && octets[0] <= 223) {
            classe = "Classe C";
        } else {
            classe = "Adresse privée ou non valide";
        }
    }

    public String getClasseNom() {
        return classe;
    }
 
}
