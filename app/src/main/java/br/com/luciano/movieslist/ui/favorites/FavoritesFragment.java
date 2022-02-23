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

import br.com.luciano.movieslist.data.model.Movie;
import movieslist.R;
import movieslist.databinding.FragmentFavoritesBinding;

public class FavoritesFragment extends Fragment implements ClickListener {

    private FragmentFavoritesBinding binding;
    private FavoritesViewModel viewModel;
    private RecyclerView favoritesMoviesRV;
    private FavoritesAdapter favoritesMoviesAdapter;
    private LinearLayoutManager favoritesMoviesLM;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favoritesMoviesRV = view.findViewById(R.id.favorites_movies_list);
        favoritesMoviesLM = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        favoritesMoviesRV.setLayoutManager(favoritesMoviesLM);
        favoritesMoviesAdapter = new FavoritesAdapter(this);
        favoritesMoviesRV.setAdapter(favoritesMoviesAdapter);
        favoritesMoviesRV.setHasFixedSize(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
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
        viewModel.deleteMovie(movie);
    }
}