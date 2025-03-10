package solidcitadel.transitplannermanager.direction;

import java.util.List;

public interface DirectionRepository {

    Long save(Direction direction);

    Direction findById(Long id);

    List<Direction> findAll();
}
