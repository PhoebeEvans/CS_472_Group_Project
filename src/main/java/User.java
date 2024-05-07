/**
 * The class is creates a User object to use with a database.
 * Author: Vince Gnodle
 */


public class User {

    //Class variables assigned to a client object
    
    private String firstName;
    private String lastName;
    private String userName;
    private String password;


    /**
     * Constructor creates a client object with attributes set as the given parameters
     * @param firstName 
     * @param lastName
     * @param userName
     * @param password  Clients password
     */
    public User(String firstName, String lastName, String userName, String password){

        this.firstName = firstName;
        
        this.lastName = lastName;
        
        this.userName = userName;

        this.password = password;

    }
 


    /**
     * Default constructor
     */
    public User(){

    }


    /**
     * Method returns clients first name
     * @return name
     */
    public String getFirstName() {
        return firstName;
    }


    /**
     * Method sets clients first name
     * @param firstName clients first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    /**
     * Method returns clients last name
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }


    /**
     * Method sets clients full name
     * @param name clients full name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    
    /**
     * Method gets the clients user name
     * @return  user name
     */
    public String getUserName() {
        return userName;
    }


    /**
     * Method sets the clients user name
     * @param userName   clients user name
     */
    public void setUserNamer(String userName) {
        this.userName = userName;
    }


    /**
     * Method retrieves the client's password
     * @return  password
     */
    public String getPassword() {
        return password;
    }


    /**
     * Method sets the clients password method
     * @param password
     */
    public void setPassword(String password) {  //need to set password upon sign up
        this.password = password;
    }
    
    
    /**
     * Method to login
     */
    public void login() {
        
    }
    
    
    /**
     * Method to edit profile
     */
    public void editProfile() {
        
    }
    
}
