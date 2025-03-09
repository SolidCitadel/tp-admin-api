package solidcitadel.transitplannermanager.jpastop;

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
public class JpaStopController {

    private final JpaStopService jpaStopService;

    @GetMapping
    public String stopList(Model model) {
        List<JpaStop> stops = jpaStopService.findAll();
        model.addAttribute("stops", stops);
        return "stops/stops";
    }

    @GetMapping("/new")
    public String newStop(Model model) {
        JpaStop stop = new JpaStop();
        model.addAttribute("stop", stop);
        model.addAttribute("transportTypes", TransportType.values());
        return "stops/new";
    }

    @PostMapping
    public String newStop(@ModelAttribute("stop") JpaStop stop) {
        jpaStopService.save(stop);
        return "redirect:/stops";
    }

}
