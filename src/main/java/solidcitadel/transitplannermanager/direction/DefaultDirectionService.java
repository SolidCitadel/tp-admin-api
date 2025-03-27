package solidcitadel.transitplannermanager.direction;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solidcitadel.transitplannermanager.direction.DTO.NewDirectionForm;
import solidcitadel.transitplannermanager.stop.Stop;
import solidcitadel.transitplannermanager.stop.StopService;

import java.time.LocalTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultDirectionService implements DirectionService{

    private final DirectionRepository directionRepository;
    private final StopService stopService;

    @Transactional
    public void save(Direction direction) {
        directionRepository.save(direction);
    }

    public Direction findById(Long id) {
        return directionRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Direction not found"));
    }

    public List<Direction> findAll() {
        return directionRepository.findAll();
    }

    @Transactional
    public void create(NewDirectionForm newDirectionForm) {
        Stop departureStop = stopService.findById(newDirectionForm.getDepartureStopId());
        Stop arrivalStop = stopService.findById(newDirectionForm.getArrivalStopId());

        Direction direction = Direction.create(
                newDirectionForm.getFare(),
                newDirectionForm.getRequiredTime(),
                departureStop,
                arrivalStop);

        directionRepository.save(direction);
    }

    @Transactional
    public void deleteById(Long id) {
        directionRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, NewDirectionForm newDirectionForm) {
        Direction direction = findById(id);
        Stop departureStop = stopService.findById(newDirectionForm.getDepartureStopId());
        Stop arrivalStop = stopService.findById(newDirectionForm.getArrivalStopId());

        direction.update(
                newDirectionForm.getFare(),
                newDirectionForm.getRequiredTime(),
                departureStop,
                arrivalStop);
    }

    @Transactional
    public void addDepartureTime(Long id, LocalTime departureTime){
        Direction direction = findById(id);
        direction.addDepartureTime(departureTime);
    }

    @Transactional
    public void removeDepartureTime(Long id, LocalTime departureTime){
        Direction direction = findById(id);
        direction.removeDepartureTime(departureTime);
    }
}
