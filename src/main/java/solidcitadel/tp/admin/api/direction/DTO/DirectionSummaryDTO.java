package solidcitadel.tp.admin.api.direction.DTO;

import lombok.Data;
import solidcitadel.tp.admin.api.direction.Direction;
import solidcitadel.tp.admin.api.stop.DTO.StopSummaryDto;

import java.time.LocalTime;

@Data
public class DirectionSummaryDTO {
    private Long id;
    private int fare;
    private LocalTime requiredTime;
    private StopSummaryDto departureStop;
    private StopSummaryDto arrivalStop;

    public static DirectionSummaryDTO from(Direction direction) {
        DirectionSummaryDTO dto = new DirectionSummaryDTO();
        dto.setId(direction.getId());
        dto.setFare(direction.getFare());
        dto.setRequiredTime(direction.getRequiredTime());
        dto.setDepartureStop(StopSummaryDto.from(direction.getDepartureStop()));
        dto.setArrivalStop(StopSummaryDto.from(direction.getArrivalStop()));
        return dto;
    }
}
