package solidcitadel.tp.admin.api.route;

import jakarta.persistence.*;
import lombok.*;
import solidcitadel.tp.admin.api.direction.Direction;

import java.time.LocalTime;

@Entity
@Getter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "segment")
public class Segment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direction_id")
    private Direction direction;

    @Column(nullable = false)
    private LocalTime departureTime;

    @Setter(AccessLevel.PACKAGE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "route_id")
    private Route route;

}
