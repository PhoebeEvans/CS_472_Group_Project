

import java.util.List;

public class Room {
    private String _type;
    private List<ReservationDate> _reservationDates;
    private double _cost;
    private int _sleepCount;
    
    // default constructor
    public Room() {}
    
    // constructor
    public Room(String type, List<ReservationDate> reservationDates, double cost, int sleepCount){
        this._type = type;
        this._reservationDates = reservationDates;
        this._cost = cost;
        this._sleepCount = sleepCount;
    }
    
    // getters
    public String getType(){return _type;}
    public List<ReservationDate> getReservationDates(){return _reservationDates;}
    public double getCost(){return _cost;}
    public int getSleepCount(){return _sleepCount;}
    
        
    // setters
    public void setType(String type){this._type = type;}
    public void setReservationDates(List<ReservationDate> reservationDates){this._reservationDates = reservationDates;}
    public void setCost(double cost){this._cost = cost;}
    public void setSleepCount(int sleepCount){this._sleepCount = sleepCount;}
   
}

