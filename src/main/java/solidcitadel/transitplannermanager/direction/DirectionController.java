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
        List<Stop> stops = stopService.findAll();
        NewDirectionForm newDirectionForm = new NewDirectionForm(direction);

        model.addAttribute("stops", stops);
        model.addAttribute("newDirectionForm", newDirectionForm);
        model.addAttribute("departureTimes", direction.getDepartureTimes());
        return "directions/detail";
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

    @PatchMapping("/{directionId}")
    public String updateDirection(@PathVariable Long directionId, @ModelAttribute("directionForm") NewDirectionForm newDirectionForm) {
        directionService.update(directionId, newDirectionForm);
        return "redirect:/directions";
    }

    @DeleteMapping("/{directionId}")
    public String deleteDirection(@PathVariable Long directionId) {
        directionService.deleteById(directionId);
        return "redirect:/directions";
    }
}
