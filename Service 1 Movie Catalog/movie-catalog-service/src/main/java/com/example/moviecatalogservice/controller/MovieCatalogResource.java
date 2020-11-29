package com.example.moviecatalogservice.controller;

import com.example.moviecatalogservice.model.CatalogItem;
import com.example.moviecatalogservice.model.Movie;
import com.example.moviecatalogservice.model.Rating;
import com.example.moviecatalogservice.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webclientBuilder;

    @GetMapping("/{userid}")
    public List<CatalogItem> getCatalog(@PathVariable("userid") String userId) {

        //get all rated movies ID
        UserRating userRating = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/foo" + userId, UserRating.class);

        return userRating.getUserRating().stream().map(rating -> {

            //Doing the same things using restTemplate, then WebClient
            /*
            //Using WebClient.This whole thing will get us an instance of movie
            Movie movie = webclientBuilder.build()
                    //when we do a get we add the get() method, when we do a post we use post()
                    .get()
                    //represents the url we do the get to.Where we do the request
                    .uri("http://localhost:8082/movies/" + rating.getMovieId())
                    //means fetch what is there at that URL from above
                    .retrieve()
                    //Convert whatever body you get back to an instance of a Movie class.Mono means the reactive
                    //way of telling us that we will get that object but in the future so in an asynchronous way.
                    .bodyToMono(Movie.class)
                    //we are blocking execution untill mono is fulfilled.Converts from asynchronous to synchoronous
                    .block();
            */
            //this method getForObject here makes a rest call, wich returns a String and then when we provide the class, it
            //creates an object from that String based on the respective class that we provided.So basically
            //it takes a payload and returns a movie object in this case.

            //for each movie id call movie info service and get details
            Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
            //put them all together
            return new CatalogItem(movie.getMovieName(), "Amazing movie", rating.getRating());
        })
                .collect(Collectors.toList());






    }

}
