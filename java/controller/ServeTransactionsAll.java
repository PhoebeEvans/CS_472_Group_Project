package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/ServeTransactionsAll")
public class ServeTransactionsAll extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"all_transactions.csv\"");

        String sql = "SELECT * FROM transactions";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:HotelDB.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            try (PrintWriter writer = response.getWriter()) {
                // Write column headers
                writer.println("TransactionID,Email,TotalAmount,StartDate,EndDate,ReservationID");

                //track sums
                int totalTransactions = 0;
                double sumOfTransactions = 0.0;

                // loop through eah entry of the transactions database
                while (rs.next()) {
                    int transactionID = rs.getInt("transactionID");
                    String email = rs.getString("email");
                    double totalAmount = rs.getDouble("totalAmount");
                    String startDate = rs.getString("startDate");
                    String endDate = rs.getString("endDate");
                    int reservationID = rs.getInt("reservationID");

                    // Increment the total transactions count and add to the sum
                    totalTransactions++;
                    sumOfTransactions += totalAmount;

                    // Write the transaction to the CSV
                    writer.printf("%d,%s,%.2f,%s,%s,%d%n",
                                  transactionID,
                                  email,
                                  totalAmount,
                                  startDate,
                                  endDate,
                                  reservationID);
                }

                //write the totals in the last line
                writer.printf("Total Transactions: %d,,Sum of Transactions: %.2f%n", totalTransactions, sumOfTransactions);
            }
        } catch (SQLException e) {
            throw new ServletException("SQL Error", e);
        }
    }
}
