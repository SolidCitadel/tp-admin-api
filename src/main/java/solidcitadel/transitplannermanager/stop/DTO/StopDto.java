package solidcitadel.transitplannermanager.stop.DTO;

import lombok.Data;
import solidcitadel.transitplannermanager.stop.Stop;
import solidcitadel.transitplannermanager.stop.TransportType;

@Data
public class StopDto {
    private Long id;
    private String name;
    private TransportType transportType;

    public StopDto(Long id, String name, TransportType transportType) {
        this.id = id;
        this.name = name;
        this.transportType = transportType;
    }

    // Stop 엔티티를 받아서 StopDto로 변환하는 정적 메서드
    public static StopDto from(Stop stop) {
        return new StopDto(stop.getId(), stop.getName(), stop.getTransportType());
    }
} 