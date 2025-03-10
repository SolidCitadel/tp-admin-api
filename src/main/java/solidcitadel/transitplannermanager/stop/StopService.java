package solidcitadel.transitplannermanager.stop;

import java.util.List;

public interface StopService {
    void save(Stop stop);

    Stop findById(Long id);

    List<Stop> findAll();
}
