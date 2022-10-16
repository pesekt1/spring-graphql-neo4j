package com.springneo4jgraphql.movie;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class MovieService {

    @Value("${tmdb_api}")
    String apiKey;

    private CompletableFuture<HttpResponse<String>> getMovie(Movie movie){
        URI uri = URI.create("https://api.themoviedb.org/3/search/movie?api_key=" + apiKey + "&query=" + URLEncoder.encode(movie.getTitle(), StandardCharsets.UTF_8));
        System.out.println(uri);
        return HttpClient.newHttpClient().sendAsync(HttpRequest.newBuilder().uri(uri).build(), HttpResponse.BodyHandlers.ofString());
    }

    private List<Object> getResults(HttpResponse<String> response){
       return (List<Object>) new JacksonJsonParser().parseMap(response.body()).get("results");
    }

    public Mono<String> getOverview(Movie movie) {
        CompletableFuture<String> futureResponse = getMovie(movie)
                .thenApply(response -> {
                    List<Object> results = getResults(response);
                    if (results.isEmpty()) {
                        return "no overview found";
                    }
                    return (String) ((Map<String, Object>) results.get(0)).get("overview");
                });
        return Mono.fromCompletionStage(futureResponse);
    }

    public Mono<String> getOriginalTitle(Movie movie) {
        CompletableFuture<String> futureResponse = getMovie(movie)
                .thenApply(response -> {
                    List<Object> results = getResults(response);
                    if (results.isEmpty()) {
                        return "no original title found";
                    }
                    return (String) ((Map<String, Object>) results.get(0)).get("original_title");
                });
        return Mono.fromCompletionStage(futureResponse);
    }

    public Mono<Double> getPopularity(Movie movie) {
        CompletableFuture<Double> futureResponse = getMovie(movie)
                .thenApply(response -> {
                    List<Object> results = getResults(response);
                    return (Double) ((Map<String, Object>) results.get(0)).get("popularity");
                });
        return Mono.fromCompletionStage(futureResponse);
    }

}
