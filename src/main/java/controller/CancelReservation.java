package controller;

import model.DatabaseModel;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/CancelReservation")
public class CancelReservation extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reservationId = request.getParameter("reservationId");
        if (reservationId == null || reservationId.isEmpty()) {
            response.sendRedirect("errorPage.jsp?message=Invalid reservation ID");
            return;
        }

        DatabaseModel dbModel = new DatabaseModel();
        try {
            //get transaction info for the original transaction
            Map<String, String> transactionDetails = dbModel.getTransactionDetailsByReservationId(Integer.parseInt(reservationId));

            //delete res
            boolean isDeleted = dbModel.deleteReservation(Integer.parseInt(reservationId));
            if (!isDeleted) {
                response.sendRedirect("errorPage.jsp?message=Failed to cancel reservation");
                return;
            }

            //refund by way of negative payment
            if (transactionDetails != null) {
                double refundAmount = -Double.parseDouble(transactionDetails.get("totalAmount"));
                dbModel.addTransaction(Integer.parseInt(reservationId), transactionDetails.get("email"), refundAmount, transactionDetails.get("startDate"), transactionDetails.get("endDate"));
            }

            response.sendRedirect("guestReservations.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("errorPage.jsp?message=Database error");
        }
    }
}
