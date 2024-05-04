/**
 * 
 */
public class Admin extends User{
	private int adminID;
	
    public Admin(String firstName, String lastName, String userName, String password, int adminID) {
        super(firstName, lastName, userName, password);
        this.adminID = adminID;
    }

    public Admin() {

    }

    /**
     * Method returns Admin's ID
     * @return adminID
     */
    public int getAdminID() {
        return adminID;
    }


    /**
     * Method sets clients full name
     * @param name clients full name
     */
    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }
}
