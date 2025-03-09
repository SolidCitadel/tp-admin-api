package solidcitadel.transitplannermanager.jpastop;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaStopRepository {

    private final EntityManager em;

    public Long save(JpaStop stop) {
        em.persist(stop);
        return stop.getId();
    }

    public JpaStop findById(Long id) {
        return em.find(JpaStop.class, id);
    }

    public List<JpaStop> findAll() {
        return em.createQuery("select s from JpaStop s", JpaStop.class).getResultList();
    }

}
