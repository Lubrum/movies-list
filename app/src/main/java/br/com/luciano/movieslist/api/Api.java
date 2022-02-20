package br.com.luciano.movieslist.api;

import br.com.luciano.movieslist.data.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("page") Integer page
    );
}
