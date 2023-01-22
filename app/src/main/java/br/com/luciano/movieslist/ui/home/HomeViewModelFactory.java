package br.com.luciano.movieslist.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import br.com.luciano.movieslist.repository.MoviesRepository;

public class HomeViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    private final MoviesRepository repository;

    @Inject
    public HomeViewModelFactory(@NonNull MoviesRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == HomeViewModel.class) {
            return (T) new HomeViewModel(repository);
        }
        throw new IllegalArgumentException("Class not allowed");
    }
}
