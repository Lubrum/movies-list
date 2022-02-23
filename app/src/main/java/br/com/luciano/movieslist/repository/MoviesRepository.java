package br.com.luciano.movieslist.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.com.luciano.movieslist.data.local.AppDatabase;
import br.com.luciano.movieslist.data.model.Movie;
import br.com.luciano.movieslist.data.remote.Api;
import br.com.luciano.movieslist.data.remote.RetrofitApi;
import br.com.luciano.movieslist.data.model.MoviesResponse;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepository {

    private final Api apiRequest;
    private final AppDatabase db;

    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();

    public MoviesRepository(Application ctx) {
        db = AppDatabase.getDatabase(ctx);
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

    public LiveData<List<Movie>> getAllMovies() {
        return db.movieDao().getAll();
    }

    public void deleteMovie(Movie movie) {
        db.movieDao()
                .delete(movie)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe();
    }

    public void insertMovie(Movie movie) {
        db.movieDao()
                .insert(movie)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe();
    }

    public LiveData<List<Movie>> getPopularMoviesResponse() {
        return movies;
    }
}
