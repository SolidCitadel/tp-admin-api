package solidcitadel.transitplannermanager.direction;

import jakarta.persistence.*;
import lombok.Getter;
import solidcitadel.transitplannermanager.stop.Stop;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "direction")
@Getter
public class Direction {

    @Id
    @GeneratedValue
    @Column(name = "direction_id")
    private Long id;

    private int fare;
    private LocalTime requiredTime;

    @ElementCollection
    @CollectionTable(name = "departure_time", joinColumns = @JoinColumn(name = "direction_id"))
    private List<LocalTime> departureTimes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departure_stop_id")
    private Stop departureStop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arrival_stop_id")
    private Stop arrivalStop;

    protected Direction() {}

    private Direction(int fare, LocalTime requiredTime, Stop departureStop, Stop arrivalStop) {
        this.fare = fare;
        this.requiredTime = requiredTime;
        changeDepartureStop(departureStop);
        changeArrivalStop(arrivalStop);
    }

    public static Direction create(int fare, LocalTime requiredTime, Stop departureStop, Stop arrivalStop) {
        return new Direction(fare, requiredTime, departureStop, arrivalStop);
    }

    public void update(int fare, LocalTime requiredTime, Stop departureStop, Stop arrivalStop) {
        this.fare = fare;
        this.requiredTime = requiredTime;
        changeDepartureStop(departureStop);
        changeArrivalStop(arrivalStop);
    }

    public void addDepartureTime(LocalTime departureTime) {
        this.departureTimes.add(departureTime);
    }

    public void removeDepartureTime(LocalTime departureTime) {
        this.departureTimes.remove(departureTime);
    }

    //== 연관관계 메서드 ==//
    public void changeDepartureStop(Stop departureStop) {
        if (this.departureStop != null) {
            this.departureStop.getDirectionsDeparture().remove(this);
        }

        this.departureStop = departureStop;

        if (!departureStop.getDirectionsDeparture().contains(this))
            departureStop.getDirectionsDeparture().add(this);
    }

    public void changeArrivalStop(Stop arrivalStop) {
        if (this.arrivalStop != null) {
            this.arrivalStop.getDirectionsArrival().remove(this);
        }

        this.arrivalStop = arrivalStop;

        if (!arrivalStop.getDirectionsArrival().contains(this))
            arrivalStop.getDirectionsArrival().add(this);
    }

}
