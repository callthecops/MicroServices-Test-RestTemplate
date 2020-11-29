package com.example.movieratingsservice.controller;

import com.example.movieratingsservice.model.Rating;
import com.example.movieratingsservice.model.UserRating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingsDataResource {

    @GetMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 4);
    }

    @GetMapping("/users/{userId}")
    public UserRating getUserRating(@PathVariable("userId") String userId) {

        List<Rating> ratings = Arrays.asList(new Rating("1234", 56), new Rating("4321", 89));
        UserRating userRating = new UserRating();
        userRating.setUserRating(ratings);
        return userRating;
    }

}
