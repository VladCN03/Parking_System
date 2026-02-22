package ro.licenta.parking_backend.owner;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.licenta.parking_backend.owner.dto.OwnerMeResponse;

@RestController
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerRepository ownerRepository;

    @GetMapping("/api/owner/me")
    public OwnerMeResponse me(Authentication auth) {
        Long ownerId = Long.parseLong(auth.getPrincipal().toString());
        Owner o = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        return OwnerMeResponse.builder()
                .id(o.getId())
                .name(o.getName())
                .email(o.getEmail())
                .createdAt(o.getCreatedAt())
                .build();
    }
}