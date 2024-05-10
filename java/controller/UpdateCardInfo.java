package controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import model.DatabaseModel;

@WebServlet("/UpdateCardInfo")
public class UpdateCardInfo extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String cardNumber = request.getParameter("cardNumber");
        String expirationDate = request.getParameter("expiryDate");
        String ccv = request.getParameter("cvc");
        String action = request.getParameter("action");

        DatabaseModel dbModel = new DatabaseModel();

        //check if this person is who they say they are
        try {
			if (!dbModel.checkCredentials(email, password)) {
			    // redirect to bad credentials if bad pass
			    response.sendRedirect("badCredentials.jsp?referrer=manageCardDetails.jsp");
			    return; 
			}
		} catch (NoSuchAlgorithmException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.println("Action received: " + action);


        // update/add cc info
        boolean success;
        if ("addCard".equals(action)) {
            success = dbModel.saveCardInfo(email, cardNumber, expirationDate, ccv, true);
            
            System.out.println("In the updateCard servlet, the first success is: " + success);
            
            if(success) {
            	boolean hasCardInfo = true;
            	request.getSession().removeAttribute("hasCardInfo");
            	request.getSession().removeAttribute("lastFourCC");
                request.getSession().setAttribute("hasCardInfo", hasCardInfo);
                String lastFour = dbModel.getCardLastFour(email);
                request.getSession().setAttribute("lastFourCC", lastFour);
                System.out.println("User has card info in session attr: " + hasCardInfo);
                System.out.println("User last four in session attr: " + lastFour);
            }
        } else {
            success = dbModel.saveCardInfo(email, cardNumber, expirationDate, ccv, false);
            System.out.println("In the updateCard servlet, the second success is: " + success);
        }

        if (success) {
            request.setAttribute("message", "Card information updated successfully.");
        } else {
            request.setAttribute("message", "Failed to update card information.");
        }
        
        System.out.println("In the updateCard servlet, the third success is: " + success);
        
        //add this to make sure cache doesn't cause issues
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); 
        response.setHeader("Pragma", "no-cache"); 
        response.setDateHeader("Expires", 0); 
        //refirect to edit profile page if success
        response.sendRedirect("editProfile.jsp");
    }
}
