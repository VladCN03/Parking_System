package ro.licenta.parking_backend.parking.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ParkingResponse {
    private Long id;
    private Long ownerId;
    private String name;
    private String address;
    private Double lat;
    private Double lon;
    private Integer capacity;
    private Integer freeSpots;
    private String detailsJson;
    private boolean active;
    private LocalDateTime updatedAt;
}
