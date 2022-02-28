package br.com.luciano.movieslist.ui.favorites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import br.com.luciano.movieslist.data.model.Movie;
import br.com.luciano.movieslist.repository.MoviesRepository;
import io.reactivex.rxjava3.core.Completable;

public class FavoritesViewModel extends ViewModel {

    private LiveData<List<Movie>> movies;
    private final MoviesRepository repository;

    public FavoritesViewModel(MoviesRepository repository) {
        this.repository = repository;
        movies = new MutableLiveData<>();
    }

    public LiveData<List<Movie>> getMovies() {
        movies = repository.getAllFavoriteMovies();
        return movies;
    }

    public Completable deleteMovie(Movie movie) {
        return repository.deleteFavoriteMovie(movie);
    }
}