package ro.licenta.parking_backend.parking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomParkingRepository extends JpaRepository<CustomParking, Long> {
    List<CustomParking> findAllByOwnerIdAndActiveTrue(Long ownerId);
    List<CustomParking> findAllByActiveTrue();
}
