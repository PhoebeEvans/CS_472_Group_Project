package controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import model.DatabaseModel;

@WebListener
public class AppContextListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DatabaseModel dbModel = new DatabaseModel();
        dbModel.createAccountsTable();
        // Add the test account if it doesn't exist
        boolean isAdded = dbModel.addAccount("John", "Doe", "johndoe@gmail.com", "password", false);
        if (isAdded) {
            System.out.println("Test account successfully added.");
        } else {
            System.out.println("Test account already exists or failed to add.");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // if cleanup is ever needed
    }
}
