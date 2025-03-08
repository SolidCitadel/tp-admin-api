package solidcitadel.transitplannermanager.direction;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DirectionRepository {
    private static final Map<Long, Direction> store = new HashMap<>();

    private static long sequence = 0L;

    public Direction save(Direction direction) {
        direction.setId(++sequence);
        store.put(direction.getId(), direction);
        return direction;
    }

    public Direction findById(Long id) {
        return store.get(id);
    }

    public List<Direction> findAll(){
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
