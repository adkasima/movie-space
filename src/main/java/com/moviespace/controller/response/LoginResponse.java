package com.moviespace.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponse(
        @Schema(type = "string", description = "Token de login")
        String token) {
}
