package solidcitadel.transitplannermanager.jpadirection;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaDirectionRepository {

    private final EntityManager em;

    public Long save(JpaDirection direction) {
        em.persist(direction);
        return direction.getId();
    }

    public JpaDirection findById(Long id) {
        return em.find(JpaDirection.class, id);
    }

    List<JpaDirection> findAll() {
        return em.createQuery("select d from JpaDirection d", JpaDirection.class).getResultList();
    }
}
