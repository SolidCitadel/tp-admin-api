package solidcitadel.transitplannermanager.domain.route;

import lombok.Data;
import solidcitadel.transitplannermanager.domain.transport.Time;
import solidcitadel.transitplannermanager.domain.transport.direction.Direction;
import solidcitadel.transitplannermanager.domain.transport.stop.Stop;
import solidcitadel.transitplannermanager.domain.transport.ticket.Ticket;

import java.util.ArrayList;

@Data
public class Route {
    private Long id;
    private String name;
    private ArrayList<Stop> stops;
    private ArrayList<Direction> directions;
    private ArrayList<Ticket> tickets;
    private Time departure;
    private Time arrival;
    private Integer fee;

    public Route(String name) {
        this.name = name;
//        this.stops = new ArrayList<Stop>();
//        this.destinations = destinations;
//        this.tickets = tickets;
//        this.departure = departure;
//        this.arrival = arrival;
        this.fee = 0;
    }
}
