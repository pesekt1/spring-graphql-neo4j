package com.springneo4jgraphql.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    //adding a field "overview" to Movie model - it will be accessible via graphql
    @SchemaMapping(typeName = "Movie", field = "overview")
    private Mono<String> overview(Movie movie) {
        return this.movieService.getOverview(movie);
    }

    @SchemaMapping(typeName = "Movie", field = "originalTitle")
    private Mono<String> originalTitle(Movie movie) {
        return this.movieService.getOriginalTitle(movie);
    }

    @SchemaMapping(typeName = "Movie", field = "releaseDate")
    private Mono<String> releaseDate(Movie movie) {
        return this.movieService.getOriginalTitle(movie);
    }

    @SchemaMapping(typeName = "Movie", field = "popularity")
    private Mono<Double> popularity(Movie movie) {
        return this.movieService.getPopularity(movie);
    }

}
