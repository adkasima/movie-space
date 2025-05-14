package com.moviespace.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserRequest(
        @Schema(type = "string", description = "Nome do Usuário")
        String name,
        @Schema(type = "string", description = "Email do Usuário")
        String email,
        @Schema(type = "string", description = "Senha do Usuário")
        String password) {
}
