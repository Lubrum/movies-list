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

import movieslist.R;
import movieslist.databinding.FragmentFavoritesBinding;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;

    private RecyclerView popularMoviesRV;
    private FavoritesAdapter popularMoviesAdapter;
    private LinearLayoutManager popularMoviesLM;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        popularMoviesRV = view.findViewById(R.id.favorites_movies_list);
        popularMoviesLM = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        popularMoviesRV.setLayoutManager(popularMoviesLM);
        popularMoviesAdapter = new FavoritesAdapter();
        popularMoviesRV.setAdapter(popularMoviesAdapter);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FavoritesViewModel viewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
        viewModel.getMovies().observe(getViewLifecycleOwner(), movies -> {
            popularMoviesAdapter.appendMovies(movies);
            attachPopularMoviesOnScrollListener();
        });

        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    private void attachPopularMoviesOnScrollListener() {
        popularMoviesRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int totalItemCount = popularMoviesLM.getItemCount();
                int visibleItemCount = popularMoviesLM.getChildCount();
                int firstVisibleItem = popularMoviesLM.findFirstVisibleItemPosition();

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    popularMoviesRV.removeOnScrollListener(this);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}