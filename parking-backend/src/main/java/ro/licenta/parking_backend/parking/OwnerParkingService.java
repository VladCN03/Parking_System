package ro.licenta.parking_backend.parking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.licenta.parking_backend.owner.Owner;
import ro.licenta.parking_backend.owner.OwnerRepository;
import ro.licenta.parking_backend.parking.dto.CreateParkingRequest;
import ro.licenta.parking_backend.parking.dto.ParkingResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerParkingService {

    private final OwnerRepository ownerRepository;
    private final CustomParkingRepository customParkingRepository;

    private Owner getOwnerOrThrow(Long ownerId) {
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
    }

    private ParkingResponse toResponse(CustomParking p) {
        return ParkingResponse.builder()
                .id(p.getId())
                .ownerId(p.getOwner().getId())
                .name(p.getName())
                .address(p.getAddress())
                .lat(p.getLat())
                .lon(p.getLon())
                .capacity(p.getCapacity())
                .freeSpots(p.getFreeSpots())
                .detailsJson(p.getDetailsJson())
                .active(p.isActive())
                .updatedAt(p.getUpdatedAt())
                .build();
    }

    public List<ParkingResponse> myParkings(Long ownerId) {
        return customParkingRepository.findAllByOwnerIdAndActiveTrue(ownerId)
                .stream().map(this::toResponse).toList();
    }

    public ParkingResponse create(Long ownerId, CreateParkingRequest req) {
        Owner owner = getOwnerOrThrow(ownerId);

        CustomParking p = CustomParking.builder()
                .owner(owner)
                .name(req.getName())
                .address(req.getAddress())
                .lat(req.getLat())
                .lon(req.getLon())
                .capacity(req.getCapacity())
                .freeSpots(req.getFreeSpots())
                .detailsJson(req.getDetailsJson())
                .active(true)
                .build();

        return toResponse(customParkingRepository.save(p));
    }

    public ParkingResponse update(Long ownerId, Long parkingId, CreateParkingRequest req) {
        CustomParking p = customParkingRepository.findById(parkingId)
                .orElseThrow(() -> new RuntimeException("Parking not found"));

        if (!p.getOwner().getId().equals(ownerId)) {
            throw new RuntimeException("Forbidden: not your parking");
        }

        p.setName(req.getName());
        p.setAddress(req.getAddress());
        p.setLat(req.getLat());
        p.setLon(req.getLon());
        p.setCapacity(req.getCapacity());
        p.setFreeSpots(req.getFreeSpots());
        p.setDetailsJson(req.getDetailsJson());

        return toResponse(customParkingRepository.save(p));
    }

    public void delete(Long ownerId, Long parkingId) {
        CustomParking p = customParkingRepository.findById(parkingId)
                .orElseThrow(() -> new RuntimeException("Parking not found"));

        if (!p.getOwner().getId().equals(ownerId)) {
            throw new RuntimeException("Forbidden: not your parking");
        }

        // soft delete
        p.setActive(false);
        customParkingRepository.save(p);
    }

    public List<ParkingResponse> publicCustomParkings() {
        return customParkingRepository.findAllByActiveTrue()
                .stream().map(this::toResponse).toList();
    }
}
