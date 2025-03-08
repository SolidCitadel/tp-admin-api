package solidcitadel.transitplannermanager.ticket;

import lombok.Data;
import solidcitadel.transitplannermanager.ticket.transport.Time;
import solidcitadel.transitplannermanager.ticket.transport.Type;
import solidcitadel.transitplannermanager.stop.Stop;

@Data
public class Ticket {
    private Long id;
    private Type type;
    private Stop departure;
    private Stop destination;
    private Time departureTime;
    private Time requiredTime;
    private Integer fare;

    public Ticket(Type type, Stop departure, Stop destination, Time departureTime, Time requiredTime, Integer fare) {
        this.type = type;
        this.departure = departure;
        this.destination = destination;
        this.departureTime = departureTime;
        this.requiredTime = requiredTime;
        this.fare = fare;
    }
}
