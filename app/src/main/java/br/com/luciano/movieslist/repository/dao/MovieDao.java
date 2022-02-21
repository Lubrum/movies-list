package br.com.luciano.movieslist.repository.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import br.com.luciano.movieslist.model.Movie;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movie")
    List<Movie> getAll();

    @Query("SELECT * FROM movie WHERE uid IN (:userIds)")
    List<Movie> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM movie WHERE id = :last")
    Movie findById(String first, String last);

    @Insert
    void insertAll(Movie... users);

    @Delete
    void delete(Movie user);
}
