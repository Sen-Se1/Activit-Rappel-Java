import java.sql.*;

public class SetupDatabase {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/banque";
        String usr = "postgres";
        String pwd = "root";

        String dropComptes = "DROP TABLE IF EXISTS comptes CASCADE;";
        String dropBanque = "DROP TABLE IF EXISTS devopsbanque CASCADE;";

        String createBanqueTable = "CREATE TABLE devopsbanque (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL" +
                ");";

        String createComptesTable = "CREATE TABLE comptes (" +
                "id SERIAL PRIMARY KEY, " +
                "proprietaire VARCHAR(100) NOT NULL, " +
                "solde DECIMAL(15, 2) DEFAULT 0.0, " +
                "banque_id INT REFERENCES devopsbanque(id) ON DELETE CASCADE" +
                ");";

        String insertBanques = "INSERT INTO devopsbanque(name) VALUES ('Banque A'), ('Banque B');";

        String insertComptes = "INSERT INTO comptes(proprietaire, solde, banque_id) " +
                "VALUES ('Houssem', 1200, 1), ('Mariem', 400, 2), ('Maram', 1200, 1), ('Ranim', 400, 2);";

        try (Connection conn = DriverManager.getConnection(url, usr, pwd);
             Statement stmt = conn.createStatement()) {

            System.out.println("Initialisation de la base de données...");

            stmt.executeUpdate(dropComptes);
            stmt.executeUpdate(dropBanque);
            System.out.println("- Tables supprimées (si existantes).");

            stmt.executeUpdate(createBanqueTable);
            stmt.executeUpdate(createComptesTable);
            System.out.println("- Tables créées.");

            stmt.executeUpdate(insertBanques);
            System.out.println("- Banques insérées.");

            stmt.executeUpdate(insertComptes);
            System.out.println("- Comptes insérés.");

            System.out.println("\nBase de données configurée avec succès !");

        } catch (SQLException e) {
            System.err.println("Erreur JDBC : " + e.getMessage());
        }
    }
}
