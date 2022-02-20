package br.com.luciano.movieslist.api;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.Objects;

import br.com.luciano.movieslist.data.MoviesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesApi {

    private final Api api;

    public MoviesApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.api = retrofit.create(Api.class);
    }

    public void getPopularMovies(String key, int page, CustomCallback callback) {
        Call<MoviesResponse> call = api.getPopularMovies(key, page);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(Objects.requireNonNull(response.body()).movies);
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
