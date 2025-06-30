package solidcitadel.tp.admin.api.stop;

import java.util.List;

public interface StopService {
    Long save(Stop stop);

    Stop findById(Long id);

    List<Stop> findAll();

    void deleteById(Long id);

    void updateField(Long id, Stop stop);
}
