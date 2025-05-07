package com.moviespace.controller;

import com.moviespace.controller.request.MovieRequest;
import com.moviespace.controller.response.MovieResponse;
import com.moviespace.entity.Movie;
import com.moviespace.mapper.MovieMapper;
import com.moviespace.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/moviespace/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/all")
    public ResponseEntity<List<MovieResponse>> getAll() {
        List<MovieResponse> movies = movieService.findAll()
                .stream()
                .map(MovieMapper::toMovieResponse)
                .toList();

        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getById(@PathVariable Long id) {
        return movieService.getById(id)
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieResponse>> getByCategory(@RequestParam Long category) {
        return ResponseEntity.ok(movieService.getByCategory(category)
                .stream()
                .map(MovieMapper::toMovieResponse)
                .toList());
    }

    @PostMapping("/create")
    public ResponseEntity<MovieResponse> save(@RequestBody MovieRequest request) {
        Movie newMovie = MovieMapper.toMovie(request);
        Movie savedMovie = movieService.save(newMovie);
        return ResponseEntity.status(HttpStatus.CREATED).body(MovieMapper.toMovieResponse(savedMovie));

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MovieResponse> update(@PathVariable Long id, @RequestBody MovieRequest request) {
        return movieService.update(id, MovieMapper.toMovie(request))
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

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
