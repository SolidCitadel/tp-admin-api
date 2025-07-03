package solidcitadel.tp.admin.api.stop.DTO;

import lombok.Data;
import solidcitadel.tp.admin.api.stop.Stop;
import solidcitadel.tp.admin.api.stop.TransportType;

@Data
public class StopSummaryDto {
    private Long id;
    private String name;
    private TransportType transportType;

    public static StopSummaryDto from(Stop stop) {
        StopSummaryDto stopSummaryDto = new StopSummaryDto();
        stopSummaryDto.setId(stop.getId());
        stopSummaryDto.setName(stop.getName());
        stopSummaryDto.setTransportType(stop.getTransportType());
        return stopSummaryDto;
    }
} 