package ro.licenta.parking_backend.parking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ro.licenta.parking_backend.common.dto.UnifiedParkingDto;
import ro.licenta.parking_backend.parking.dto.CreateParkingRequest;
import ro.licenta.parking_backend.parking.dto.ParkingResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParkingController {

    private final OwnerParkingService service;
    private final ParkingAggregationService aggregationService;

    // OWNER: listează parcările lui
    @GetMapping("/api/owner/parkings")
    public List<ParkingResponse> myParkings(Authentication auth) {
        Long ownerId = Long.parseLong(auth.getPrincipal().toString());
        return service.myParkings(ownerId);
    }

    // OWNER: creează parcare
    @PostMapping("/api/owner/parkings")
    public ParkingResponse create(Authentication auth, @Valid @RequestBody CreateParkingRequest req) {
        Long ownerId = Long.parseLong(auth.getPrincipal().toString());
        return service.create(ownerId, req);
    }

    // OWNER: update parcare
    @PutMapping("/api/owner/parkings/{id}")
    public ParkingResponse update(Authentication auth, @PathVariable Long id,
                                  @Valid @RequestBody CreateParkingRequest req) {
        Long ownerId = Long.parseLong(auth.getPrincipal().toString());
        return service.update(ownerId, id, req);
    }

    // OWNER: delete (soft)
    @DeleteMapping("/api/owner/parkings/{id}")
    public void delete(Authentication auth, @PathVariable Long id) {
        Long ownerId = Long.parseLong(auth.getPrincipal().toString());
        service.delete(ownerId, id);
    }

    // PUBLIC: toate parcările custom active (pentru Flutter)
    @GetMapping("/api/parkings/custom")
    public List<ParkingResponse> publicCustom() {
        return service.publicCustomParkings();
    }

    @GetMapping("/api/parkings")
    public List<UnifiedParkingDto> all() {
        return aggregationService.getAll();
    }
}
