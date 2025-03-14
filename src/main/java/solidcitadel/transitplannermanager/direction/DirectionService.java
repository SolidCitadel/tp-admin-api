package solidcitadel.transitplannermanager.direction;

import solidcitadel.transitplannermanager.direction.DTO.NewDirectionForm;

import java.util.List;
import java.util.Optional;

public interface DirectionService {
    void save(Direction direction);

    Direction findById(Long id);

    List<Direction> findAll();

    void create(NewDirectionForm newDirectionForm);
}
