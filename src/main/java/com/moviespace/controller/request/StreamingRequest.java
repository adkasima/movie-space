package com.moviespace.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record StreamingRequest(
        @NotEmpty(message = "Nome do serviço de streaming é obrigatório.")
        @Schema(type = "string", description = "Nome do serviço de streaming")
        String name) {
}
