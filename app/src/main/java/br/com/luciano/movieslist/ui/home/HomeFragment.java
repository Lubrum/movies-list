package br.com.luciano.movieslist.ui.home;

import static br.com.luciano.movieslist.constants.AppConstants.API_KEY;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.luciano.movieslist.repository.api.CustomCallback;
import br.com.luciano.movieslist.model.Movie;

import br.com.luciano.movieslist.repository.api.MoviesApi;
import movieslist.R;
import movieslist.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements CustomCallback {

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
        getPopularMovies();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void getPopularMovies() {
        new MoviesApi().getPopularMovies(API_KEY, popularMoviesPage, this);
    }

    @Override
    public void onSuccess(List<Movie> movies){
        popularMoviesAdapter.appendMovies(movies);
        attachPopularMoviesOnScrollListener();
    }

    @Override
    public void onFailure(Throwable t){
        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
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
                    getPopularMovies();
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