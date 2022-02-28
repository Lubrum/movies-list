package br.com.luciano.movieslist.ui.favorites;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.com.luciano.movieslist.repository.MoviesRepository;

public class FavoritesViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    private final MoviesRepository repository;

    public FavoritesViewModelFactory(@NonNull MoviesRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == FavoritesViewModel.class) {
            return (T) new FavoritesViewModel(repository);
        }
        throw new IllegalArgumentException("Class not allowed");
    }
}
