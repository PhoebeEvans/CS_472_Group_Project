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
        
        request.setAttribute("checkInDate", checkInDate);
        request.setAttribute("checkOutDate", checkOutDate);

        List<Integer> availableRooms = new ArrayList<>(); //rooms that are available list

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:HotelDB.db")) {
            //make list of rooms not availble
            List<Integer> unavailableRooms = getUnavailableRooms(conn, checkInDate, checkOutDate);

            //open db connection to check unavailable rooms against all rooms to get available rooms
            try (PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM rooms WHERE roomNumber NOT IN (SELECT roomNumber FROM reservations WHERE NOT (endDate <= ? OR startDate >= ?))")) {
                pstmt.setString(1, checkOutDate);
                pstmt.setString(2, checkInDate);
                ResultSet rs = pstmt.executeQuery();
                List<List<String>> roomDetails = new ArrayList<>();
                while (rs.next()) {
                    List<String> room = new ArrayList<>();
                    room.add(String.valueOf(rs.getInt("roomNumber")));
                    room.add(String.valueOf(rs.getInt("bedNumber")));
                    room.add(rs.getString("bedSize"));
                    
                    if(rs.getInt("hasBalcony") == 1) {
                    	room.add("Yes");
                    } else {
                    	room.add("No");
                    }
                    
                    if(rs.getInt("nonsmoking") == 1) {
                    	room.add("Yes");
                    } else {
                    	room.add("No");
                    }
                    
                    //add rounded price as string
                    
                    room.add(String.format("%.2f", rs.getDouble("price")));
                    
                    //add room to details
                    roomDetails.add(room);
                }
                
              //with available rooms data, forward to jsp file
              request.setAttribute("availableRooms", roomDetails);
            }


            
            RequestDispatcher dispatcher = request.getRequestDispatcher("availableRooms.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Database connection problem", e);
        }
    }

    private List<Integer> getUnavailableRooms(Connection conn, String checkInDate, String checkOutDate) throws SQLException {
        List<Integer> unavailableRooms = new ArrayList<>();
        String sql = "SELECT roomNumber FROM reservations WHERE NOT (endDate <= ? OR startDate >= ?)";
        
        //open db connection to get all rooms that are curr reserved in specified date range
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, checkInDate);
            pstmt.setString(2, checkOutDate);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                unavailableRooms.add(rs.getInt("roomNumber"));
            }
        }
        return unavailableRooms;
    }
}
