package model;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.EncryptionUtils;
import controller.InfoHasher;

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
        String sql = "CREATE TABLE IF NOT EXISTS accounts (" +
            "id integer PRIMARY KEY AUTOINCREMENT," +
            "firstName text NOT NULL," +
            "lastName text NOT NULL," +
            "email text NOT NULL UNIQUE," +
            "password text NOT NULL," +
            "isAdmin boolean NOT NULL," +
            "cardNumber text," +
            "cardExpiration text," +
            "cardCCV text," +
            "totalSpent DECIMAL(10, 2) DEFAULT 0," +
            "couponsRedeemed INTEGER DEFAULT 0" +
            ");";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("The accounts table has been created with new loyalty fields.");
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
                + " nonsmoking BOOLEAN NOT NULL,"
                + " price DECIMAL(10, 2) NOT NULL" 
                + ");";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("rooms table created.");
            
            if (countRooms() < 2) {
                addRoom(101, 2, "Queen", true, true, 120.00);
                addRoom(102, 1, "King", false, true, 150.00);
                System.out.println("Dummy rooms successfully added.");
            } else {
                System.out.println("Dummy rooms already present.");
            }
         
        } catch (SQLException e) {
            System.out.println("failed to create rooms table: " + e.getMessage());
        }
    }
    
    private int countRooms() {
        String sql = "SELECT COUNT(*) AS total FROM rooms";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Error counting rooms: " + e.getMessage());
        }
        return 0;
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
    
    public boolean addRoom(int roomNumber, int bedNumber, String bedSize, boolean hasBalcony, boolean nonsmoking, double price) {
        String sql = "INSERT INTO rooms (roomNumber, bedNumber, bedSize, hasBalcony, nonsmoking, price) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, roomNumber);
            pstmt.setInt(2, bedNumber);
            pstmt.setString(3, bedSize);
            pstmt.setBoolean(4, hasBalcony);
            pstmt.setBoolean(5, nonsmoking);
            pstmt.setDouble(6, price);
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



    public boolean addAccount(String firstName, String lastName, String email, String password, boolean isAdmin, String cardNumber, String cardExpiration, String cardCCV) throws NoSuchAlgorithmException {
        String sql = "INSERT INTO accounts (firstName, lastName, email, password, isAdmin, cardNumber, cardExpiration, cardCCV, totalSpent, couponsRedeemed) VALUES (?,?,?,?,?,?,?,?,0,0)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	
        	String hashedPassword = InfoHasher.hashInfo(password);
        	
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, hashedPassword);
            pstmt.setBoolean(5, isAdmin);
            pstmt.setString(6, cardNumber);
            pstmt.setString(7, cardExpiration);
            pstmt.setString(8, cardCCV);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows == 1; //return true if success
        } catch (SQLException e) {
            System.out.println("Failed to add new account: " + e.getMessage());
            return false;
        }
    }

    
    public boolean addTransaction(int reservationID, String email, double totalAmount, String startDate, String endDate) {
        Connection conn = this.connect();
        try {
            //begin transaction 
            conn.setAutoCommit(false);

            //get total spent and coupons redeemed amount
            String fetchAccount = "SELECT totalSpent, couponsRedeemed FROM accounts WHERE email = ?";
            double newTotalSpent;
            int newCouponsRedeemed;
            try (PreparedStatement pstmtFetch = conn.prepareStatement(fetchAccount)) {
                pstmtFetch.setString(1, email);
                ResultSet rs = pstmtFetch.executeQuery();
                if (!rs.next()) return false; //err
                double currentTotalSpent = rs.getDouble("totalSpent");
                int currentCouponsRedeemed = rs.getInt("couponsRedeemed");

                //add to total spent
                newTotalSpent = currentTotalSpent + totalAmount;
                newCouponsRedeemed = currentCouponsRedeemed;

                //check if coupons are redeemable
                int couponsPossible = (int) (newTotalSpent / 500);
                if (couponsPossible > currentCouponsRedeemed) {
                    newCouponsRedeemed = couponsPossible;
                    totalAmount -= (newCouponsRedeemed - currentCouponsRedeemed) * 100; // discount total with each available coupon
                }
            }

            //update coupons and totalspent to account
            String updateAccount = "UPDATE accounts SET totalSpent = ?, couponsRedeemed = ? WHERE email = ?";
            try (PreparedStatement pstmtUpdate = conn.prepareStatement(updateAccount)) {
                pstmtUpdate.setDouble(1, newTotalSpent);
                pstmtUpdate.setInt(2, newCouponsRedeemed);
                pstmtUpdate.setString(3, email);
                pstmtUpdate.executeUpdate();
            }

            //add transaction with adjusted total spent
            String sql = "INSERT INTO transactions (email, totalAmount, startDate, endDate, reservationID) VALUES (?,?,?,?,?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, email);
                pstmt.setDouble(2, totalAmount);
                pstmt.setString(3, startDate);
                pstmt.setString(4, endDate);
                pstmt.setInt(5, reservationID);
                int affectedRows = pstmt.executeUpdate();
                conn.commit(); //commit transactions
                return affectedRows == 1;
            }
        } catch (SQLException e) {
            System.out.println("Transaction failed: " + e.getMessage());
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.out.println("Rollback failed: " + ex.getMessage());
            }
            return false;
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true); //back to auto commit after transaction commiited
            } catch (SQLException e) {
                System.out.println("Error resetting auto-commit: " + e.getMessage());
            }
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
    }


    public boolean checkCredentials(String email, String password) throws NoSuchAlgorithmException {
        String sql = "SELECT id FROM accounts WHERE email = ? AND password = ?";
        
        String hashedPassword = InfoHasher.hashInfo(password);
        
        System.out.println("DB MODEL credentials check: " + email + " " + password);

        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, email);
            statement.setString(2, hashedPassword);

            return statement.executeQuery().next(); // if result, credentials valid
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
	
	public boolean updateProfile(String email, String firstName, String lastName, String newPassword, boolean isAdmin) throws NoSuchAlgorithmException {
	    String sql = "UPDATE accounts SET firstName = ?, lastName = ?, password = ?, isAdmin = ? WHERE email = ?";
	    
	    String hashedPassword = InfoHasher.hashInfo(newPassword);
	    
	    try (Connection conn = this.connect();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, firstName);
	        stmt.setString(2, lastName);
	        stmt.setString(3, newPassword.isEmpty() ? getPasswordByEmail(email) : hashedPassword);
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
	
	public boolean hasCardInfo(String email) {
	    String sql = "SELECT cardNumber FROM accounts WHERE email = ? AND cardNumber IS NOT NULL AND cardNumber != ''";

	    try (Connection conn = this.connect();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, email);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            
	            return rs.next();
	        }
	    } catch (SQLException e) {
	        System.out.println("check card info error: " + e.getMessage());
	    }
	    return false;
	}
	
	//get the id of last reservation for receipt purposes
	public int getLastReservationID() {
	    String sql = "SELECT MAX(reservationID) AS lastID FROM reservations";
	    try (Connection conn = this.connect();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {
	        if (rs.next()) {
	            return rs.getInt("lastID");
	        }
	    } catch (SQLException e) {
	        System.out.println("Error fetching last reservation ID: " + e.getMessage());
	    }
	    return -1; // error retrieving
	}
	
	public boolean saveCardInfo(String email, String cardNumber, String cardExpiration, String cardCCV, boolean userIsUpdatingInfo) {
	    // check if cc exists for checkout reasons, or if user is purposely wanting to update info
		try {
            String encryptedCardNumber = EncryptionUtils.encrypt(cardNumber, "AES"); 
            String encryptedCardExpiration = EncryptionUtils.encrypt(cardExpiration, "AES");
            String encryptedCCV = EncryptionUtils.encrypt(cardCCV, "AES"); 

            String sql = "UPDATE accounts SET cardNumber = ?, cardExpiration = ?, cardCCV = ? WHERE email = ?";
            try (Connection conn = this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, encryptedCardNumber);
                pstmt.setString(2, encryptedCardExpiration);
                pstmt.setString(3, encryptedCCV);
                pstmt.setString(4, email);

                int affectedRows = pstmt.executeUpdate();
                return affectedRows == 1;
            }
        } catch (Exception e) {
            System.out.println("Encryption or database error: " + e.getMessage());
            return false;
        }
	    
	}

	public String getCardLastFour(String email) {
        try {
            String sql = "SELECT cardNumber FROM accounts WHERE email = ? AND cardNumber IS NOT NULL";
            try (Connection conn = this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, email);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    String encryptedCardNumber = rs.getString("cardNumber");
                    String decryptedCardNumber = EncryptionUtils.decrypt(encryptedCardNumber, "AES");
                    if (decryptedCardNumber != null && decryptedCardNumber.length() >= 4) {
                    	System.out.println("Decrypted last four: " + decryptedCardNumber.substring(decryptedCardNumber.length() - 4));
                        return decryptedCardNumber.substring(decryptedCardNumber.length() - 4);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Decryption or database error: " + e.getMessage());
        }
        return null; // In case of error or no data
    }


	public List<Map<String, String>> getReservationsByGuest(String email, boolean isFuture) {
	    List<Map<String, String>> reservations = new ArrayList<>();
	    String currentDate = LocalDate.now().toString(); // Get todays date
	    String sql = isFuture ?
	        "SELECT * FROM reservations WHERE guest = ? AND startDate >= ? ORDER BY startDate ASC" :
	        "SELECT * FROM reservations WHERE guest = ? AND startDate < ? ORDER BY startDate DESC";

	    try (Connection conn = this.connect();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, email);
	        pstmt.setString(2, currentDate);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                Map<String, String> reservation = new HashMap<>();
	                reservation.put("reservationID", String.valueOf(rs.getInt("reservationID")));
	                reservation.put("startDate", rs.getString("startDate"));
	                reservation.put("endDate", rs.getString("endDate"));
	                reservation.put("roomNumber", String.valueOf(rs.getInt("roomNumber")));
	                reservations.add(reservation);
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Error fetching reservations: " + e.getMessage());
	    }
	    return reservations;
	}
	
	public boolean deleteReservation(int reservationId) throws SQLException {
	    String sql = "DELETE FROM reservations WHERE reservationID = ?";
	    try (Connection conn = this.connect();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, reservationId);
	        int affectedRows = pstmt.executeUpdate();
	        return affectedRows > 0;
	    }
	}

	public Map<String, String> getTransactionDetailsByReservationId(int reservationId) throws SQLException {
	    String sql = "SELECT * FROM transactions WHERE reservationID = ?";
	    try (Connection conn = this.connect();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, reservationId);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            Map<String, String> details = new HashMap<>();
	            details.put("email", rs.getString("email"));
	            details.put("totalAmount", rs.getString("totalAmount"));
	            details.put("startDate", rs.getString("startDate"));
	            details.put("endDate", rs.getString("endDate"));
	            return details;
	        }
	        return null;
	    }
	}
	
	public Map<String, Double> getCouponDetails(String email) {
	    Map<String, Double> details = new HashMap<>();
	    String sql = "SELECT totalSpent, couponsRedeemed FROM accounts WHERE email = ?";
	    try (Connection conn = this.connect();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, email);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            details.put("totalSpent", rs.getDouble("totalSpent"));
	            details.put("couponsRedeemed", (double) rs.getInt("couponsRedeemed"));
	        }
	    } catch (SQLException e) {
	        System.err.println("Error fetching coupon details: " + e.getMessage());
	    }
	    return details;
	}
	
	public boolean updateRoom(int roomNumber, int bedNumber, String bedSize, boolean hasBalcony, boolean nonsmoking, double price) {
	    String sql = "UPDATE rooms SET bedNumber = ?, bedSize = ?, hasBalcony = ?, nonsmoking = ?, price = ? WHERE roomNumber = ?";
	    try (Connection conn = this.connect();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, bedNumber);
	        pstmt.setString(2, bedSize);
	        pstmt.setBoolean(3, hasBalcony);
	        pstmt.setBoolean(4, nonsmoking);
	        pstmt.setDouble(5, price);
	        pstmt.setInt(6, roomNumber);
	        int affectedRows = pstmt.executeUpdate();
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        System.out.println("Failed to update room: " + e.getMessage());
	        return false;
	    }
	}

	public Map<String, Object> getRoomDetails(int roomNumber) {
	    String sql = "SELECT * FROM rooms WHERE roomNumber = ?";
	    try (Connection conn = this.connect();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, roomNumber);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            Map<String, Object> roomDetails = new HashMap<>();
	            roomDetails.put("roomNumber", rs.getInt("roomNumber"));
	            roomDetails.put("bedNumber", rs.getInt("bedNumber"));
	            roomDetails.put("bedSize", rs.getString("bedSize"));
	            roomDetails.put("hasBalcony", rs.getBoolean("hasBalcony"));
	            roomDetails.put("nonsmoking", rs.getBoolean("nonsmoking"));
	            roomDetails.put("price", rs.getDouble("price"));
	            return roomDetails;
	        }
	    } catch (SQLException e) {
	        System.out.println("Failed to fetch room details: " + e.getMessage());
	    }
	    return null;
	}


}
