package com.asquare.main.dto;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String flatNumber,
        String email,
        String mobile,
        String loginPin,
        Long builderId,
        Long projectId,
        Long clusterId,
        Long wingId
) {
}
