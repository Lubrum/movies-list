package br.com.luciano.movieslist.ui.home;

import static br.com.luciano.movieslist.constants.AppConstants.API_KEY;

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

import br.com.luciano.movieslist.ui.favorites.FavoritesViewModel;
import movieslist.R;
import movieslist.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;
    private RecyclerView popularMoviesRV;
    private HomeAdapter popularMoviesAdapter;
    private LinearLayoutManager popularMoviesLM;

    private int popularMoviesPage = 1;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        popularMoviesRV = view.findViewById(R.id.popular_movies_list);
        popularMoviesLM = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        popularMoviesRV.setLayoutManager(popularMoviesLM);
        popularMoviesAdapter = new HomeAdapter();
        popularMoviesRV.setAdapter(popularMoviesAdapter);
        popularMoviesRV.setHasFixedSize(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        viewModel.searchPopularMovies(API_KEY, popularMoviesPage);
        viewModel.getMovies().observe(getViewLifecycleOwner(), movies -> {
            popularMoviesAdapter.appendMovies(movies);
            attachPopularMoviesOnScrollListener();
        });
        binding = FragmentHomeBinding.inflate(inflater, container, false);
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
                    popularMoviesPage++;
                    viewModel.searchPopularMovies(API_KEY, popularMoviesPage);
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