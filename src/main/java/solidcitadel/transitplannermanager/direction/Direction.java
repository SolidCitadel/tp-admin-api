package solidcitadel.transitplannermanager.direction;

import lombok.Data;
import solidcitadel.transitplannermanager.ticket.transport.Time;
import solidcitadel.transitplannermanager.stop.Stop;

import java.util.ArrayList;

@Data
public class Direction {
    private Long id;
    private String type;
    private Stop departure;
    private Stop destination;
    private Time requiredTime;
    private Integer fare;
    private ArrayList<Time> departureTimes;

    public Direction() {
    }

    public Direction(String type, Stop departure, Stop destination, Time requiredTime, Integer fare, ArrayList<Time> departureTimes) {
        this.type = type;
        this.departure = departure;
        this.destination = destination;
        this.requiredTime = requiredTime;
        this.fare = fare;
        this.departureTimes = departureTimes;
    }

    public java.lang.String StopsToString(){
        return departure.toString() + " - " + destination.toString();
    }
}
