package solidcitadel.transitplannermanager.web.transport.stop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import solidcitadel.transitplannermanager.domain.transport.stop.JdbcStopRepository;
import solidcitadel.transitplannermanager.domain.transport.stop.Stop;

import java.util.List;

@Controller
@RequestMapping("/stops")
@RequiredArgsConstructor
public class StopController {
    private final JdbcStopRepository stopRepository;

    @GetMapping
    public String stops(Model model){
        List<Stop> stops = stopRepository.findAll();
        model.addAttribute("stops", stops);
        return "stops/stops";
    }

    @GetMapping("/add")
    public String addStopForm(){
        return "stops/addStopForm";
    }

    @PostMapping("/add")
    public String addStop(Stop stop) {
        stopRepository.save(stop);
        return "redirect:/stops";
    }

    @GetMapping("/delete")
    public String deleteStop() {
        return "stops/deleteStopForm";
    }
    @PostMapping("/delete")
    public String deleteStopForm(Stop stop) {
        stopRepository.delete(stop.getId());
        return "redirect:/stops";
    }
}

