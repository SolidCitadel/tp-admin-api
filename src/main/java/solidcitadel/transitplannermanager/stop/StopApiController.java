package solidcitadel.transitplannermanager.stop;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import solidcitadel.transitplannermanager.stop.DTO.StopDto;

@RestController
@RequestMapping("/api/stops")
@RequiredArgsConstructor
public class StopApiController {
    private final StopService stopService;

    @GetMapping
    public List<StopDto> getStops(){
        return stopService.findAll().stream()
            .map(StopDto::from)
            .toList();
    }
    
}