package solidcitadel.transitplannermanager.jpadirection;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import solidcitadel.transitplannermanager.jpastop.JpaStop;
import solidcitadel.transitplannermanager.jpastop.JpaStopRepository;
import solidcitadel.transitplannermanager.jpastop.TransportType;

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class JpaDirectionRepositoryTest {

    @Autowired
    private JpaDirectionRepository directionRepository;

    @Autowired
    private JpaStopRepository stopRepository;

    @Test
    @Transactional
    @Rollback
    void save_and_find() {
        LocalTime requiredTime = LocalTime.of(2, 10);
        JpaStop departureStop = new JpaStop("departure", TransportType.BUS);
        JpaStop arrivalStop = new JpaStop("arrival", TransportType.BUS);
        stopRepository.save(departureStop);
        stopRepository.save(arrivalStop);

        JpaDirection direction = new JpaDirection("direction", 12000, requiredTime, departureStop, arrivalStop);
        Long savedId = directionRepository.save(direction);

        JpaDirection foundDirection = directionRepository.findById(savedId);

        assertEquals(foundDirection.getId(), direction.getId());
        assertEquals(foundDirection.getName(), direction.getName());
        assertEquals(foundDirection.getFare(), direction.getFare());
        assertEquals(foundDirection.getArrivalStop().getId(), direction.getArrivalStop().getId());
        assertEquals(foundDirection.getDepartureStop().getId(), direction.getDepartureStop().getId());
        assertEquals(foundDirection.getDepartureTimes(), direction.getDepartureTimes());
        assertEquals(foundDirection, direction);
    }
}