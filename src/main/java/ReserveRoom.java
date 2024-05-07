

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReserveRoom {
    public static boolean roomAvailability(ReservationDate wantedReservationDate, Room room){
        boolean availability = true;
        List<ReservationDate> existingReservations = room.getReservationDates();
        for(int i = 0; i < existingReservations.size(); i++){
            ReservationDate reservationDate = existingReservations.get(i);
            
            // sets the start and end dates of the wanted and preexisting reservations for a room
            LocalDate bookedStartDate = reservationDate.getStartDate();
            LocalDate bookedEndDate = reservationDate.getEndDate();
            LocalDate wantedStartDate = wantedReservationDate.getStartDate();
            LocalDate wantedEndDate = wantedReservationDate.getEndDate();
            
            // gets all the days between the wanted and preexisting reservations for a room
            List<LocalDate> bookedDays = getDaysBetweenDates(bookedStartDate, bookedEndDate);
            List<LocalDate> wantedDays = getDaysBetweenDates(wantedStartDate, wantedEndDate);
            
            // loops through all the wanted days of reservation
            for(int j = 0; j < wantedDays.size(); j++){
                LocalDate wantedDay = wantedDays.get(j);
                
                // loops through all the booked days of reservation
                for(int k = 0; k < bookedDays.size(); k++){
                    LocalDate bookedDay = bookedDays.get(k);
                    
                    // if the wanted day is already booked, then availability is set to false and returned
                    if(wantedDay.isEqual(bookedDay)){
                        availability = false;
                        return availability;
                    }
                }
            }
            
        }
        return availability;
    }
    
    public static List<LocalDate> getDaysBetweenDates(LocalDate startdate, LocalDate enddate)
    {
        List<LocalDate> dates = new ArrayList<>();
        
        while (!startdate.isAfter(enddate)) {
            dates.add(startdate);
            startdate = startdate.plusDays(1);
        }
        return dates;
    }
    
    public static List<Room> filterRooms(ReservationDate wantedReservationDate){
    	List<Room> rooms = generateRooms();
    	List<Room> availableRooms = new ArrayList<>();
    	
    	for(int i = 0; i < rooms.size(); i++){
    		boolean available = roomAvailability(wantedReservationDate, rooms.get(i));
    		
    		if(available = true) {
    			availableRooms.add(rooms.get(i));
    		}
    	}
    	
    	return availableRooms;
    }
    
    public static List<Room> generateRooms(){
    	List<Room> rooms = new ArrayList<>();
    	
    	LocalDate date1StartDate = LocalDate.parse("2024-01-01");
        LocalDate date1EndDate = LocalDate.parse("2024-02-01");
        ReservationDate reservationDateRoom1 = new ReservationDate(date1StartDate, date1EndDate);
        List<ReservationDate> reservationDates1 = new ArrayList<>();
        reservationDates1.add(reservationDateRoom1);
    	Room room1 = new Room("Single King", reservationDates1, 150.0, 2);
    	
    	LocalDate date2StartDate = LocalDate.parse("2024-01-01");
        LocalDate date2EndDate = LocalDate.parse("2024-02-01");
        ReservationDate reservationDateRoom2 = new ReservationDate(date2StartDate, date2EndDate);
        List<ReservationDate> reservationDates2 = new ArrayList<>();
        reservationDates2.add(reservationDateRoom2);
    	Room room2 = new Room("Single King", reservationDates2, 150.0, 2);
    	
    	LocalDate date3StartDate = LocalDate.parse("2024-01-01");
        LocalDate date3EndDate = LocalDate.parse("2024-02-01");
        ReservationDate reservationDateRoom3 = new ReservationDate(date3StartDate, date3EndDate);
        List<ReservationDate> reservationDates3 = new ArrayList<>();
        reservationDates3.add(reservationDateRoom3);
    	Room room3 = new Room("Single King", reservationDates3, 150.0, 2);
    	
    	LocalDate date4StartDate = LocalDate.parse("2024-01-01");
        LocalDate date4EndDate = LocalDate.parse("2024-02-01");
        ReservationDate reservationDateRoom4 = new ReservationDate(date4StartDate, date4EndDate);
        List<ReservationDate> reservationDates4 = new ArrayList<>();
        reservationDates4.add(reservationDateRoom4);
    	Room room4 = new Room("Single King", reservationDates4, 150.0, 2);
    	
    	LocalDate date5StartDate = LocalDate.parse("2024-01-01");
        LocalDate date5EndDate = LocalDate.parse("2024-02-01");
        ReservationDate reservationDateRoom5 = new ReservationDate(date5StartDate, date5EndDate);
        List<ReservationDate> reservationDates5 = new ArrayList<>();
        reservationDates5.add(reservationDateRoom5);
    	Room room5 = new Room("Single King", reservationDates5, 150.0, 2);
    	
    	LocalDate date6StartDate = LocalDate.parse("2024-01-01");
        LocalDate date6EndDate = LocalDate.parse("2024-02-01");
        ReservationDate reservationDateRoom6 = new ReservationDate(date6StartDate, date6EndDate);
        List<ReservationDate> reservationDates6 = new ArrayList<>();
        reservationDates6.add(reservationDateRoom6);
    	Room room6 = new Room("Single King", reservationDates6, 150.0, 2);
    	
    	rooms.add(room1);
    	rooms.add(room2);
    	rooms.add(room3);
    	rooms.add(room4);
    	rooms.add(room5);
    	rooms.add(room6);
    	
    	return rooms;
    }
}

