package solidcitadel.tp.admin.api.route;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RouteRepository {

    private static final Map<Long, Route> store = new HashMap<>();

    private static long sequence = 0L;

    public Route save(Route route) {
        route.setId(++sequence);
        store.put(route.getId(), route);
        return route;
    }

    public Route findById(Long id) {
        return store.get(id);
    }

    // for test
    public Optional<Route> findByName(String name) {
        return findAll().stream().filter(route -> route.getName().equals(name)).findFirst();
    }

    public List<Route> findAll(){
        return new ArrayList<>(store.values());
    }

    public Long getSequence(){
        return sequence;
    }

    public void clearStore(){
        store.clear();
    }
}
