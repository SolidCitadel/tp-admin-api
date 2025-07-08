package solidcitadel.tp.admin.api.direction;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solidcitadel.tp.admin.api.direction.dto.NewDirectionForm;
import solidcitadel.tp.admin.api.stop.Stop;
import solidcitadel.tp.admin.api.stop.StopService;

import java.time.LocalTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultDirectionService implements DirectionService{

    private final DirectionRepository directionRepository;
    private final StopService stopService;

    @Transactional
    public Long save(Direction direction) {
        return directionRepository.save(direction).getId();
    }

    public Direction findById(Long id) {
        return directionRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Direction not found"));
    }

    public List<Direction> findAll() {
        return directionRepository.findAll();
    }

    // Deprecated
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
    public Long create(int fare, LocalTime requiredTime, Long departureStopId, Long arrivalStopId) {
        Stop departureStop = stopService.findById(departureStopId);
        Stop arrivalStop = stopService.findById(arrivalStopId);
        Direction direction = Direction.create(fare, requiredTime, departureStop, arrivalStop);
        return directionRepository.save(direction).getId();
    }

    @Transactional
    public void deleteById(Long id) {
        directionRepository.deleteById(id);
    }

    // Deprecated
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
    public void update(Long id, int fare, LocalTime requiredTime, Long departureStopId, Long arrivalStopId) {
        Direction target = findById(id);
        Stop departureStop = stopService.findById(departureStopId);
        Stop arrivalStop = stopService.findById(arrivalStopId);
        target.update(fare, requiredTime, departureStop, arrivalStop);
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

    @Transactional
    public void replaceDepartureTimes(Long id, List<LocalTime> departureTimes){
        Direction direction = findById(id);
        direction.replaceDepartureTimes(departureTimes);
    }
}
