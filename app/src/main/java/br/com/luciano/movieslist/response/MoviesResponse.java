package br.com.luciano.movieslist.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.luciano.movieslist.model.Movie;

public class MoviesResponse {
    @SerializedName("page") public Integer page;
    @SerializedName("results") public List<Movie> movies;
    @SerializedName("total_pages") public Integer pages;
}
