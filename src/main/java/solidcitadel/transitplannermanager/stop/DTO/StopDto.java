package solidcitadel.transitplannermanager.stop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import solidcitadel.transitplannermanager.stop.Stop;
import solidcitadel.transitplannermanager.stop.TransportType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StopDto {
    private Long id;
    private String name;
    private TransportType transportType;

    // Stop 엔티티를 받아서 StopDto로 변환하는 정적 메서드
    public static StopDto from(Stop stop) {
        return new StopDto(stop.getId(), stop.getName(), stop.getTransportType());
    }

    public Stop toEntity(){
        return new Stop(this.getName(), this.getTransportType());
    }
} 