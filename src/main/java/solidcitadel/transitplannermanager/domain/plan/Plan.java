package solidcitadel.transitplannermanager.domain.plan;

import lombok.Data;
import solidcitadel.transitplannermanager.domain.route.Route;

import java.util.ArrayList;

@Data
public class Plan {
    private Long id;
    private String name;
    private ArrayList<Route> routes;

    public Plan(String name, ArrayList<Route> routes) {
        this.name = name;
        this.routes = routes;
    }
}
