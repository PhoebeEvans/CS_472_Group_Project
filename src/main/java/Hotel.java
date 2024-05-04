import java.util.List;

/**
 *
 * @author Vince Gnodle
 * Class creates a veterinary clinic that stores an inventory, transaction records,
 * a patient registry, and multiple Calendars for different treatment types.
 */
public class Hotel{
    
    private final String hotelName;
    private final String hotelAddress;
    private final String hotelPhoneNumber;
    protected List<Room> roomsList; //maintains an account of the hotels rooms list
    
    
    /**
     * Clinic constructor creates the clinic object and initializes the name as
     * the given parameter. Also initializes an empty registry, inventory, financial
     * report, and calendars.
     * @param name name of the clinic
     */
    Hotel(String name, String address, String phoneNumber){
        this.hotelName = name;
        this.hotelAddress = address;
        this.hotelPhoneNumber = phoneNumber;
        roomsList = ReserveRoom.generateRooms();
    }


	/**
	 * @return the hotelName
	 */
	public String getHotelName() {
		return hotelName;
	}


	/**
	 * @return the hotelAddress
	 */
	public String getHotelAddress() {
		return hotelAddress;
	}


	/**
	 * @return the hotelPhoneNumber
	 */
	public String getHotelPhoneNumber() {
		return hotelPhoneNumber;
	}
	
	
	public void manageRooms() {
		
	}
    
	
	public void searchAvailableRooms() {
		
	}
	
	
	public void createReservation() {
		
	}
	
	
	public void manageReservation() {
		
	}
	
	
	public void cancelReservation() {
		
	}
}
