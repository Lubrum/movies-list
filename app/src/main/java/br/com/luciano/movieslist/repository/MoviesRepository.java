package br.com.luciano.movieslist.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.luciano.movieslist.model.Movie;
import br.com.luciano.movieslist.repository.dao.AppDatabase;
import br.com.luciano.movieslist.repository.dao.MovieDao;

public class MoviesRepository {

    private LiveData<List<Movie>> movies;
    private MovieDao movieDao;
    AppDatabase database;

    public MoviesRepository(AppDatabase database) {
        this.database = database;
    }

    public LiveData<List<Movie>> getloadAllMovies() {
        movies = database.movieDao().getAll();
        return movies;
    }

    public void deleteMovie(Movie movie) {
        database.movieDao().delete(movie);
    }

    public void insertAllMovies(Movie... movies) {
        database.movieDao().insertAll(movies);
    }
}
