package com.moviespace.controller;

import com.moviespace.controller.request.MovieRequest;
import com.moviespace.controller.response.MovieResponse;
import com.moviespace.entity.Movie;
import com.moviespace.mapper.MovieMapper;
import com.moviespace.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/moviespace/movie")
@RequiredArgsConstructor
@Tag(name = "Movie", description = "Rota resposável pelo gerenciamento de filmes")
public class MovieController {

    private final MovieService movieService;

    @Operation(summary = "Buscar Filmes", description = "Método responsável por retornar todos os filmes cadastrados",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Retorna todos os filmes cadastrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovieResponse.class))))
    @GetMapping("/all")
    public ResponseEntity<List<MovieResponse>> getAll() {
        List<MovieResponse> movies = movieService.findAll()
                .stream()
                .map(MovieMapper::toMovieResponse)
                .toList();

        return ResponseEntity.ok(movies);
    }

    @Operation(summary = "Buscar um Filme", description = "Método responsável por buscar filme",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Filme encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = MovieResponse.class))),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado",
                    content = @Content())
    })
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getById(@PathVariable Long id) {
        return movieService.getById(id)
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar Filmes por categoria ", description = "Método responsável por retornar todos os " +
            "filmes pela categoria",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Retorna todos os filmes pela categoria",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MovieResponse.class))))
    @GetMapping("/search")
    public ResponseEntity<List<MovieResponse>> getByCategory(@RequestParam Long category) {
        return ResponseEntity.ok(movieService.getByCategory(category)
                .stream()
                .map(MovieMapper::toMovieResponse)
                .toList());
    }

    @Operation(summary = "Salvar Filme", description = "Método responsável pelo salvamento de filme",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Filme salvo com sucesso",
            content = @Content(schema = @Schema(implementation = MovieResponse.class)))
    @PostMapping("/create")
    public ResponseEntity<MovieResponse> save(@Valid @RequestBody MovieRequest request) {
        Movie newMovie = MovieMapper.toMovie(request);
        Movie savedMovie = movieService.save(newMovie);
        return ResponseEntity.status(HttpStatus.CREATED).body(MovieMapper.toMovieResponse(savedMovie));

    }

    @Operation(summary = "Atualizar Filme", description = "Método responsável por alterar dados do filme",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Filme deletado com sucesso",
                    content = @Content(schema = @Schema(implementation = MovieResponse.class))),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado",
                    content = @Content())
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<MovieResponse> update(@Valid @PathVariable Long id, @RequestBody MovieRequest request) {
        return movieService.update(id, MovieMapper.toMovie(request))
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar Filme", description = "Método responsável por deletar um filme",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Filme deletado com sucesso",
                    content = @Content()),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado",
                    content = @Content())
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        Optional<Movie> optMovie = movieService.getById(id);

        if (optMovie.isPresent()) {
            movieService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.notFound().build();

    }
}
