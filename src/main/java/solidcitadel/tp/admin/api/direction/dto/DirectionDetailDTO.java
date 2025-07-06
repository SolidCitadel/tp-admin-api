package solidcitadel.tp.admin.api.direction.dto;

import lombok.Data;
import solidcitadel.tp.admin.api.direction.Direction;
import solidcitadel.tp.admin.api.stop.dto.StopSummaryDto;

import java.time.LocalTime;
import java.util.List;

@Data
public class DirectionDetailDTO {
    private Long id;
    private int fare;
    private LocalTime requiredTime;
    private StopSummaryDto departureStop;
    private StopSummaryDto arrivalStop;
    private List<LocalTime> departureTimes;

    public static DirectionDetailDTO from(Direction direction) {
        DirectionDetailDTO dto = new DirectionDetailDTO();
        dto.setId(direction.getId());
        dto.setFare(direction.getFare());
        dto.setRequiredTime(direction.getRequiredTime());
        dto.setDepartureStop(StopSummaryDto.from(direction.getDepartureStop()));
        dto.setArrivalStop(StopSummaryDto.from(direction.getArrivalStop()));
        dto.setDepartureTimes(direction.getDepartureTimes());
        return dto;
    }
}
