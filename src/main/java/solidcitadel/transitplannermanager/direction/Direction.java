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

    private String name;
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

    public Direction(String name, int fare, LocalTime requiredTime, Stop departureStop, Stop arrivalStop) {
        this.name = name;
        this.fare = fare;
        this.requiredTime = requiredTime;
        changeDepartureStop(departureStop);
        changeArrivalStop(arrivalStop);
    }

    public void update(String name, int fare, LocalTime requiredTime) {
        this.name = name;
        this.fare = fare;
        this.requiredTime = requiredTime;
    }

    //== 연관관계 메서드 ==//
    public void changeDepartureStop(Stop departureStop) {
        this.departureStop = departureStop;
        departureStop.getDirectionsDeparture().add(this);
    }

    public void changeArrivalStop(Stop arrivalStop) {
        this.arrivalStop = arrivalStop;
        arrivalStop.getDirectionsArrival().add(this);
    }

}
