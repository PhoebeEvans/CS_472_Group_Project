package controller;

import java.net.URL;
import java.net.URLClassLoader;
import java.security.NoSuchAlgorithmException;

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
        dbModel.createReservationsTable();
        dbModel.createRoomsTable();
        
        // Add the test account if it doesn't exist
        boolean isAdded = false;
		try {
			isAdded = dbModel.addAccount("John", "Doe", "johndoe@gmail.com", "password", true, null, null, null);
			isAdded = dbModel.addAccount("Phoebe", "Evans", "pe.computerscience@gmail.com", "1234", true, null, null, null);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (isAdded) {
            System.out.println("Test account successfully added.");
        } else {
            System.out.println("Test account already exists or failed to add.");
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
