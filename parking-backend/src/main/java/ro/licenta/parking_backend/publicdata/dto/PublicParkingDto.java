package ro.licenta.parking_backend.publicdata.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublicParkingDto {

    @JsonProperty("denumire")
    private String name;

    @JsonProperty("lat")
    private String lat;

    @JsonProperty("lon")
    private String lon;

    @JsonProperty("capacitate")
    private Integer capacity;

    @JsonProperty("locuri_libere")
    private Object freeSpots; // poate fi număr sau "NA"

    @JsonProperty("detalii")
    private Object details; // îl ținem generic deocamdată
}