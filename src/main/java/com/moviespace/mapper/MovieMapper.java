package com.moviespace.mapper;

import com.moviespace.controller.request.MovieRequest;
import com.moviespace.controller.response.CategoryResponse;
import com.moviespace.controller.response.MovieResponse;
import com.moviespace.controller.response.StreamingResponse;
import com.moviespace.entity.Category;
import com.moviespace.entity.Movie;
import com.moviespace.entity.Streaming;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class MovieMapper {

    public static Movie toMovie(MovieRequest request) {
        List<Category> categories = request.categories().stream()
                .map(categoryId -> Category.builder().id(categoryId).build()).toList();

        List<Streaming> streamings = request.streamings().stream()
                .map(streamingId -> Streaming.builder().id(streamingId).build()).toList();


        return Movie.builder()
                .title(request.title())
                .description(request.description())
                .releaseDate(request.releaseDate())
                .rating(request.rating())
                .categories(categories)
                .streamings(streamings)
                .build();
    }

    public static MovieResponse toMovieResponse(Movie movie) {

        List<CategoryResponse> categories = movie.getCategories()
                .stream()
                .map(CategoryMapper::toCategoryResponse)
                .toList();

        List<StreamingResponse> streamings = movie.getStreamings()
                .stream()
                .map(StreamingMapper::toStreamingResponse)
                .toList();

        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .releaseDate(movie.getReleaseDate())
                .rating(movie.getRating())
                .categories(categories)
                .streamings(streamings)
                .build();

    }
}
