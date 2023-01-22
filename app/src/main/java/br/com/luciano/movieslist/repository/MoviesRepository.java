package br.com.luciano.movieslist.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import br.com.luciano.movieslist.data.local.AppDatabase;
import br.com.luciano.movieslist.data.model.Movie;
import br.com.luciano.movieslist.data.model.MoviesResponse;
import br.com.luciano.movieslist.data.remote.Api;
import br.com.luciano.movieslist.data.remote.RetrofitApi;
import io.reactivex.rxjava3.core.Completable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepository {

    private final Api apiRequest;
    private final AppDatabase db;
    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();

    @Inject
    public MoviesRepository(AppDatabase db) {
        this.db = db;
        apiRequest = RetrofitApi.getRetrofitInstance().create(Api.class);
    }

    public void getPopularMovies(String key, Integer page) {
        Call<MoviesResponse> call = apiRequest.getPopularMovies(key, page);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        movies.postValue(response.body().getMovies());
                    } else {
                        movies.postValue(null);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    if (t.getMessage() != null) {
                        movies.postValue(null);
                    }
                    movies.postValue(null);
                }
            }
        });
    }

    public LiveData<List<Movie>> getPopularMoviesResponse() {
        return movies;
    }

    public LiveData<List<Movie>> getAllFavoriteMovies() {
        return db.movieDao().getAll();
    }

    public Completable deleteFavoriteMovie(Movie movie) {
        return db.movieDao().delete(movie);
    }

    public Completable insertFavoriteMovie(Movie movie) {
        return db.movieDao().insert(movie);
    }
}
