package solidcitadel.transitplannermanager.stop.DTO;

import java.util.List;

import lombok.Data;
import solidcitadel.transitplannermanager.direction.Direction;
import solidcitadel.transitplannermanager.stop.Stop;
import solidcitadel.transitplannermanager.stop.TransportType;

@Data
public class StopDetailDto {
    private Long id;
    private String name;
    private TransportType transportType;
    private List<Long> departureDirectionIds;
    private List<Long> arrivalDirectionIds;

    public StopDetailDto(Long id, String name, TransportType transportType,
            List<Direction> departureDirections, List<Direction> arrivalDirections) {
        this.id = id;
        this.name = name;
        this.transportType = transportType;
        this.departureDirectionIds = departureDirections.stream()
            .map(Direction::getId)
            .toList();
        this.arrivalDirectionIds = arrivalDirections.stream()
            .map(Direction::getId)
            .toList();
    }

    // Stop 엔티티를 받아서 StopDetailDto로 변환하는 정적 메서드
    public static StopDetailDto from(Stop stop) {
        return new StopDetailDto(stop.getId(), stop.getName(), stop.getTransportType(), stop.getDirectionsDeparture(), stop.getDirectionsArrival());
    }
}
