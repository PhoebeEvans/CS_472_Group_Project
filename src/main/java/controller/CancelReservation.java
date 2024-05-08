package controller;

import model.DatabaseModel;
import model.Mail;

import javax.mail.MessagingException;
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
                
                int reservationID = Integer.parseInt(reservationId);
                double cost = Double.parseDouble(transactionDetails.get("totalAmount"));
                String email = transactionDetails.get("email");
                String startDate = transactionDetails.get("startDate");
                String endDate = transactionDetails.get("endDate");
                
                
                ////email form
                String subject = "Reservation Deleted - Caribou Inn - " + reservationID;
                String body = "Hello " + email + ",\nYour reservation " + reservationID + " at Caribou Inn from " + startDate + " to " + endDate + " has been canceled.\nThe sum of " + cost + " has been refunded.";
                //send welcome email
                Mail newEmail = new Mail();
                
                try {
                	System.out.println("try");
    				newEmail.send(email, subject, body);
    			} catch (MessagingException | IOException e) {
    				// TODO Auto-generated catch block
    				System.out.println("fail");
    				e.printStackTrace();
    			}
            }

            response.sendRedirect("guestReservations.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("errorPage.jsp?message=Database error");
        }
    }
}
