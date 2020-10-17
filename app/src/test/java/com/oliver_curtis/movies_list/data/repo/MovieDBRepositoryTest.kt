package com.oliver_curtis.movies_list.data.repo

import com.oliver_curtis.movies_list.data.db.MovieDatabase
import com.oliver_curtis.movies_list.data.entity.MovieApiEntity
import com.oliver_curtis.movies_list.data.entity.MovieDetailsApiEntity
import com.oliver_curtis.movies_list.domain.model.Movie
import com.oliver_curtis.movies_list.helper.KMockito
import io.reactivex.observers.TestObserver
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class MovieDBRepositoryTest {


    @Mock
    lateinit var movieDatabase: MovieDatabase

    private val movieTestObserver = TestObserver<List<Movie>>()

    private val movieEntity = MovieApiEntity(
        1,
        arrayListOf(
            MovieDetailsApiEntity(
                false,
                "/aO5ILS7qnqtFIprbJ40zla0jhpu.jpg",
                arrayListOf(1, 2, 3),
                741067,
                "en",
                "Welcome to Sudden Death",
                "some overview",
                1892.824,
                "/elZ6JCzSEvFOq4gNjNeZsnRFsvj.jpg",
                "2020-09-29",
                "Welcome to Sudden Death",
                false,
                6.7, 99
            ),
            MovieDetailsApiEntity(
                false,
                "/aO5ILS7qnqtFIprbJ40zla0jhpu.jpg",
                arrayListOf(1, 2, 3),
                741067,
                "en",
                "2067",
                "some overview",
                1892.824,
                "/elZ6JCzSEvFOq4gNjNeZsnRFsvj.jpg",
                "2020-09-29",
                "Enola Holmes",
                false,
                6.7, 99
            )
        ),
        500,
        1000
    )

    private val movie = Movie(1, "/ugZW8ocsrfgI95pnQ7wrmKDxIe.jpg", "Hard Kill", 4.7, "2020-08-25")


    private lateinit var movieDBRepository: MovieDBRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        movieDBRepository = MovieDBRepository(movieDatabase)
    }

    @Test
    fun given_onePageOfPopularMovies_whenGettingMovies_thenReturnMovies() {

        val expected = listOf(movie)

        // WHEN we get movies from the database, we expect to receive the value stated
        Mockito.`when`(movieDatabase.getMovies(1)).thenReturn(movieEntity)

        // GIVEN movies actually requested from the DB
        KMockito.suspendedWhen {movieDBRepository.getMovies(1)}.thenReturn(expected)

        // THEN assert that our returned object has a id value at position 0 as per our expected object
        val result = movieTestObserver.values()[0]

        assertEquals(movieEntity.results[0].title, result[0].title)

    }
}