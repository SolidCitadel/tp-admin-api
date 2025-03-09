package solidcitadel.transitplannermanager.jpadirection;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import solidcitadel.transitplannermanager.jpadirection.DTO.NewDirectionForm;
import solidcitadel.transitplannermanager.jpastop.JpaStop;
import solidcitadel.transitplannermanager.jpastop.JpaStopService;

import java.util.List;

@Controller
@RequestMapping("/directions")
@RequiredArgsConstructor
public class JpaDirectionController {

    private final JpaDirectionService directionService;
    private final JpaStopService stopService;

    @GetMapping
    public String directions(Model model) {
        List<JpaDirection> directions = directionService.findAll();
        model.addAttribute("directions", directions);
        return "directions/directions";
    }

    @GetMapping("/{directionId}")
    public String direction(@PathVariable Long directionId, Model model) {
        JpaDirection direction = directionService.findById(directionId);
        model.addAttribute("direction", direction);
        return "directions/direction";
    }

    @GetMapping("/new")
    public String newDirection(Model model) {
        NewDirectionForm newDirectionForm = new NewDirectionForm();
        List<JpaStop> stops = stopService.findAll();
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
