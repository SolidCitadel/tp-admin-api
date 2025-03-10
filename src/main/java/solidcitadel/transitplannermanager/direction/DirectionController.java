package solidcitadel.transitplannermanager.direction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import solidcitadel.transitplannermanager.direction.DTO.NewDirectionForm;
import solidcitadel.transitplannermanager.stop.Stop;
import solidcitadel.transitplannermanager.stop.StopService;

import java.util.List;

@Controller
@RequestMapping("/directions")
@RequiredArgsConstructor
public class DirectionController {

    private final DirectionService directionService;
    private final StopService stopService;

    @GetMapping
    public String directions(Model model) {
        List<Direction> directions = directionService.findAll();
        model.addAttribute("directions", directions);
        return "directions/directions";
    }

    @GetMapping("/{directionId}")
    public String direction(@PathVariable Long directionId, Model model) {
        Direction direction = directionService.findById(directionId);
        model.addAttribute("direction", direction);
        return "directions/direction";
    }

    @GetMapping("/new")
    public String newDirection(Model model) {
        NewDirectionForm newDirectionForm = new NewDirectionForm();
        List<Stop> stops = stopService.findAll();
        model.addAttribute("newDirectionForm", newDirectionForm);
        model.addAttribute("stops", stops);
        return "directions/new";
    }

    @PostMapping
    public String newDirection(@ModelAttribute("directionForm") NewDirectionForm newDirectionForm) {
        directionService.create(newDirectionForm);
        return "redirect:/directions";
    }
}
