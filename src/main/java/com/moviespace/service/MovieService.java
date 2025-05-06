package com.moviespace.service;

import com.moviespace.entity.Category;
import com.moviespace.entity.Movie;
import com.moviespace.entity.Streaming;
import com.moviespace.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final CategoryService categoryService;
    private final StreamingService streamingService;

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getById(Long id) {
        return movieRepository.findById(id);
    }

    public Movie save(Movie movie) {
        movie.setCategories(this.findCategories(movie.getCategories()));
        movie.setStreamings(this.findStreaming(movie.getStreamings()));
        return movieRepository.save(movie);
    }

    public void deleteById(Long id) {
        movieRepository.deleteById(id);
    }

    private List<Category> findCategories(List<Category> categories) {
        List<Category> categoriesFound = new ArrayList<>();
        categories.forEach(category -> categoryService.getById(category.getId()).ifPresent(categoriesFound::add));
        return categoriesFound;
    }

    private List<Streaming> findStreaming(List<Streaming> streamings) {
        List<Streaming> streamingsFound = new ArrayList<>();
        streamings.forEach(streaming -> streamingService.getById(streaming.getId()).ifPresent(streamingsFound::add));
        return streamingsFound;
    }
}
