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
    
    
    /**
     * Retrieves guest's credit card number
     * @return	creditCardNumber
     */
    public int getCreditCardNumber() {
		return creditCardNumber;
	}

    
    /**
     * Sets guest's credit card number
     * @param creditCardNumber
     */
	public void setCreditCardNumber(int creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	
	/**
	 * Retrieves guest's credit card expiration date
	 * @return	creditCardExp
	 */
	public String getCreditCardExp() {
		return creditCardExp;
	}

	
	/**
	 * Sets guest's credit card expiration date
	 * @param creditCardExp
	 */
	public void setCreditCardExp(String creditCardExp) {
		this.creditCardExp = creditCardExp;
	}

	
	/**
	 * Retrieves guest's credit card security code
	 * @return	creditCardCode
	 */
	public int getCreditCardCode() {
		return creditCardCode;
	}


	/**
	 * Sets guest's credit card security code
	 * @param creditCardCode
	 */
	public void setCreditCardCode(int creditCardCode) {
		this.creditCardCode = creditCardCode;
	}


	/**
	 * Retrieves guest's number of extra guests
	 * @return	extraGuests
	 */
	public int getExtraGuests() {
		return extraGuests;
	}


	/**
	 * Sets guest's number of extra guests
	 * @param extraGuests
	 */
	public void setExtraGuests(int extraGuests) {
		this.extraGuests = extraGuests;
	}

    
}
