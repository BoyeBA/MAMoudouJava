public class Reseau {
    private String masque;
    private String adresseDebut;
    private String adresseFin;
    private String classeNom;

    // Méthode pour valider l'adresse IP
    public boolean validerAdresse(String adresse) {
        String[] octets = adresse.split("\\.");
        if (octets.length == 4) {
            for (String octet : octets) {
                try {
                    int valeur = Integer.parseInt(octet);
                    if (valeur < 0 || valeur > 255) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // Calculer l'adresse réseau, l'adresse de diffusion et la plage d'adresses
    public void calculerPlageAdresse(String adresseIP, String masque) {
        // Convertir l'adresse IP et le masque en format binaire
        long adresseLong = ipToLong(adresseIP);
        long masqueLong = ipToLong(masque);

        // L'adresse réseau est obtenue en faisant un ET bit à bit entre l'adresse IP et le masque
        long adresseReseau = adresseLong & masqueLong;

        // L'adresse de diffusion est obtenue en inversant le masque et en faisant un OU bit à bit
        long adresseDiffusion = adresseReseau | ~masqueLong;

        // Convertir ces valeurs en adresses IP
        adresseDebut = longToIp(adresseReseau);  // Adresse réseau
        adresseFin = longToIp(adresseDiffusion); // Adresse de diffusion

        // Calcul de la classe de l'adresse IP
        classeNom = calculerClasse(adresseIP);
    }

    // Convertir une adresse IP (sous forme de chaîne) en un long
    private long ipToLong(String ip) {
        String[] octets = ip.split("\\.");
        long resultat = 0;
        for (int i = 0; i < octets.length; i++) {
            int octet = Integer.parseInt(octets[i]);
            resultat |= (long) octet << (24 - (8 * i));
        }
        return resultat;
    }

    // Convertir un long en adresse IP (sous forme de chaîne)
    private String longToIp(long longIp) {
        return ((longIp >> 24) & 0xFF) + "." + ((longIp >> 16) & 0xFF) + "." +
               ((longIp >> 8) & 0xFF) + "." + (longIp & 0xFF);
    }

    // Calculer la classe de l'adresse IP
    private String calculerClasse(String adresse) {
        String[] octets = adresse.split("\\.");
        int premierOctet = Integer.parseInt(octets[0]);

        if (premierOctet >= 1 && premierOctet <= 126) {
            return "Classe A";
        } else if (premierOctet >= 128 && premierOctet <= 191) {
            return "Classe B";
        } else if (premierOctet >= 192 && premierOctet <= 223) {
            return "Classe C";
        } else {
            return "Classe Inconnue";
        }
    }

    public String getClasseNom() {
        return classeNom;
    }

    public String getPlage() {
        return "De " + adresseDebut + " à " + adresseFin;
    }
}
