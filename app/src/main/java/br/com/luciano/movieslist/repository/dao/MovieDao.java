package br.com.luciano.movieslist.repository.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import br.com.luciano.movieslist.model.Movie;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movie")
    LiveData<List<Movie>> getAll();

    @Insert
    void insertAll(Movie... users);

    @Delete
    void delete(Movie user);
}
