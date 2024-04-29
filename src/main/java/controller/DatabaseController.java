package controller;

import model.DatabaseModel;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AccountServlet")
public class DatabaseController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private DatabaseModel dbModel = new DatabaseModel();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("login".equals(action)) {
            // Login logic
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            
            // Check credentials with database
            boolean isValidUser = dbModel.checkCredentials(email, password);
            
            if (isValidUser) {
                // Assume you have a method to get the first name based on email
                String firstName = dbModel.getFirstNameByEmail(email);
                
                // Set up the user session
                request.getSession().setAttribute("firstName", firstName);
                
                // Redirect to the home page
                response.sendRedirect("index.html");
            } else {
                // Handle login failure
                response.getWriter().write("Invalid login credentials!");
            }
        } else if ("createAccount".equals(action)) {
            // Account creation logic
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            boolean isAdmin = Boolean.parseBoolean(request.getParameter("isAdmin"));
            
            // add account method here, returns true if successful
            boolean accountCreated = dbModel.addAccount(firstName, lastName, email, password, isAdmin);

            // Check if account creation was successful and send response
            if (accountCreated) {
                // Forward to a success page or write a success response
                response.getWriter().write("Account created successfully!");
            } else {
                // Forward to an error page or write an error response
                response.getWriter().write("Account creation failed!");
            }
        }
    }
}
