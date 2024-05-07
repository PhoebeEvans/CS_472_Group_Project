package controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import model.DatabaseModel;

@WebServlet("/UpdateOrAddRoom")
public class UpdateOrAddRoom extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve room details from form
        int roomNumber = Integer.parseInt(request.getParameter("roomNumber"));
        int bedNumber = Integer.parseInt(request.getParameter("bedNumber"));
        String bedSize = request.getParameter("bedSize");
        double price = Double.parseDouble(request.getParameter("price"));
        boolean hasBalcony = Boolean.parseBoolean(request.getParameter("hasBalcony"));
        boolean nonsmoking = Boolean.parseBoolean(request.getParameter("nonsmoking"));

        HttpSession session = request.getSession();
        String action = (String) session.getAttribute("roomAction");

        DatabaseModel dbModel = new DatabaseModel();
        boolean result = false;

        if ("add".equals(action)) {
            //add room
            result = dbModel.addRoom(roomNumber, bedNumber, bedSize, hasBalcony, nonsmoking, price);
        } else if ("update".equals(action)) {
            // update room
            result = dbModel.updateRoom(roomNumber, bedNumber, bedSize, hasBalcony, nonsmoking, price);
        }

        if (result) {
            response.sendRedirect("employeePage.jsp?success=true");
        } else {
            response.sendRedirect("employeePage.jsp?success=false");
        }
    }
}
