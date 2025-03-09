package solidcitadel.transitplannermanager.jpadirection;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import solidcitadel.transitplannermanager.jpadirection.DTO.NewDirectionForm;
import solidcitadel.transitplannermanager.jpastop.JpaStop;
import solidcitadel.transitplannermanager.jpastop.JpaStopRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JpaDirectionService {

    private final JpaDirectionRepository directionRepository;
    private final JpaStopRepository stopRepository;

    @Transactional
    public void save(JpaDirection direction) {
        directionRepository.save(direction);
    }

    public JpaDirection findById(Long id) {
        return directionRepository.findById(id);
    }

    public List<JpaDirection> findAll() {
        return directionRepository.findAll();
    }

    @Transactional
    public void create(NewDirectionForm newDirectionForm) {
        JpaStop departureStop = stopRepository.findById(newDirectionForm.getDepartureStopId());
        JpaStop arrivalStop = stopRepository.findById(newDirectionForm.getArrivalStopId());
        JpaDirection direction = new JpaDirection();

        direction.update(newDirectionForm.getName(),
                newDirectionForm.getFare(),
                newDirectionForm.getRequiredTime());
        direction.changeDepartureStop(departureStop);
        direction.changeArrivalStop(arrivalStop);

        directionRepository.save(direction);
    }
}
