package solidcitadel.tp.admin.api.direction.DTO;

import lombok.Data;

import java.time.LocalTime;

@Data
public class CreateDirectionRequest {
    private int fare;
    private LocalTime requiredTime;
    private Long departureStopId;
    private Long arrivalStopId;
}
