import java.sql.*;

public class TestCompte {

    // Retrieve all comptes
    static void getCompteFromDB() {

        String url = "jdbc:postgresql://localhost:5432/banque";
        String usr = "postgres";
        String pwd = "root";

        try (Connection c = DriverManager.getConnection(url, usr, pwd);
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT id, proprietaire, solde FROM comptes")) {

            System.out.println("\nListe des comptes :");

            while (rs.next()) {
                System.out.println("ID = " + rs.getInt("id") +
                        " | Propriétaire = " + rs.getString("proprietaire") +
                        " | Solde = " + rs.getDouble("solde"));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    // Retrieve comptes with their bank name
    static void getComptesWithBanque() {

        String url = "jdbc:postgresql://localhost:5432/banque";
        String usr = "postgres";
        String pwd = "root";

        String query =
                "SELECT c.id, c.proprietaire, c.solde, b.name AS banque_name " +
                "FROM comptes c " +
                "JOIN devopsbanque b ON c.banque_id = b.id";

        try (Connection c = DriverManager.getConnection(url, usr, pwd);
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\nComptes avec leurs banques :");

            while (rs.next()) {
                System.out.println("Compte ID = " + rs.getInt("id") +
                        " | Propriétaire = " + rs.getString("proprietaire") +
                        " | Solde = " + rs.getDouble("solde") +
                        " | Banque = " + rs.getString("banque_name"));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    // Calculate real abonnés using COUNT()
    static void getBanquesWithAbonnes() {

        String url = "jdbc:postgresql://localhost:5432/banque";
        String usr = "postgres";
        String pwd = "root";

        String query =
                "SELECT b.id, b.name, COUNT(c.id) AS nb_abonnes " +
                "FROM devopsbanque b " +
                "LEFT JOIN comptes c ON b.id = c.banque_id " +
                "GROUP BY b.id, b.name " +
                "ORDER BY b.id";

        try (Connection c = DriverManager.getConnection(url, usr, pwd);
             Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\nBanques avec nombre réel d'abonnés :");

            while (rs.next()) {
                System.out.println("Banque ID = " + rs.getInt("id") +
                        " | Nom = " + rs.getString("name") +
                        " | Abonnés = " + rs.getInt("nb_abonnes"));
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    static void displayBanquesHierarchy() {

        String url = "jdbc:postgresql://localhost:5432/banque";
        String usr = "postgres";
        String pwd = "root";

        String query =
                "SELECT b.name AS banque_name, c.proprietaire, c.solde " +
                "FROM devopsbanque b " +
                "LEFT JOIN comptes c ON b.id = c.banque_id " +
                "ORDER BY b.id";

        try (Connection conn = DriverManager.getConnection(url, usr, pwd);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("\n===== BANQUES & LEURS COMPTES =====");

            String currentBanque = "";

            while (rs.next()) {

                String banque = rs.getString("banque_name");
                String proprietaire = rs.getString("proprietaire");
                double solde = rs.getDouble("solde");

                // When bank changes → print new bank header
                if (!banque.equals(currentBanque)) {
                    currentBanque = banque;
                    System.out.println("\n" + banque);
                }

                // If there is a compte attached
                if (proprietaire != null) {
                    System.out.println("   - " + proprietaire + " (" + solde + ")");
                } else {
                    System.out.println("   - Aucun compte");
                }
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

        Compte compte1 = new Compte(1, "Alice", 1000.0);
        Compte compte2 = new Compte(2, "Bob", 500.0);

        System.out.println("Compte 1 : " + compte1);
        System.out.println("Compte 2 : " + compte2);

        try {
            compte1.deposer(200.0);
            compte2.retirer(100.0);

            System.out.println("\nAprès opérations :");
            System.out.println(compte1);
            System.out.println(compte2);

            CompteEpargne compteEpargne =
                    new CompteEpargne(3, "Charlie", 1500.0, 5.0);

            System.out.println("\nCompte épargne : " + compteEpargne);
            compteEpargne.calculerInterets();
            System.out.println("Après calcul des intérêts : " + compteEpargne);

        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        System.out.println("\n---------------- DATABASE ----------------");

        getCompteFromDB();
        getComptesWithBanque();
        getBanquesWithAbonnes();   
        displayBanquesHierarchy();
    }
}