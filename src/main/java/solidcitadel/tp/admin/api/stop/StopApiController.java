package solidcitadel.tp.admin.api.stop;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import solidcitadel.tp.admin.api.stop.DTO.CreateStopRequest;
import solidcitadel.tp.admin.api.stop.DTO.StopDetailDto;
import solidcitadel.tp.admin.api.stop.DTO.StopSummaryDto;

@RestController
@RequestMapping("/api/stops")
@RequiredArgsConstructor
public class StopApiController {
    private final StopService stopService;

    @GetMapping
    public List<StopSummaryDto> getStops(){
        return stopService.findAll().stream()
            .map(StopSummaryDto::from)
            .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createStop(@RequestBody CreateStopRequest createStopRequest) {
        return stopService.save(createStopRequest.toEntity());
    }
    
    @GetMapping("/{stopId}")
    public StopDetailDto getStopDetail(@PathVariable("stopId") Long stopId) {
        return StopDetailDto.from(stopService.findById(stopId));
    }

    @PutMapping("/{stopId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStop(@PathVariable("stopId") Long stopId, @RequestBody CreateStopRequest createStopRequest) {
        stopService.updateField(stopId, createStopRequest.toEntity());
    }

    @DeleteMapping("/{stopId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStop(@PathVariable("stopId") Long stopId) {
        stopService.deleteById(stopId);
    }
}