package com.moviespace.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.List;

public record MovieRequest(
        @Schema(type = "string", description = "Nome do Filme")
        @NotEmpty(message = "Título do filme é obrigatório.")
        String title,
        @Schema(type = "string", description = "Descrição do Filme")
        String description,
        @Schema(type = "date", description = "Data de lançamento do Filme. (dd/MM/yyyy)")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate releaseDate,
        @Schema(type = "double", description = "Avaliação do Filme")
        double rating,
        @Schema(type = "array", description = "Lista de códigos de categorias")
        List<Long> categories,
        @Schema(type = "array", description = "Lista de códigos de serviços de streamings")
        List<Long> streamings) {
}
