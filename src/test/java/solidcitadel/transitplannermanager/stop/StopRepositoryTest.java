package solidcitadel.transitplannermanager.stop;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class StopRepositoryTest {

    @Autowired
    JpaStopRepository stopRepository;

    @Test
    @Transactional
    @Rollback
    void save_and_find() {
        Stop stop = new Stop("stop", TransportType.BUS);

        Long savedId = stopRepository.save(stop);

        Stop foundStop = stopRepository.findById(savedId);

        assertEquals(foundStop.getId(), stop.getId());
        assertEquals(foundStop.getName(), stop.getName());
        assertEquals(foundStop.getTransportType(), stop.getTransportType());
        assertEquals(foundStop, stop);
    }
}