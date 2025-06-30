package solidcitadel.tp.admin.api.stop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/stops")
@RequiredArgsConstructor
public class StopController {

    private final StopService stopService;

    @GetMapping
    public String stopList(Model model) {
        List<Stop> stops = stopService.findAll();
        model.addAttribute("stops", stops);
        return "stops/stops";
    }

    @GetMapping("/new")
    public String newStop(Model model) {
        Stop stop = new Stop();
        model.addAttribute("stop", stop);
        model.addAttribute("transportTypes", TransportType.values());
        return "stops/new";
    }

    @PostMapping
    public String newStop(@ModelAttribute("stop") Stop stop) {
        stopService.save(stop);
        return "redirect:/stops";
    }

    @GetMapping("/{stopId}")
    public String viewStop(@PathVariable("stopId") Long stopId, Model model) {
        Stop stop = stopService.findById(stopId);
        model.addAttribute("stop", stop);
        return "stops/detail";
    }

    @PatchMapping("/{stopId}")
    public String editStop(@PathVariable Long stopId, @ModelAttribute("stop") Stop stop) {
        stopService.updateField(stopId, stop);
        return "redirect:/stops";
    }

    @DeleteMapping("/{stopId}")
    public String deleteStop(@PathVariable("stopId") Long stopId) {
        stopService.deleteById(stopId);
        return "redirect:/stops";
    }


}
