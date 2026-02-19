package ro.licenta.parking_backend.parking;

import jakarta.persistence.*;
import lombok.*;
import ro.licenta.parking_backend.owner.Owner;

import java.time.LocalDateTime;

@Entity
@Table(name = "custom_parkings")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CustomParking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Column(nullable = false, length = 160)
    private String name;

    @Column(length = 255)
    private String address;

    @Column(nullable = false)
    private Double lat;

    @Column(nullable = false)
    private Double lon;

    @Column(nullable = false)
    private Integer capacity;

    @Column(name = "free_spots")
    private Integer freeSpots; // poate fi null

    @Column(name = "details_json")
    private String detailsJson; // optional

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "is_active", nullable = false)
    private boolean active;

    @PrePersist
    public void prePersist() {
        if (updatedAt == null) updatedAt = LocalDateTime.now();
        active = true;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
