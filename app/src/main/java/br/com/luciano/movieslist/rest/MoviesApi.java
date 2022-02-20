package br.com.luciano.movieslist.rest;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.Objects;

import br.com.luciano.movieslist.api.Api;
import br.com.luciano.movieslist.api.CustomCallback;
import br.com.luciano.movieslist.api.RetrofitApi;
import br.com.luciano.movieslist.response.MoviesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesApi {

    private final Api apiRequest;

    public MoviesApi() {
        apiRequest = RetrofitApi.getRetrofitInstance().create(Api.class);
    }

    public void getPopularMovies(String key, int page, CustomCallback callback) {
        Call<MoviesResponse> call = apiRequest.getPopularMovies(key, page);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(Objects.requireNonNull(response.body()).getMovies());
                    return;
                }

                try {
                    callback.onFailure(new Exception(Objects.requireNonNull(response.errorBody()).string()));
                } catch (IOException e) {
                    callback.onFailure(new Exception(response.raw().message()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    if (t.getMessage() != null) {
                        callback.onFailure(t);
                    }

                    callback.onFailure(new Exception("Falha ao efetuar comunicação com o servidor"));
                }
            }
        });
    }
}
