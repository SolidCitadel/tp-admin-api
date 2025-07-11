package solidcitadel.tp.admin.api.route;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteRepository routeRepository;
    @GetMapping("/{routeId}/editStops")
    public String directions(@PathVariable Long routeId, Model model) {
        Route route = routeRepository.findById(routeId);
        model.addAttribute("route", route);
        return "routes/editStopsForm";
    }
}

