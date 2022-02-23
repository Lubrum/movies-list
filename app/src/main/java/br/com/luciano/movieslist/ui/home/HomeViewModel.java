package br.com.luciano.movieslist.ui.home;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.luciano.movieslist.data.model.Movie;
import br.com.luciano.movieslist.repository.MoviesRepository;

public class HomeViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> movies;
    private MoviesRepository repository;

    public HomeViewModel(Application application) {
        super(application);
        repository = new MoviesRepository(getApplication());
        movies = repository.getPopularMoviesResponse();
    }

    public void searchPopularMovies(String apiKey, int popularMoviesPage) {
        repository.getPopularMovies(apiKey, popularMoviesPage);
    }

    public void insertMovie(Movie movie) {
        repository.insertMovie(movie);
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}