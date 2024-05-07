package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/CheckRoomAvailabilityServlet")
public class CheckAvailability extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String checkInDate = request.getParameter("checkInDate");
        String checkOutDate = request.getParameter("checkOutDate");
        String origin = request.getParameter("origin");
        
        request.setAttribute("checkInDate", checkInDate);
        request.setAttribute("checkOutDate", checkOutDate);

        List<List<String>> roomDetails = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:HotelDB.db")) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM rooms WHERE roomNumber NOT IN (SELECT roomNumber FROM reservations WHERE NOT (endDate <= ? OR startDate >= ?))");
            pstmt.setString(1, checkOutDate);
            pstmt.setString(2, checkInDate);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    List<String> room = new ArrayList<>();
                    room.add(String.valueOf(rs.getInt("roomNumber")));
                    room.add(String.valueOf(rs.getInt("bedNumber")));
                    room.add(rs.getString("bedSize"));
                    room.add(rs.getInt("hasBalcony") == 1 ? "Yes" : "No");
                    room.add(rs.getInt("nonsmoking") == 1 ? "Yes" : "No");
                    room.add(String.format("%.2f", rs.getDouble("price")));
                    roomDetails.add(room);
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database connection problem", e);
        }

        request.setAttribute("availableRooms", roomDetails);
        
        // determine where to send user based on origin hidden input
        String redirectPage = "availableRooms.jsp"; // default for admin

        if ("guest".equals(origin)) {
            redirectPage = "chooseRoomsAsGuest.jsp";
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(redirectPage);
        dispatcher.forward(request, response);
    }
}
