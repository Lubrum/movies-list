package br.com.luciano.movieslist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static br.com.luciano.movieslist.ViewModelIntegrationTest.LiveDataTestUtil.getOrAwaitValue;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import br.com.luciano.movieslist.data.local.AppDatabase;
import br.com.luciano.movieslist.data.model.Movie;
import br.com.luciano.movieslist.repository.MoviesRepository;
import br.com.luciano.movieslist.ui.favorites.FavoritesViewModel;
import br.com.luciano.movieslist.ui.home.HomeViewModel;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {31})
public class ViewModelIntegrationTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    public static final Movie MOVIE_1 = new Movie(1L, "mock1", "mock1",
            "mock1", "mock1", "mock1", "mock1");

    public static final Movie MOVIE_2 = new Movie(2L, "mock2",
            "mock2", "mock2", "mock2", "mock2", "mock2");

    public static final Movie MOVIE_3 = new Movie(1L, "mock3",
            "mock3", "mock3", "mock3", "mock3", "mock3");

    public AppDatabase mDatabase;
    public MoviesRepository mRepository;
    public FavoritesViewModel favoritesViewModel;
    public HomeViewModel homeViewModel;
    public LiveData<List<Movie>> liveDataHome;
    public LiveData<List<Movie>> liveDataFavorites;

    @Before
    public void initDb() {
        Application ctx = ApplicationProvider.getApplicationContext();
        mDatabase = Room.inMemoryDatabaseBuilder(ctx, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        mRepository = new MoviesRepository(mDatabase);
        favoritesViewModel = new FavoritesViewModel(mRepository);
        homeViewModel = new HomeViewModel(mRepository);
    }

    @After
    public void closeDb() {
        mDatabase.clearAllTables();
        mDatabase.close();
    }

    @Test
    public void insertOneMovieAndGet() throws InterruptedException {
        //assertNotNull(liveDataFavorites.getValue());
        homeViewModel.insertMovie(MOVIE_1).blockingAwait();
        liveDataFavorites = favoritesViewModel.getMovies();
        assertEquals(List.of(MOVIE_1), getOrAwaitValue(liveDataFavorites));
    }

    @Test
    public void insertTwoMoviesAndGet() throws InterruptedException {
        homeViewModel.insertMovie(MOVIE_1).blockingAwait();
        homeViewModel.insertMovie(MOVIE_2).blockingAwait();
        liveDataFavorites = favoritesViewModel.getMovies();
        assertEquals(List.of(MOVIE_1, MOVIE_2), getOrAwaitValue(liveDataFavorites));
    }

    @Test
    public void insertRepeatedMoviesAndGet() throws InterruptedException {
        homeViewModel.insertMovie(MOVIE_1).blockingAwait();
        homeViewModel.insertMovie(MOVIE_1).blockingAwait();
        liveDataFavorites = favoritesViewModel.getMovies();
        assertEquals(List.of(MOVIE_1), getOrAwaitValue(liveDataFavorites));
    }

    @Test
    public void insertRepeatedButDifferentMoviesAndGet() throws InterruptedException {
        homeViewModel.insertMovie(MOVIE_1).blockingAwait();
        homeViewModel.insertMovie(MOVIE_3).blockingAwait();
        liveDataFavorites = favoritesViewModel.getMovies();
        assertEquals(List.of(MOVIE_3), getOrAwaitValue(liveDataFavorites));
    }

    @Test
    public void insertAndDeleteMovieAndGet() throws InterruptedException {
        homeViewModel.insertMovie(MOVIE_1).blockingAwait();
        favoritesViewModel.deleteMovie(MOVIE_1).blockingAwait();
        liveDataFavorites = favoritesViewModel.getMovies();
        assertEquals(List.of(), getOrAwaitValue(liveDataFavorites));
    }

    @Test
    public void insertTwoAndDeleteFirstMovieAndGet() throws InterruptedException {
        homeViewModel.insertMovie(MOVIE_1).blockingAwait();
        homeViewModel.insertMovie(MOVIE_2).blockingAwait();
        favoritesViewModel.deleteMovie(MOVIE_1).blockingAwait();
        liveDataFavorites = favoritesViewModel.getMovies();
        assertEquals(List.of(MOVIE_2), getOrAwaitValue(liveDataFavorites));
    }

    @Test
    public void insertTwoAndDeleteLastMovieAndGet() throws InterruptedException {
        homeViewModel.insertMovie(MOVIE_1).blockingAwait();
        homeViewModel.insertMovie(MOVIE_2).blockingAwait();
        favoritesViewModel.deleteMovie(MOVIE_2).blockingAwait();
        liveDataFavorites = favoritesViewModel.getMovies();
        assertEquals(List.of(MOVIE_1), getOrAwaitValue(liveDataFavorites));
    }

    @Test
    public void deleteNonExistingMovieAndGet() throws InterruptedException {
        favoritesViewModel.deleteMovie(MOVIE_1).blockingAwait();
        liveDataFavorites = favoritesViewModel.getMovies();
        assertEquals(List.of(), getOrAwaitValue(liveDataFavorites));
    }

/*
    @Test
    public void insertDeleteAndGetMovie() {
        mRepository.insertFavoriteMovie(MOVIE);
        mRepository.deleteFavoriteMovie(MOVIE);

        Observer<List<Movie>> observer = spy(movies -> {
            Log.e("", "" + movies.get(0));
            assertNotEquals(movies, Collections.emptyList());
        });

        mRepository.getAllFavoriteMovies().observe(mOwner, observer);
        assertTrue(mRepository.getAllFavoriteMovies().hasObservers());
        verify(observer).onChanged(Collections.emptyList());
    }
*/

/*
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private static final Movie MOVIE = new Movie(1L, "username", "", "", "", "", "");

    private AppDatabase mDatabase;
    private MoviesRepository mRepository;

    @Before
    public void initDb() {
        mDatabase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), AppDatabase.class)
                .allowMainThreadQueries()
                .build();
        mRepository = new MoviesRepository(ApplicationProvider.getApplicationContext());
    }

    @After
    public void closeDb() {
        mDatabase.close();
    }

    @Test
    public void insertAndGetUser() {
        // When inserting a new user in the data source
        mRepository.insertFavoriteMovie(MOVIE);

        // When subscribing to the emissions of the user
        mRepository.getAllFavoriteMovies().observe(this, movies -> {

        });
                .test()
                // assertValue asserts that there was only one emission of the user
                .assertValue(user -> {
                    // The emitted user is the expected one
                    return user != null && user.getId().equals(USER.getId()) &&
                            user.getUserName().equals(USER.getUserName());
                });
    }

    @Test
    public void updateAndGetUser() {
        // Given that we have a user in the data source
        mRepository.insertOrUpdateUser(USER).blockingAwait();

        // When we are updating the name of the user
        User updatedUser = new User(USER.getId(), "new username");
        mRepository.insertOrUpdateUser(updatedUser).blockingAwait();

        // When subscribing to the emissions of the user
        mDatabase.userDao().getUser()
                .test()
                // assertValue asserts that there was only one emission of the user
                .assertValue(user -> {
                    // The emitted user is the expected one
                    return user != null && user.getId().equals(USER.getId()) &&
                            user.getUserName().equals("new username");
                });
    }

    @Test
    public void deleteAndGetUser() {
        // Given that we have a user in the data source
        mRepository.insertOrUpdateUser(USER).blockingAwait();

        //When we are deleting all users
        mRepository.deleteAllUsers();
        // When subscribing to the emissions of the user
        mDatabase.userDao().getUser()
                .test()
                // check that there's no user emitted
                .assertNoValues();
    }

 */

    public static class LiveDataTestUtil {
        public static <T> T getOrAwaitValue(final LiveData<T> liveData) throws InterruptedException {
            final Object[] data = new Object[1];
            final CountDownLatch latch = new CountDownLatch(1);
            Observer<T> observer = new Observer<>() {
                @Override
                public void onChanged(@Nullable T o) {
                    data[0] = o;
                    latch.countDown();
                    liveData.removeObserver(this);
                }
            };
            liveData.observeForever(observer);
            // Don't wait indefinitely if the LiveData is not set.
            if (!latch.await(2, TimeUnit.SECONDS)) {
                throw new RuntimeException("LiveData value was never set.");
            }
            return (T) data[0];
        }
    }
}