package solidcitadel.transitplannermanager.stop;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import solidcitadel.transitplannermanager.stop.DTO.StopDetailDto;
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

    @PostMapping
    public Long createStop(@RequestBody StopDto stopDto) {
        return stopService.save(stopDto.toEntity());
    }
    
    @GetMapping("/{stopId}")
    public StopDetailDto getStopDetail(@PathVariable("stopId") Long stopId) {
        return StopDetailDto.from(stopService.findById(stopId));
    }

    @PutMapping("/{stopId}")
    public void updateStop(@PathVariable("stopId") Long stopId, @RequestBody StopDto stopDto) {
        stopService.updateField(stopId, stopDto.toEntity());
    }

    @DeleteMapping("/{stopId}")
    public void deleteStop(@PathVariable("stopId") Long stopId) {
        stopService.deleteById(stopId);
    }
}