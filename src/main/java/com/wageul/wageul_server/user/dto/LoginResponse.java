package com.wageul.wageul_server.user.dto;

public record LoginResponse(
        long userId,
        String token
) {
}
