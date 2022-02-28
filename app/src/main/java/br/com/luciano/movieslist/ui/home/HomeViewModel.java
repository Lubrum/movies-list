package br.com.luciano.movieslist.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import br.com.luciano.movieslist.data.model.Movie;
import br.com.luciano.movieslist.repository.MoviesRepository;
import io.reactivex.rxjava3.core.Completable;

public class HomeViewModel extends ViewModel {

    private final LiveData<List<Movie>> movies;
    private final MoviesRepository repository;

    public HomeViewModel(MoviesRepository repository) {
        this.repository = repository;
        movies = repository.getPopularMoviesResponse();
    }

    public void fetchPopularMovies(String apiKey, int popularMoviesPage) {
        repository.getPopularMovies(apiKey, popularMoviesPage);
    }

    public Completable insertMovie(Movie movie) {
        return repository.insertFavoriteMovie(movie);
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}