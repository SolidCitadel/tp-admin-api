package solidcitadel.tp.admin.api.direction;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import solidcitadel.tp.admin.api.stop.Stop;
import solidcitadel.tp.admin.api.stop.StopRepository;
import solidcitadel.tp.admin.api.stop.TransportType;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DirectionRepositoryTest {
    private final DirectionRepository directionRepository;
    private final StopRepository stopRepository;
    @Autowired
    public DirectionRepositoryTest(DirectionRepository directionRepository,
                                   StopRepository stopRepository) {
        this.directionRepository = directionRepository;
        this.stopRepository = stopRepository;
    }

    @Test
    @Transactional
    @Rollback
    void save_and_find() {
        LocalTime requiredTime = LocalTime.of(2, 10);
        Stop departureStop = new Stop("departure", TransportType.BUS);
        Stop arrivalStop = new Stop("arrival", TransportType.BUS);
        stopRepository.save(departureStop);
        stopRepository.save(arrivalStop);

        Direction direction = Direction.create(12000, requiredTime, departureStop, arrivalStop);
        Long savedId = directionRepository.save(direction).getId();

        Direction foundDirection = directionRepository.findById(savedId).orElseThrow();

        assertEquals(foundDirection.getId(), direction.getId());
        assertEquals(foundDirection.getFare(), direction.getFare());
        assertEquals(foundDirection.getArrivalStop().getId(), direction.getArrivalStop().getId());
        assertEquals(foundDirection.getDepartureStop().getId(), direction.getDepartureStop().getId());
        assertEquals(foundDirection.getDepartureTimes(), direction.getDepartureTimes());
        assertEquals(foundDirection, direction);
    }
}