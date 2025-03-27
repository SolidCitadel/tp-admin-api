package solidcitadel.transitplannermanager.direction;

import solidcitadel.transitplannermanager.direction.DTO.NewDirectionForm;

import java.time.LocalTime;
import java.util.List;

public interface DirectionService {
    void save(Direction direction);

    Direction findById(Long id);

    List<Direction> findAll();

    void create(NewDirectionForm newDirectionForm);

    void deleteById(Long id);

    void update(Long id, NewDirectionForm newDirectionForm);

    void addDepartureTime(Long id, LocalTime departureTimeId);

    void removeDepartureTime(Long id, LocalTime departureTimeId);
}
