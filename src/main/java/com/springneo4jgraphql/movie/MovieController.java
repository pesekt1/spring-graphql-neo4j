package com.springneo4jgraphql.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
public class MovieController {

    private final MovieService movieService;
    private final PersonRepository personRepository;

    @Autowired
    public MovieController(MovieService movieService, PersonRepository personRepository) {
        this.movieService = movieService;
        this.personRepository = personRepository;
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

    @SchemaMapping(typeName = "Movie", field = "actors")
    private List<Person> actors(Movie movie) {
       return this.personRepository.actorByMovie(movie.getTitle());

    }

    @SchemaMapping(typeName = "Movie", field = "directors")
    private List<Person> directors(Movie movie) {
        return this.personRepository.directorByMovie(movie.getTitle());
    }

    @MutationMapping
    public Movie addMovie(@Argument String title, @Argument String description) {
        System.out.println("Saving movie: " + title);
        return movieService.save(new Movie(title, description, null, null));
    }

    @MutationMapping
    public Long deleteMovieByTitle(@Argument String title){
        return movieService.deleteByTitle(title);
    }

    @MutationMapping
    public Movie addActorToMovie(@Argument String title, @Argument String actorName, @Argument String role) {
        return movieService.addActor(title, actorName, role);
    }

    @MutationMapping
    public List<Movie> removeActorFromMovie(@Argument String title, @Argument String actorName) {
        return movieService.removeActor(title, actorName);
    }

    @QueryMapping
    public Collection<Movie> moviesByActor(@Argument String name){
        return movieService.getMoviesByActor(name);
    }
}
