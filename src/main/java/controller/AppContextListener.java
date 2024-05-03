package controller;

import java.net.URL;
import java.net.URLClassLoader;

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
        dbModel.createTransactionsTable();
        
        // Add the test account if it doesn't exist
        boolean isAdded = dbModel.addAccount("John", "Doe", "johndoe@gmail.com", "password", true);
        if (isAdded) {
            System.out.println("Test account successfully added.");
        } else {
            System.out.println("Test account already exists or failed to add.");
        }
        
        //add dummy transactions
        dbModel.addTransaction(1, "johndoe@gmail.com", 200.00, "2024-04-01", "2024-04-03");
        isAdded = dbModel.addTransaction(2, "janedoe@gmail.com", 450.50, "2024-05-15", "2024-05-20");
        
        if (isAdded) {
            System.out.println("Dummy transactions successfully added.");
        } else {
            System.out.println("Dummy transactions failed to add.");
        }
        
        URL[] urls = ((URLClassLoader) Thread.currentThread().getContextClassLoader()).getURLs();
        for (URL url : urls) {
            System.out.println("LOADED URLS:" + url.getFile());
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // if cleanup is ever needed
    }
}
