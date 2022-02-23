package br.com.luciano.movieslist.ui.favorites;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import br.com.luciano.movieslist.data.model.Movie;
import br.com.luciano.movieslist.repository.MoviesRepository;

public class FavoritesViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> movies;
    private MoviesRepository repository;

    public FavoritesViewModel(Application application) {
        super(application);
        repository = new MoviesRepository(getApplication());
        movies = repository.getAllMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public void deleteMovie(Movie movie) {
        repository.deleteMovie(movie);
    }
}