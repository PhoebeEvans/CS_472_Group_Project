package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseModel {

    private static final String DATABASE_URL = "jdbc:sqlite:HotelDB.db";

    public Connection connect() {
        Connection conn = null;
        try {
            // Establish the connection
            conn = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            System.out.println("Connection to SQLite has failed.");
            e.printStackTrace();
        }
        return conn;
    }

    public DatabaseModel() {
        createNewDatabase();
        createAccountsTable();
    }

    private void createNewDatabase() {
        try (Connection conn = this.connect()) {
            if (conn != null) {
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createAccountsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS accounts ("
                + " id integer PRIMARY KEY AUTOINCREMENT,"
                + " firstName text NOT NULL,"
                + " lastName text NOT NULL,"
                + " email text NOT NULL UNIQUE,"
                + " password text NOT NULL,"
                + " isAdmin boolean NOT NULL"
                + ");";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("The accounts table has been created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean addAccount(String firstName, String lastName, String email, String password, boolean isAdmin) {
        // Implement the logic to add an account into the database using PreparedStatement
        return false;
    }

    // Testing the DatabaseModel
    public static void main(String[] args) {
        DatabaseModel dbModel = new DatabaseModel();
        // You can test your methods here, like dbModel.addAccount(...)
    }
}
