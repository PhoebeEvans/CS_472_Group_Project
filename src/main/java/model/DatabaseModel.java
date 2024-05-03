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
    
    public void createReservationsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS reservations ("
                + " reservationID integer PRIMARY KEY AUTOINCREMENT,"
                + " status text NOT NULL,"
                + " startDate text NOT NULL," // YYYY-MM-DD
                + " endDate text NOT NULL," // YYYY-MM-DD
                + " guest text NOT NULL,"
                + " roomNumber integer NOT NULL,"
                + " specialRequest text"
                + ");";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("reservation table created.");
            
            if (countReservations() < 2) {  // Assuming you want exactly two dummy reservations
                addReservation("2024-04-01", "2024-04-03", "johndoe@gmail.com", 101, "Near the pool", "FULFILLED");
                addReservation("2024-05-15", "2024-05-20", "janedoe@gmail.com", 102, "Away from the elevator", "PENDING");
                System.out.println("Dummy reservations successfully added.");
            } else {
                System.out.println("Dummy reservations already present.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to create reservations table: " + e.getMessage());
        }
    }
    
    public void createRoomsTable() {
        String sql = "CREATE TABLE IF NOT EXISTS rooms ("
                + " roomNumber INTEGER PRIMARY KEY,"
                + " bedNumber INTEGER NOT NULL,"
                + " bedSize TEXT NOT NULL,"
                + " hasBalcony BOOLEAN NOT NULL,"
                + " nonsmoking BOOLEAN NOT NULL"
                + ");";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("The rooms table has been created.");
            
	         // Check if room 101 and 102 already exist
            if (!roomExists(101)) {
                addRoom(101, 2, "Queen", true, true);
            }
            if (!roomExists(102)) {
                addRoom(102, 1, "King", false, true);
            }
        } catch (SQLException e) {
            System.out.println("Failed to create rooms table: " + e.getMessage());
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
            
            if (countTransactions() < 2) {  //if less than 2, then dummy values have not been added yet
                // Add dummy transactions
                addTransaction(1, "johndoe@gmail.com", 200.00, "2024-04-01", "2024-04-03");
                boolean isAdded = addTransaction(2, "janedoe@gmail.com", 450.50, "2024-05-15", "2024-05-20");
                
                if (isAdded) {
                    System.out.println("Dummy transactions successfully added.");
                } else {
                    System.out.println("Dummy transactions failed to add.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to create transactions table: " + e.getMessage());
        }
        
    }
    
    private int countTransactions() {
        String sql = "SELECT COUNT(*) AS total FROM transactions";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("cnt transactions err: " + e.getMessage());
        }
        return 0;
    }
    
    private int countReservations() {
        String sql = "SELECT COUNT(*) AS total FROM reservations";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Error counting reservations: " + e.getMessage());
        }
        return 0;
    }
    
    private boolean roomExists(int roomNumber) {
        String sql = "SELECT COUNT(*) FROM rooms WHERE roomNumber = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, roomNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to check if room exists: " + e.getMessage());
        }
        return false;
    }
    
    public boolean addRoom(int roomNumber, int bedNumber, String bedSize, boolean hasBalcony, boolean nonsmoking) {
        String sql = "INSERT INTO rooms (roomNumber, bedNumber, bedSize, hasBalcony, nonsmoking) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, roomNumber);
            pstmt.setInt(2, bedNumber);
            pstmt.setString(3, bedSize);
            pstmt.setBoolean(4, hasBalcony);
            pstmt.setBoolean(5, nonsmoking);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 1) {
                System.out.println("Room " + roomNumber + " added successfully.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Failed to add room: " + e.getMessage());
        }
        return false;
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
    
    public boolean addReservation(String startDate, String endDate, String guest, int roomNumber, String specialRequest, String status) {
        String sql = "INSERT INTO reservations (startDate, endDate, guest, roomNumber, specialRequest, status) VALUES(?,?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);
            pstmt.setString(3, guest);
            pstmt.setInt(4, roomNumber);
            pstmt.setString(5, specialRequest);
            pstmt.setString(6, status);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows == 1;
        } catch (SQLException e) {
            System.out.println("Failed to add reservation: " + e.getMessage());
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
