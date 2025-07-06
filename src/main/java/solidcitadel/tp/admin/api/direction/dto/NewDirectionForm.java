package solidcitadel.tp.admin.api.direction.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import solidcitadel.tp.admin.api.direction.Direction;

import java.time.LocalTime;

@Data
public class NewDirectionForm {
    private int fare;
    private Long departureStopId;
    private Long arrivalStopId;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime requiredTime;

    public NewDirectionForm() {}

    public NewDirectionForm(Direction direction) {
        this.requiredTime = direction.getRequiredTime();
        this.fare = direction.getFare();
        this.departureStopId = direction.getDepartureStop().getId();
        this.arrivalStopId = direction.getArrivalStop().getId();
    }
}
