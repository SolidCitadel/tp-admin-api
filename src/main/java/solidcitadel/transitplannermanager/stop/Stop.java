package solidcitadel.transitplannermanager.stop;

import lombok.Data;

@Data
public class Stop {
    private Long id;
    private String name;

    public Stop() {
    }

    public Stop(String name) {
        this.name = name;
    }
}
