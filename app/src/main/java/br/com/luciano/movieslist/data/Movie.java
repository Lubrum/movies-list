package br.com.luciano.movieslist.data;

import com.google.gson.annotations.SerializedName;

public class Movie {
    @SerializedName("id") public Long id;
    @SerializedName("title") public String title;
    @SerializedName("overview") public String overview;
    @SerializedName("poster_path") public String posterPath;
    @SerializedName("backdrop_path") public String backdropPath;
    @SerializedName("vote_average") public String rating;
    @SerializedName("release_date") public String releaseDate;
}
