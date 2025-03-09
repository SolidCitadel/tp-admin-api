package solidcitadel.transitplannermanager.jpadirection.DTO;

import lombok.Data;

import java.time.LocalTime;

@Data
public class NewDirectionForm {
    String name;
    LocalTime requiredTime;
    int fare;
    Long departureStopId;
    Long arrivalStopId;
}
