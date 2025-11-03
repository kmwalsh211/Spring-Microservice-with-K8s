package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.*;

@CrossOrigin(origins = "*")
@RequestMapping("/api")
@RestController
public class MovieController {
    @Autowired
    private MovieService movieService;

    //NEW CONFIG ROUTE
    @GetMapping("/config")
    public Map<String, String> getConfig() {
        Map<String, String> env = System.getenv();     // read all environment variables

        System.out.println("---- CONFIG DATA ----");
        System.out.println(env);                      // log to console (pod logs)

        return env;                                   // return json to browser/react/swagger
    }

    //NEW FIB ROUTE
    @GetMapping("/fib")
    public List<Integer> generateFibonacci(@RequestParam("length") int length) {
        List<Integer> fib = new ArrayList<>();

        if (length <= 0) {
            System.out.println("FIB RESULT: []");
            return fib;
        }

        fib.add(0);
        if (length == 1) {
            System.out.println("FIB RESULT: " + fib);
            return fib;
        }

        fib.add(1);
        for (int i = 2; i < length; i++) {
            fib.add(fib.get(i - 1) + fib.get(i - 2));
        }

        System.out.println("FIB RESULT: " + fib);
        return fib;
    }

    //CREATE
    @PostMapping("/add")
    public void addMovie(@RequestBody Movie movie) {
        List<Movie> movies = movieService.getMovies();
        for(Movie m : movies){
            if(m.getTitle().equals(movie.getTitle()) && m.getYear() == movie.getYear()){
                throw new IllegalArgumentException("Movie already exists");
            }
        }
        movieService.addMovie(movie.getTitle(), movie.getYear());
    }

    //READS
    @GetMapping("/viewAllMovies")
    public List<Movie> viewAllMovies() {
        return movieService.getMovies();
    }

    @GetMapping("/viewMovieByTitle")
    public Movie viewMovieByTitle(@RequestParam String title){
        return movieService.getMovieByTitle(title);
    }

    @GetMapping("/viewMovieById")
    public Movie viewMovieById(@RequestParam int id){
        return movieService.getMovieById(id);
    }

    //UPDATES
    @PutMapping("/updateTitleById")
    public Movie updateTitleById(@RequestParam int id, String title) {
        Movie movie = movieService.getMovieById(id);
        movieService.updateTitle(movie, title);
        return movie;
    }

    @PutMapping("/updateYearById")
    public Movie updateYearById(@RequestParam int id, int year) {
        Movie movie = movieService.getMovieById(id);
        movieService.updateYear(movie, year);
        return movie;
    }

    @PutMapping("/updateYearByTitle")
    public Movie updateYearByTitle(@RequestParam String title, int year) {
        Movie movie = movieService.getMovieByTitle(title);
        movieService.updateYear(movie, year);
        return movie;
    }

    //DELETE
    @DeleteMapping("/deleteMovieById")
    public void deleteMovieById(@RequestParam int id) {
        movieService.deleteMovie(movieService.getMovieById(id));
    }

    @DeleteMapping("/deleteMovieByTitle")
    public void deleteMovieByTitle(@RequestParam String title) {
        movieService.deleteMovie(movieService.getMovieByTitle(title));
    }

}
