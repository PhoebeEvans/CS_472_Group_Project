package model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
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
            // Explicitly load the SQLite JDBC driver class
            Class.forName("org.sqlite.JDBC");
            // Establish the connection
            conn = DriverManager.getConnection(DATABASE_URL);
        } catch (ClassNotFoundException e) {
            System.err.println("Could not load the SQLite JDBC Driver.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Connection to SQLite has failed.");
            e.printStackTrace();
        }
        return conn;
    }



    public DatabaseModel() {
    	createNewDatabase();
    }

    private void createNewDatabase() {
        // The SQLite JDBC driver will create the database file if it doesn't exist
        try (Connection conn = this.connect()) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created or already exists.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void createAccountsTable() {
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
    
    public void createTransactionsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS transactions ("
                + " transactionID integer PRIMARY KEY AUTOINCREMENT,"
                + " email text NOT NULL,"
                + " totalAmount decimal NOT NULL,"
                + " startDate text NOT NULL," // YYYY-MM-DD
                + " endDate text NOT NULL," // YYYY-MM-DD
                + " reservationID int NOT NULL"
                + ");";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("The transactions table has been created.");
        } catch (SQLException e) {
            System.out.println("Failed to create transactions table: " + e.getMessage());
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
    
    public boolean addTransaction(int reservationID, String email, double totalAmount, String startDate, String endDate) {
        String sql = "INSERT INTO transactions(email, totalAmount, startDate, endDate, reservationID) VALUES(?,?,?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setString(1, email);      
            pstmt.setDouble(2, totalAmount);  
            pstmt.setString(3, startDate);  
            pstmt.setString(4, endDate);      
            pstmt.setInt(5, reservationID);   

            int affectedRows = pstmt.executeUpdate();
            System.out.println("Added new transaction from email: " + email); //debug
            return affectedRows == 1;
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
        
        System.out.println("DB MODEL credentials check: " + email + " " + password);

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
            	System.out.println("User's first name found with email: " + email); //debug
                return resultSet.getString("firstName");
            } 
            
            System.out.println("User's first name NOT found with email: " + email); //debug
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public String getLastNameByEmail(String email) {
        String sql = "SELECT lastName FROM accounts WHERE email = ?";

        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("lastName");
            } 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }



	public Boolean getAdminStatusByEmail(String email) {
		String sql = "SELECT isAdmin FROM accounts WHERE email = ?";

        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("isAdmin");
            } 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
	}
	
	public boolean updateProfile(String email, String firstName, String lastName, String newPassword, boolean isAdmin) {
	    String sql = "UPDATE accounts SET firstName = ?, lastName = ?, password = ?, isAdmin = ? WHERE email = ?";
	    try (Connection conn = this.connect();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, firstName);
	        stmt.setString(2, lastName);
	        stmt.setString(3, newPassword.isEmpty() ? getPasswordByEmail(email) : newPassword);
	        stmt.setBoolean(4, isAdmin);
	        stmt.setString(5, email);

	        int affectedRows = stmt.executeUpdate();
	        System.out.println("while updating profile, updated admin access to: " + isAdmin); //debug
	        return affectedRows == 1;
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    return false;
	}

	private String getPasswordByEmail(String email) {
	    String sql = "SELECT password FROM accounts WHERE email = ?";
	    try (Connection conn = this.connect();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, email);
	        ResultSet resultSet = stmt.executeQuery();
	        if (resultSet.next()) {
	            return resultSet.getString("password");
	        }
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	    return null;
	}


}
