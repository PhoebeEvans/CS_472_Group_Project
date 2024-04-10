package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        String sql = "INSERT INTO accounts(firstName, lastName, email, password, isAdmin) VALUES(?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            // Set the parameters for the prepared statement
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, password);
            statement.setBoolean(5, isAdmin);

            int affectedRows = statement.executeUpdate();
            return affectedRows == 1; // Return true if one row was inserted
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public static void main(String[] args) {
        DatabaseModel dbModel = new DatabaseModel();
        
        //for testung, add an account
        boolean isAdded = dbModel.addAccount("John", "Doe", "johndoe@gmail.com", "password", false);
        if (isAdded) {
            System.out.println("Account successfully added.");
        } else {
            System.out.println("Failed to add account.");
        }
    }

    public boolean checkCredentials(String email, String password) {
        String sql = "SELECT id FROM accounts WHERE email = ? AND password = ?";

        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, email);
            statement.setString(2, password);

            return statement.executeQuery().next(); // If there is a result, credentials are valid
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public String getFirstNameByEmail(String email) {
        String sql = "SELECT firstName FROM accounts WHERE email = ?";

        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("firstName");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
