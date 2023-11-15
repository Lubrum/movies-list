package br.com.luciano.movieslist.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class MoviesResponse {

    @SerializedName("page") private Integer page;
    @SerializedName("results") private List<Movie> movies;
    @SerializedName("total_pages") private Integer pages;

    private MoviesResponse() {
    }

    public MoviesResponse(Integer page, List<Movie> movies, Integer pages) {
        this.page = page;
        this.movies = movies;
        this.pages = pages;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MoviesResponse)) return false;
        MoviesResponse that = (MoviesResponse) o;
        return Objects.equals(getPage(), that.getPage()) &&
                Objects.equals(getMovies(), that.getMovies()) &&
                Objects.equals(getPages(), that.getPages());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPage(), getMovies(), getPages());
    }

    @Override
    public String toString() {
        return "MoviesResponse{" +
                "page=" + page +
                ", movies=" + movies +
                ", pages=" + pages +
                '}';
    }
}
