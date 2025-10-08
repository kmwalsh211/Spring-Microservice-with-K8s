package com.example.demo;

import org.springframework.stereotype.Service;

import java.rmi.MarshalledObject;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    private List<Movie> MovieList = new ArrayList<>();

    //CREATE
    public void addMovie(String title, int year){
        Movie movie = new Movie(MovieList.size(), title, year);
        MovieList.add(movie);
    }

    //READS
    public List<Movie> getMovies() {
        return MovieList;
    }

    public Movie getMovieByTitle(String title) {
        for (Movie movie : MovieList) {
            if (movie.getTitle().equals(title)) {
                return movie;
            }
        }
        throw new IllegalArgumentException("No such movie.");
    }

    public Movie getMovieById(int id) {
        for (Movie movie : MovieList) {
            if (movie.getId() == id){
                return movie;
            }
        }
        throw new IllegalArgumentException("No such movie.");
    }

    //UPDATES
    public Movie updateTitle(Movie movie, String title){
        movie.setTitle(title);
        return movie;
    }

    public Movie updateYear(Movie movie, int year){
        movie.setYear(year);
        return movie;
    }

    //DELETE
    public void deleteMovie(Movie movie){
        MovieList.remove(movie);
    }

}
