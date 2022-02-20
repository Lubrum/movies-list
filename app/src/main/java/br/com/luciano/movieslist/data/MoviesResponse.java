package br.com.luciano.movieslist.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesResponse {
    @SerializedName("page") public Integer page;
    @SerializedName("results") public List<Movie> movies;
    @SerializedName("total_pages") public Integer pages;
}
