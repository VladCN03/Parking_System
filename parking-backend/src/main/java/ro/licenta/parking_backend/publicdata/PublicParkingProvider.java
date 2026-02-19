package ro.licenta.parking_backend.publicdata;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ro.licenta.parking_backend.publicdata.dto.PublicParkingDto;

import java.util.Arrays;
import java.util.List;

@Service
public class PublicParkingProvider {

    private static final String URL = "https://data.e-primariaclujnapoca.ro/sitpark.json";
    private final RestTemplate restTemplate = new RestTemplate();

    @Cacheable("publicParkings")
    public List<PublicParkingDto> fetch() {
        PublicParkingDto[] arr = restTemplate.getForObject(URL, PublicParkingDto[].class);
        return arr == null ? List.of() : Arrays.asList(arr);
    }
}
