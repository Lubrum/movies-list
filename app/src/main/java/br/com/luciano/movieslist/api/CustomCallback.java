package br.com.luciano.movieslist.api;

import java.util.List;

import br.com.luciano.movieslist.data.Movie;

public interface CustomCallback {
    void onSuccess(List<Movie> movies);
    void onFailure(Throwable t);
}