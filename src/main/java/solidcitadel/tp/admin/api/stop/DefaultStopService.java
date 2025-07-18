package solidcitadel.tp.admin.api.stop;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultStopService implements StopService {

    private final StopRepository stopRepository;

    @Transactional
    public Long save(Stop stop) {
        return stopRepository.save(stop).getId();
    }

    public Stop findById(Long id) {
        return stopRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Stop not found"));
    }

    public List<Stop> findAll() {
        return stopRepository.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        stopRepository.deleteById(id);
    }

    @Transactional
    public void updateField(Long stopId, Stop stop) {
        Stop findStop = findById(stopId);
        findStop.update(stop.getName());
    }
}
