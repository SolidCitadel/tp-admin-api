package solidcitadel.transitplannermanager.direction;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JpaDirectionRepository implements DirectionRepository{

    private final EntityManager em;

    public Long save(Direction direction) {
        em.persist(direction);
        return direction.getId();
    }

    public Direction findById(Long id) {
        return em.find(Direction.class, id);
    }

    public List<Direction> findAll() {
        return em.createQuery("select d from Direction d", Direction.class).getResultList();
    }
}
