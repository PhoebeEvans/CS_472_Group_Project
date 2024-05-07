/**
 * 
 */
public class Transaction{
	
	private int transactionID;
	private double totalPrice;
	private Guest mainGuest;
	
    public Transaction() {

    }

    /**
     * Method returns transactions ID
     * @return transactionID
     */
    public int getTransactionID() {
        return transactionID;
    }


    /**
     * Method sets the transactions ID
     * @param transactionID
     */
    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    
    /**
     * Method returns transactions total price
     * @return	totalPrice
     */
	public double getTotalPrice() {
		return totalPrice;
	}

	
	/**
	 * Method sets the transactions total price
	 * @param totalPrice
	 */
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	
	/**
	 * Method returns main guest attached to transaction
	 * @return	mainGuest
	 */
	public Guest getMainGuest() {
		return mainGuest;
	}

	
	/**
	 * Method sets the main guest for the transaction
	 * @param mainGuest
	 */
	public void setMainGuest(Guest mainGuest) {
		this.mainGuest = mainGuest;
	}
    
    
    
}
