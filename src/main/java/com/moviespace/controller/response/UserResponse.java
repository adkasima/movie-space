package com.moviespace.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record UserResponse(
        @Schema(type = "string", description = "Código do Usuário")
        Long id,
        @Schema(type = "string", description = "Nome do Usuário")
        String name,
        @Schema(type = "string", description = "Email do Usuário")
        String email) {
}
