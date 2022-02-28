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

import br.com.luciano.movieslist.data.local.AppDatabase;
import br.com.luciano.movieslist.data.model.Movie;
import br.com.luciano.movieslist.repository.MoviesRepository;
import br.com.luciano.movieslist.ui.favorites.ClickListener;

import io.reactivex.rxjava3.schedulers.Schedulers;
import movieslist.R;
import movieslist.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements ClickListener {

    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;
    private RecyclerView popularMoviesRV;
    private HomeAdapter popularMoviesAdapter;
    private LinearLayoutManager popularMoviesLLM;

    private int popularMoviesPage = 1;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        popularMoviesRV = view.findViewById(R.id.popular_movies_list);
        popularMoviesLLM = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);
        popularMoviesRV.setLayoutManager(popularMoviesLLM);
        popularMoviesAdapter = new HomeAdapter(this);
        popularMoviesRV.setAdapter(popularMoviesAdapter);
        popularMoviesRV.setHasFixedSize(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AppDatabase db = AppDatabase.getDatabase(getContext());
        MoviesRepository repository = new MoviesRepository(db);
        HomeViewModelFactory factory = new HomeViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(HomeViewModel.class);
        viewModel.fetchPopularMovies(API_KEY, popularMoviesPage);
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
                if (fetchMorePopularMovies()) {
                    popularMoviesRV.removeOnScrollListener(this);
                    popularMoviesPage++;
                    viewModel.fetchPopularMovies(API_KEY, popularMoviesPage);
                }
            }
        });
    }

    private boolean fetchMorePopularMovies() {
        int totalItemCount = popularMoviesLLM.getItemCount();
        int visibleItemCount = popularMoviesLLM.getChildCount();
        int firstVisibleItem = popularMoviesLLM.findFirstVisibleItemPosition();
        return firstVisibleItem + visibleItemCount >= totalItemCount / 2;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(Movie movie) {
        viewModel.insertMovie(movie)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe();
    }
}