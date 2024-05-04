
import java.time.LocalDate;

public class ReservationDate {
    // TODO: make ReservationDates singular
    private LocalDate _startDate;
    private LocalDate _endDate;
    
    // default constructor
    public ReservationDate() {}
    
    // constructor
    public ReservationDate(LocalDate startDate, LocalDate endDate) {
        this._startDate = startDate;
        this._endDate = endDate;
    }
    
    // getters
    public LocalDate getStartDate(){return _startDate;}
    public LocalDate getEndDate() {return _endDate;}
    
    // setters
    public void setStartDate(LocalDate startDate){this._startDate = startDate;}
    public void setEndDate(LocalDate endDate){this._endDate = endDate;}
    
}
