import java.util.Scanner;

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
    protected Rooms[] roomsList; //maintains an account of the clinics financial records
    protected Reservation[] reservationsList; //a list that contains all animals seen at the clinic
    
    
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
        reservationsList = new Reservation[]();
        roomsList = new Rooms[]();
    }
    
}
