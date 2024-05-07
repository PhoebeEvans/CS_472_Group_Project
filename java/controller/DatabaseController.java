package controller;

import model.Mail;
import model.DatabaseModel;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

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
            boolean isValidUser = false;
			try {
				isValidUser = dbModel.checkCredentials(email, password);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            System.out.println("Credentials valid: " + isValidUser); //logging

            if (isValidUser) {
                System.out.println("User is valid. Setting session attribute."); // Log valid user
                // Assume you have a method to get the first name based on email
                String firstName = dbModel.getFirstNameByEmail(email);
                String lastName = dbModel.getLastNameByEmail(email);
                Boolean isAdmin = dbModel.getAdminStatusByEmail(email);
                
                String strIsAdmin = null;
                
                if(isAdmin) strIsAdmin = "admin"; //session can only check if object is null, so set to null if not admin, "admin" otherwise

                // Set up the user session
                request.getSession().setAttribute("firstName", firstName);
                request.getSession().setAttribute("lastName", lastName);
                request.getSession().setAttribute("email", email);
                request.getSession().setAttribute("isAdmin", strIsAdmin);
                boolean hasCardInfo = dbModel.hasCardInfo(email);
                request.getSession().setAttribute("hasCardInfo", hasCardInfo);
                System.out.println("User has card info in session attr: " + hasCardInfo);
                
                if(hasCardInfo) {
                	request.getSession().setAttribute("lastFourCC", dbModel.getCardLastFour(email));
                }
                
                //logging
                System.out.println("Session attribute set for firstName: " + firstName);


                // Redirect to the home page
                System.out.println("Redirecting to index.jsp"); // Log redirection
                response.sendRedirect("index.jsp");
                return;
            } else {
                System.out.println("Invalid login credentials provided."); // Log invalid credentials
                // Handle login failure
                response.sendRedirect("badCredentials.jsp?referrer=login.html");
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
            boolean accountCreated = false;
			try {
				accountCreated = dbModel.addAccount(firstName, lastName, email, password, isAdmin, null, null, null);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            // Check if account creation was successful
            if (accountCreated) {
            	// Set up the user session
            	String strIsAdmin = null; //upon account creation, account will never be an admin initially
                request.getSession().setAttribute("firstName", firstName);
                request.getSession().setAttribute("lastName", lastName);
                request.getSession().setAttribute("email", email);
                request.getSession().setAttribute("isAdmin", strIsAdmin);
                
                //logging
                System.out.println("Session attribute set for firstName: " + firstName);


                // Redirect to the home page
                System.out.println("Redirecting to index.jsp"); // Log redirectionm
                response.sendRedirect("index.jsp");
                
                
                //email form
                String body = "Your account at the Caribou Inn is now active.";
                //
                String subject = "Account Activated";
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
                
                return;
            }

            else {
                System.out.println("Failed to create account for email: " + email); // Log account creation failure
                // Redirect to an error page or return some error message
                response.sendRedirect("errorPage.jsp"); // Redirect to an error page
            }
        } 
        
        else if ("accessOtherAccount".equals(action)) {
            handleAccessOtherAccount(request, response);
        }
        
        else if ("updateOtherUser".equals(action)) {
        	
        	//need to verify that the admin is the one changing other's accounts
        	String adminEmail = request.getParameter("adminEmail");
            String adminPassword = request.getParameter("adminPassword");
            
            try {
				if (dbModel.checkCredentials(adminEmail, adminPassword)) {
					handleUpdateOtherUser(request, response);
				} else {
				    // Admin credentials are not valid
				    response.sendRedirect("badCredentials.jsp?referrer=editProfileAsAdmin.jsp");
				}
			} catch (NoSuchAlgorithmException | IOException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
        }
        
        else if ("updateProfile".equals(action)) {
            try {
				handleProfileUpdate(request, response);
			} catch (NoSuchAlgorithmException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        else {
            System.out.println("No recognized action specified."); // Log unrecognized action
            // Redirect to a page that indicates an error or unrecognized action.
            response.sendRedirect("errorPage.jsp"); // replace 'errorPage.jsp' with the actual error page
        }
    }
    
    private void handleAccessOtherAccount(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String otherUserEmail = request.getParameter("otherUserEmail");

        // Security check: Ensure the current user is an admin
        String currentUserAdminStatus = (String) session.getAttribute("isAdmin");
        if (!"admin".equals(currentUserAdminStatus)) {
            System.out.println("Access denied: Current user is not an admin.");
            response.sendRedirect("accessDenied.jsp");
            return;
        }
        
        System.out.println("Attempting to access account of: " + otherUserEmail);

        String otherUserFirstName = dbModel.getFirstNameByEmail(otherUserEmail);
        String otherUserLastName = dbModel.getLastNameByEmail(otherUserEmail);
        Boolean otherUserIsAdmin = dbModel.getAdminStatusByEmail(otherUserEmail);		
        		
        if (otherUserFirstName != null) {
            request.setAttribute("otherUserFirstName", otherUserFirstName);
            request.setAttribute("otherUserLastName", otherUserLastName);
            request.setAttribute("otherUserEmail", otherUserEmail);
            request.setAttribute("otherUserIsAdmin", otherUserIsAdmin);
            
            System.out.println("Redirecting to editProfileAsAdmin.jsp for email: " + otherUserEmail);
            RequestDispatcher dispatcher = request.getRequestDispatcher("editProfileAsAdmin.jsp");
            dispatcher.forward(request, response);
        } else {
            System.out.println("No user found with the email: " + otherUserEmail);
            response.sendRedirect("userNotFound.jsp?referrer=employeePage.jsp");
        }
    }
    
    private void handleUpdateOtherUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, NoSuchAlgorithmException {
        HttpSession session = request.getSession();
        String adminEmail = request.getParameter("adminEmail");
        String adminPassword = request.getParameter("adminPassword");
        
        boolean otherUserIsAdmin = "true".equals(request.getParameter("otherUserIsAdmin"));
        System.out.println("Admin status to set: " + otherUserIsAdmin); //debug


        // Check admin credentials
        if (dbModel.checkCredentials(adminEmail, adminPassword)) {
            // Retrieve other user's info from the form
            String otherUserFirstName = request.getParameter("otherUserFirstName");
            String otherUserLastName = request.getParameter("otherUserLastName");
            String otherUserEmail = request.getParameter("otherUserEmail");
            String otherUserNewPassword = request.getParameter("otherUserNewPassword");
            // Perform the update in the database
            boolean updateSuccessful = dbModel.updateProfile(otherUserEmail, otherUserFirstName, otherUserLastName, otherUserNewPassword, otherUserIsAdmin);

            if (updateSuccessful) {
                System.out.println("Other user profile updated successfully.");
                response.sendRedirect("profileUpdatedSuccess.jsp");
            } else {
                System.out.println("Failed to update other user profile.");
                response.sendRedirect("profileUpdatedError.jsp");
            }
        } else {
            System.out.println("Admin credentials invalid.");
            response.sendRedirect("badCredentials.jsp?referrer=editProfileAsAdmin.jsp");
        }
    }
    
    private void handleProfileUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchAlgorithmException {
        String email = request.getParameter("email");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        
        System.out.println("edit profile: email: " + email);
        System.out.println("edit profile: password: " + oldPassword);

        // Verify old password
        if (dbModel.checkCredentials(email, oldPassword)) {
            boolean updateSuccessful = dbModel.updateProfile(email, firstName, lastName, newPassword, false);
            
            //update session info with new info
            request.getSession().setAttribute("firstName", firstName);
            request.getSession().setAttribute("lastName", lastName);
            request.getSession().setAttribute("email", email);

            if (updateSuccessful) {
                response.sendRedirect("profileUpdatedSuccess.jsp");
            } else {
                response.sendRedirect("profileUpdatedError.jsp");
            }
        } else {
        	response.sendRedirect("badCredentials.jsp?referrer=editProfile.jsp");

        }
    }

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("logout".equals(action)) {
            System.out.println("Attempting to log out");
            HttpSession session = request.getSession(false); //retrieve session
            if (session != null) {
                session.invalidate(); // invalidate session to clear attributes
                System.out.println("Session invalidated successfully.");
            }
            response.sendRedirect("index.jsp");
        } 
        
        else {
            response.sendRedirect("errorPage.jsp");
        }
    }

}
