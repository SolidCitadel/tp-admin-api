package solidcitadel.transitplannermanager.plan;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PlanRepository {

    private static final Map<Long, Plan> store = new HashMap<>();

    private static long sequence = 0L;

    public Plan save(Plan plan) {
        plan.setId(++sequence);
        store.put(plan.getId(), plan);
        return plan;
    }

    public Plan findById(Long id) {
        return store.get(id);
    }

    public List<Plan> findAll(){
        return new ArrayList<>(store.values());
    }

}
