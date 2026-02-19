package ro.licenta.parking_backend.parking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.licenta.parking_backend.common.dto.UnifiedParkingDto;
import ro.licenta.parking_backend.publicdata.PublicParkingProvider;
import ro.licenta.parking_backend.publicdata.dto.PublicParkingDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ParkingAggregationService {

    private final PublicParkingProvider publicProvider;
    private final CustomParkingRepository customRepo;
    private final ObjectMapper objectMapper; // Spring îl oferă automat

    private Integer parseFreeSpots(Object val) {
        if (val == null) return null;
        if (val instanceof Number n) return n.intValue();
        if (val instanceof String s) {
            if (s.equalsIgnoreCase("NA")) return null;
            try { return Integer.parseInt(s); } catch (Exception ignored) {}
        }
        return null;
    }

    private Double parseDouble(String s) {
        if (s == null) return null;
        return Double.parseDouble(s);
    }

    private String publicId(PublicParkingDto p) {
        String slug = p.getName() == null ? "unknown" : p.getName().toLowerCase().replace(" ", "-");
        return "PUBLIC:" + slug;
    }

    private Map<String, Object> toMap(Object obj) {
        if (obj == null) return null;
        // transformă obiectul (Map deja sau alt tip) în Map<String,Object>
        return objectMapper.convertValue(obj, new TypeReference<Map<String, Object>>() {});
    }

    private Map<String, Object> parseJsonToMap(String json) {
        if (json == null || json.isBlank()) return null;
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            return null;
        }
    }

    public List<UnifiedParkingDto> getAll() {
        List<UnifiedParkingDto> result = new ArrayList<>();

        // PUBLIC (primărie) -> details devine JsonNode
        for (PublicParkingDto p : publicProvider.fetch()) {
            result.add(UnifiedParkingDto.builder()
                    .id(publicId(p))
                    .source("PUBLIC")
                    .name(p.getName())
                    .lat(parseDouble(p.getLat()))
                    .lon(parseDouble(p.getLon()))
                    .capacity(p.getCapacity())
                    .freeSpots(parseFreeSpots(p.getFreeSpots()))
                    .details(toMap(p.getDetails()))
                    .build());
        }

        // CUSTOM (DB) -> detailsJson (String) -> JsonNode
        customRepo.findAllByActiveTrue().forEach(cp -> {
            result.add(UnifiedParkingDto.builder()
                    .id("CUSTOM:" + cp.getId())
                    .source("CUSTOM")
                    .name(cp.getName())
                    .lat(cp.getLat())
                    .lon(cp.getLon())
                    .capacity(cp.getCapacity())
                    .freeSpots(cp.getFreeSpots())
                    .details(parseJsonToMap(cp.getDetailsJson()))
                    .build());
        });

        return result;
    }
}