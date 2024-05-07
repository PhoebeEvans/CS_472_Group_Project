package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ReserveRoomServlet")
public class ReserveRoomAsAdmin extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roomNumber = request.getParameter("roomNumber");
        String price = request.getParameter("price");
        String checkInDate = request.getParameter("checkInDate");
        String checkOutDate = request.getParameter("checkOutDate");

        //pass relevant info for reservation
        request.setAttribute("roomNumber", roomNumber);
        request.setAttribute("price", price);
        request.setAttribute("checkInDate", checkInDate);
        request.setAttribute("checkOutDate", checkOutDate);

        //redirect to transactions page for admins
        RequestDispatcher dispatcher = request.getRequestDispatcher("checkoutAsAdmin.jsp");
        dispatcher.forward(request, response);
    }
}
