/**
 * Guest class differentiates a hotel guest from a hotel admin
 * Author: Vince Gnodle
 * 
 */

public class Guest extends User{

    //protected ArrayList questions;

    int guestID;
    int creditCardNumber;
    String creditCardExp;
    int creditCardCode;
    int extraGuests;
    //Reservation[] bookingHistory; //Can uncomment after reservation class is added

    /**
     * Constructor with parameters
     * @param firstName
     * @param lastName
     * @param userName
     * @param password
     */
    Guest(String firstName, String lastName, String userName, String password) {
        super(firstName, lastName, userName, password);
    }

    
    /**
     * Empty constructor
     */
    public Guest() {

    }

    
    /**
     * Retrieves the id of the clients question
     * @return
     */
    public int getGuestID() {
        return guestID;
    }

    
    /**
     * Sets guests ID number
     * @param guestID
     */
    public void setGuestID(int guestID) {
        this.guestID = guestID;
    }
    
    //need to add remaining getters and setters
    
}
