package br.com.luciano.movieslist.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.com.luciano.movieslist.data.local.AppDatabase;
import br.com.luciano.movieslist.data.model.Movie;
import br.com.luciano.movieslist.repository.MoviesRepository;
import io.reactivex.rxjava3.schedulers.Schedulers;
import movieslist.R;
import movieslist.databinding.FragmentFavoritesBinding;

public class FavoritesFragment extends Fragment implements ClickListener {

    private FragmentFavoritesBinding binding;
    private FavoritesViewModel viewModel;
    private FavoritesAdapter favoritesMoviesAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView favoritesMoviesRV = view.findViewById(R.id.favorites_movies_list);
        LinearLayoutManager favoritesMoviesLM = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        favoritesMoviesRV.setLayoutManager(favoritesMoviesLM);
        favoritesMoviesAdapter = new FavoritesAdapter(this);
        favoritesMoviesRV.setAdapter(favoritesMoviesAdapter);
        favoritesMoviesRV.setHasFixedSize(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AppDatabase db = AppDatabase.getDatabase(getContext());
        MoviesRepository repository = new MoviesRepository(db);
        FavoritesViewModelFactory factory = new FavoritesViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(FavoritesViewModel.class);
        viewModel.getMovies().observe(getViewLifecycleOwner(),
                movies -> favoritesMoviesAdapter.appendMovies(movies)
        );
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(Movie movie) {
        viewModel.deleteMovie(movie)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe();
    }
}