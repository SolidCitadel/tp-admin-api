package solidcitadel.tp.admin.api.stop.DTO;

import lombok.Data;
import solidcitadel.tp.admin.api.stop.Stop;
import solidcitadel.tp.admin.api.stop.TransportType;

@Data
public class CreateStopRequest {
    private String name;
    private TransportType transportType;

    public Stop toEntity(){
        return new Stop(this.getName(), this.getTransportType());
    }
} 