
public class Compte {

    private int id;
    private String proprietaire;
    private double solde;

    // 2 Constructeur avec paramètres
    public Compte(int id, String proprietaire, double solde) {
        this.id = id;
        this.proprietaire = proprietaire;
        this.solde = solde;
    }

    // 3 Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(String proprietaire) {
        this.proprietaire = proprietaire;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    // 4 Méthode deposer
    public void deposer(double montant) throws MontantNonValideException {
        if (montant <= 0) {
            throw new MontantNonValideException("Montant à déposer non valide !");
        }
        solde += montant;
    }

    // 5 Méthode retirer
    public void retirer(double montant) throws MontantNonValideException {
        if (montant <= 0) {
            throw new MontantNonValideException("Montant à retirer non valide !");
        }
        if (montant > solde) {
            throw new MontantNonValideException("Solde insuffisant !");
        }
        solde -= montant;
    }

    // 6 Méthode toString
    @Override
    public String toString() {
        return "Compte{"
                + "id=" + id
                + ", proprietaire='" + proprietaire + '\''
                + ", solde=" + solde
                + '}';
    }
}
