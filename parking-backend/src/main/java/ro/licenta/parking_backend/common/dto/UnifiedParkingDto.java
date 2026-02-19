package ro.licenta.parking_backend.common.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;
import java.util.Map;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UnifiedParkingDto {
    private String id;
    private String source;     // PUBLIC / CUSTOM
    private String name;
    private Double lat;
    private Double lon;
    private Integer capacity;
    private Integer freeSpots; // null dacă NA/necunoscut
    private Map<String, Object> details;
}
