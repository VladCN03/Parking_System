package ro.licenta.parking_backend.parking.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CreateParkingRequest {

    @NotBlank
    private String name;

    private String address;

    @NotNull
    private Double lat;

    @NotNull
    private Double lon;

    @NotNull
    @Min(1)
    private Integer capacity;

    @Min(0)
    private Integer freeSpots;

    private String detailsJson;
}
