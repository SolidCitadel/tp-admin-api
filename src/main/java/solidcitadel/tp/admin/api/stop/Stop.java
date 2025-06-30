package solidcitadel.tp.admin.api.stop;

import jakarta.persistence.*;
import lombok.Getter;
import solidcitadel.tp.admin.api.direction.Direction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stop")
@Getter
public class Stop {

    @Id
    @GeneratedValue
    @Column(name = "stop_id")
    private Long id;

    @OneToMany(mappedBy = "departureStop", cascade = CascadeType.ALL)
    private List<Direction> directionsDeparture = new ArrayList<>();

    @OneToMany(mappedBy = "arrivalStop", cascade = CascadeType.ALL)
    private List<Direction> directionsArrival = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private TransportType transportType;

    private String name;

    protected Stop() {}

    public Stop(String name, TransportType transportType) {
        this.name = name;
        this.transportType = transportType;
    }

    public void update(String name) {
        this.name = name;
    }
}
