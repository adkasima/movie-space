package com.moviespace.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record MovieResponse(
        @Schema(type = "long", description = "Código do Filme")
        Long id,
        @Schema(type = "string", description = "Nome do Filme")
        String title,
        @Schema(type = "string", description = "Descrição do Filme")
        String description,
        @Schema(type = "date", description = "Data de lançamento do Filme. (dd/MM/yyyy)")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate releaseDate,
        @Schema(type = "double", description = "Avaliação do Filme")
        double rating,
        @Schema(type = "array", description = "Lista de códigos de categorias")
        List<CategoryResponse> categories,
        @Schema(type = "array", description = "Lista de códigos de serviços de streamings")
        List<StreamingResponse> streamings) {
}
