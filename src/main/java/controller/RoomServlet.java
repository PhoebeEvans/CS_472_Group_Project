package controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

import model.DatabaseModel;

@WebServlet("/RoomServlet")
public class RoomServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        DatabaseModel dbModel = new DatabaseModel();
        HttpSession session = request.getSession();

        if ("Add This Room".equals(action)) {
            
            session.setAttribute("roomAction", "add");
            response.sendRedirect("manageRoom.jsp");
        } else if ("Update This Room".equals(action)) {
            int roomNumber = Integer.parseInt(request.getParameter("roomDetails"));
            session.setAttribute("roomAction", "update");
            //get room info from database
            Map<String, Object> roomDetails = dbModel.getRoomDetails(roomNumber);
            if (roomDetails != null) {
                session.setAttribute("roomDetails", roomDetails);
            } else {
                session.setAttribute("error", "Room not found");
            }
            response.sendRedirect("manageRoom.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        request.getRequestDispatcher("/manageRoom.jsp").forward(request, response);
    }
}
