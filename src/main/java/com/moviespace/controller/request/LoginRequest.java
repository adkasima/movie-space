package com.moviespace.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRequest(
        @Schema(type = "string", description = "Email de login")
        String email,
        @Schema(type = "string", description = "Senha de login")
        String password) {
}
