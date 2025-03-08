package solidcitadel.transitplannermanager.stop;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class StopRepository {
    private static final Map<Long, Stop> store = new HashMap<>();

    private static long sequence = 0L;

    public Stop save(Stop stop) {
        stop.setId(++sequence);
        store.put(stop.getId(), stop);
        return stop;
    }

    public Stop findById(Long id) {
        return store.get(id);
    }

    // for test
    public Optional<Stop> findByName(String name) {
        return findAll().stream().filter(stop -> stop.getName().equals(name)).findFirst();
    }

    public List<Stop> findAll(){
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
