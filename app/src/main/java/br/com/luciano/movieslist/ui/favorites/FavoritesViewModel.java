package br.com.luciano.movieslist.ui.favorites;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import br.com.luciano.movieslist.model.Movie;
import br.com.luciano.movieslist.repository.MoviesRepository;
import br.com.luciano.movieslist.repository.dao.AppDatabase;

public class FavoritesViewModel extends AndroidViewModel {

    private final LiveData<List<Movie>> movies;

    private final MoviesRepository repository;

    public FavoritesViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getDatabase(this.getApplication());
        repository = new MoviesRepository(database);
        movies = repository.getloadAllMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public void deleteMovie(Movie movie) {
        repository.deleteMovie(movie);
    }
}