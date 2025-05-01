package com.moviespace.controller;

import com.moviespace.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moviespace/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
}
