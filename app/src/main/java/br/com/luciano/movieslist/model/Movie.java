package br.com.luciano.movieslist.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Movie {
    @SerializedName("id") private Long id;
    @SerializedName("title") private String title;
    @SerializedName("overview") private String overview;
    @SerializedName("poster_path") private String posterPath;
    @SerializedName("backdrop_path") private String backdropPath;
    @SerializedName("vote_average") private String rating;
    @SerializedName("release_date") private String releaseDate;

    public Movie(Long id, String title, String overview, String posterPath, String backdropPath, String rating, String releaseDate) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return getId().equals(movie.getId()) &&
                getTitle().equals(movie.getTitle()) &&
                getOverview().equals(movie.getOverview()) &&
                getPosterPath().equals(movie.getPosterPath()) &&
                getBackdropPath().equals(movie.getBackdropPath()) &&
                getRating().equals(movie.getRating()) &&
                getReleaseDate().equals(movie.getReleaseDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getOverview(), getPosterPath(), getBackdropPath(), getRating(), getReleaseDate());
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", rating='" + rating + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }
}
