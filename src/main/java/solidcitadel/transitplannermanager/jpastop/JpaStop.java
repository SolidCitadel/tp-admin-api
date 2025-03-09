package solidcitadel.transitplannermanager.jpastop;

import jakarta.persistence.*;
import lombok.Getter;
import solidcitadel.transitplannermanager.jpadirection.JpaDirection;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stop")
@Getter
public class JpaStop {

    @Id
    @GeneratedValue
    @Column(name = "stop_id")
    private Long id;

    @OneToMany(mappedBy = "departureStop", cascade = CascadeType.ALL)
    private List<JpaDirection> directionsDeparture = new ArrayList<>();

    @OneToMany(mappedBy = "arrivalStop", cascade = CascadeType.ALL)
    private List<JpaDirection> directionsArrival = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private TransportType transportType;

    private String name;

    protected JpaStop() {}

    public JpaStop(String name, TransportType transportType) {
        this.name = name;
        this.transportType = transportType;
    }

    public void update(String name) {
        this.name = name;
    }
}
