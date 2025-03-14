package solidcitadel.transitplannermanager.stop;

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
    public void save(Stop stop) {
        stopRepository.save(stop);
    }

    public Stop findById(Long id) {
        return stopRepository.findById(id);
    }

    public List<Stop> findAll() {
        return stopRepository.findAll();
    }
}
