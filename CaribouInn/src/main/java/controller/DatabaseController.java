package controller;

import model.DatabaseModel;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AccountServlet")
public class DatabaseController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private DatabaseModel dbModel = new DatabaseModel();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        System.out.println("Received action: " + action); // Log the received action

        if ("login".equals(action)) {
            System.out.println("Attempting to log in"); // Log login attempt
            // Login logic
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Check credentials with database
            boolean isValidUser = dbModel.checkCredentials(email, password);
            System.out.println("Credentials valid: " + isValidUser); //logging

            if (isValidUser) {
                System.out.println("User is valid. Setting session attribute."); // Log valid user
                // Assume you have a method to get the first name based on email
                String firstName = dbModel.getFirstNameByEmail(email);

                // Set up the user session
                request.getSession().setAttribute("firstName", firstName);
                
                //logging
                System.out.println("Session attribute set for firstName: " + firstName);


                // Redirect to the home page
                System.out.println("Redirecting to index.jsp"); // Log redirection
                response.sendRedirect("index.jsp");
                return;
            } else {
                System.out.println("Invalid login credentials provided."); // Log invalid credentials
                // Handle login failure
                response.getWriter().write("Invalid login credentials!");
            }
        } else if ("createAccount".equals(action)) {
        	System.out.println("Attempting to create an account"); // Log account creation attempt

            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            // Assuming all new accounts are not admin accounts.
            boolean isAdmin = false; // Or you can take this as a parameter from the form if needed.

            // Attempt to add the account
            boolean accountCreated = dbModel.addAccount(firstName, lastName, email, password, isAdmin);

            // Check if account creation was successful
            if (accountCreated) {
            	// Set up the user session
                request.getSession().setAttribute("firstName", firstName);
                
                //logging
                System.out.println("Session attribute set for firstName: " + firstName);


                // Redirect to the home page
                System.out.println("Redirecting to index.jsp"); // Log redirection
                response.sendRedirect("index.jsp");
                return;
            } else {
                System.out.println("Failed to create account for email: " + email); // Log account creation failure
                // Redirect to an error page or return some error message
                response.sendRedirect("errorPage.jsp"); // Redirect to an error page
            }
        } 
        
        else {
            System.out.println("No recognized action specified."); // Log unrecognized action
            // Redirect to a page that indicates an error or unrecognized action.
            response.sendRedirect("errorPage.jsp"); // replace 'errorPage.jsp' with the actual error page
        }
    }

}
