package solidcitadel.tp.admin.api.direction;

import solidcitadel.tp.admin.api.direction.dto.NewDirectionForm;

import java.time.LocalTime;
import java.util.List;

public interface DirectionService {
    Long save(Direction direction);

    Direction findById(Long id);

    List<Direction> findAll();

    void create(NewDirectionForm newDirectionForm);
    Long create(int fare, LocalTime requiredTime, Long departureStopId, Long arrivalStopId);

    void deleteById(Long id);

    void update(Long id, NewDirectionForm newDirectionForm);
    void update(Long id, int fare, LocalTime requiredTime, Long departureStopId, Long arrivalStopId);

    void addDepartureTime(Long id, LocalTime departureTime);

    void removeDepartureTime(Long id, LocalTime departureTime);
}
