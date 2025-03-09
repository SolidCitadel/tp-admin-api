package solidcitadel.transitplannermanager.jpastop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JpaStopService {

    private final JpaStopRepository stopRepository;

    @Transactional
    public void save(JpaStop stop) {
        stopRepository.save(stop);
    }

    public JpaStop findById(Long id) {
        return stopRepository.findById(id);
    }

    public List<JpaStop> findAll() {
        return stopRepository.findAll();
    }
}
