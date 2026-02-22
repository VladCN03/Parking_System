package ro.licenta.parking_backend.owner.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record OwnerMeResponse(
        Long id,
        String name,
        String email,
        LocalDateTime createdAt
) {}