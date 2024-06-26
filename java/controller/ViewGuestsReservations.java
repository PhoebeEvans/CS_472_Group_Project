package controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import model.DatabaseModel;

@WebServlet("/ViewReservations")
public class ViewGuestsReservations extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        if (email == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        DatabaseModel dbModel = new DatabaseModel();
        //get coupon details
        Map<String, Double> couponInfo = dbModel.getCouponDetails(email);
        request.setAttribute("amountNeeded", 500 - (couponInfo.get("totalSpent") % 500));
        double totalSpent = couponInfo.get("totalSpent");
        double couponsRedeemed = couponInfo.get("couponsRedeemed");
        int availableCoupons = (int)((totalSpent / 500) - couponsRedeemed);
        request.setAttribute("availableCoupons", availableCoupons);

        List<Map<String, String>> futureReservations = dbModel.getReservationsByGuest(email, true);
        List<Map<String, String>> pastReservations = dbModel.getReservationsByGuest(email, false);

        request.setAttribute("futureReservations", futureReservations);
        request.setAttribute("pastReservations", pastReservations);
        RequestDispatcher dispatcher = request.getRequestDispatcher("guestReservations.jsp");
        dispatcher.forward(request, response);
    }
}
