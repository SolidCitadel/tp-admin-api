package solidcitadel.transitplannermanager.stop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
