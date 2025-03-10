package solidcitadel.transitplannermanager.stop;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaStopRepository implements StopRepository {

    private final EntityManager em;

    public Long save(Stop stop) {
        em.persist(stop);
        return stop.getId();
    }

    public Stop findById(Long id) {
        return em.find(Stop.class, id);
    }

    public List<Stop> findAll() {
        return em.createQuery("select s from Stop s", Stop.class).getResultList();
    }

}
