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

@WebServlet("/ServeTransactionsFromDateRange")
public class ServeTransactionsFromDateRange extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"transactions_date_range.csv\"");

        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");

        String sql = "SELECT * FROM transactions WHERE startDate >= ? AND endDate <= ?";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:HotelDB.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, beginDate);
            pstmt.setString(2, endDate);

            ResultSet rs = pstmt.executeQuery();
            try (PrintWriter writer = response.getWriter()) {
                writer.println("TransactionID,Email,TotalAmount,StartDate,EndDate,ReservationID");
                
                int totalTransactions = 0;
                double sumOfTransactions = 0.0;

                while (rs.next()) {
                    int transactionID = rs.getInt("transactionID");
                    String email = rs.getString("email");
                    double totalAmount = rs.getDouble("totalAmount");
                    String startDate = rs.getString("startDate");
                    endDate = rs.getString("endDate");
                    int reservationID = rs.getInt("reservationID");

                    //track sums
                    totalTransactions++;
                    sumOfTransactions += totalAmount;

                    writer.printf("%d,%s,%.2f,%s,%s,%d%n",
                                  transactionID,
                                  email,
                                  totalAmount,
                                  startDate,
                                  endDate,
                                  reservationID);
                }

                //write summary of report
                writer.printf("Total Transactions: %d,,Sum of Transactions: %.2f%n", totalTransactions, sumOfTransactions);
            }
        } catch (SQLException e) {
            throw new ServletException("SQL Error", e);
        }
    }
}

