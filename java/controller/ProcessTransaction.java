package controller;

import javax.mail.MessagingException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import model.DatabaseModel;
import model.Mail;

@WebServlet("/ProcessTransaction")
public class ProcessTransaction extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("customerEmail");
        String cardNumber = request.getParameter("cardNumber"); 
        String expirationDate = request.getParameter("expirationDate"); 
        String ccv = request.getParameter("ccv"); 
        DatabaseModel dbModel = new DatabaseModel();
        
        //info of the transaction
        double totalAmount = Double.parseDouble(request.getParameter("totalAmount")); 
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String guest = email;
        int roomNumber = Integer.parseInt(request.getParameter("roomNumber"));
        String specialRequest = request.getParameter("specialRequest"); 
        String status = "PENDING"; 

        // add reservation and return the id
        boolean reservationAdded = dbModel.addReservation(startDate, endDate, guest, roomNumber, specialRequest, status);
        int reservationID = dbModel.getLastReservationID(); // Implement this method to retrieve the last inserted reservation ID
        
        if (reservationAdded && reservationID != -1) {
            // Proceed to add the transaction
            boolean transactionAdded = dbModel.addTransaction(reservationID, email, totalAmount, startDate, endDate);
            
            //user is wanting to save CC info
            System.out.println("Is user wanting to save card info: " + request.getParameter("saveCardOption"));
            if ("yes".equals(request.getParameter("saveCardOption"))) { 
                boolean cardSaved = dbModel.saveCardInfo(email, cardNumber, expirationDate, ccv, false);
                if (cardSaved) {
                    System.out.println("Card information saved successfully.");
                } else {
                    System.out.println("Failed to save card information.");
                }
            }

            if (transactionAdded) {
            	//email form
                String subject = "Reservation Conformation - Caribou Inn.";
                String body = "Your reservation for a lovely stay at the Caribou Inn is confirmed.";
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
                
                response.sendRedirect("reservationConfirmed.jsp"); // Redirect to confirmation page
            } else {
                // Handle the error in transaction addition
                request.setAttribute("error", "Failed to add transaction.");
                request.getRequestDispatcher("errorPage.jsp").forward(request, response); // Redirect to an error page
            }
        } else {
            // Handle the error in reservation addition
            request.setAttribute("error", "Failed to add reservation.");
            request.getRequestDispatcher("errorPage.jsp").forward(request, response); // Redirect to an error page
        }
    }
}
