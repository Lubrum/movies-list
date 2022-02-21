package br.com.luciano.movieslist.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.luciano.movieslist.repository.api.CustomCallback;
import br.com.luciano.movieslist.model.Movie;
import br.com.luciano.movieslist.ui.home.HomeAdapter;
import movieslist.R;
import movieslist.databinding.FragmentDashboardBinding;

public class FavoritesFragment extends Fragment implements CustomCallback {

    private FragmentDashboardBinding binding;

    private RecyclerView popularMoviesRV;
    private HomeAdapter popularMoviesAdapter;
    private LinearLayoutManager popularMoviesLM;

    private int favoriteMoviesPage = 1;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        popularMoviesRV = view.findViewById(R.id.popular_movies_list);
        popularMoviesLM = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        popularMoviesRV.setLayoutManager(popularMoviesLM);
        popularMoviesAdapter = new HomeAdapter();
        popularMoviesRV.setAdapter(popularMoviesAdapter);
        getFavoriteMovies();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FavoritesViewModel dashboardViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    private void getFavoriteMovies() {

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
                    favoriteMoviesPage++;
                    getFavoriteMovies();
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