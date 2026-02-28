public class CompteEpargne extends Compte {
    private double tauxInteret;

    public CompteEpargne(int id, String proprietaire, double solde, double tauxInteret) {
        super(id, proprietaire, solde);
        this.tauxInteret = tauxInteret;
    }

    // 9 Getters et Setters
    public double getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(double tauxInteret) {
        this.tauxInteret = tauxInteret;
    }

    // 10 Méthode calculerInterets
    public double calculerInterets() {
        return getSolde() * tauxInteret / 100;
    }

    @Override
    public String toString() {
        return super.toString() + ", tauxInteret=" + tauxInteret + "%";
    }
}