package com.oliver_curtis.movies_list.data.repo

import com.oliver_curtis.movies_list.common.date.formatDate
import com.oliver_curtis.movies_list.data.source.impl.RemoteMovieDataSource
import com.oliver_curtis.movies_list.data.entity.MovieApiEntity
import com.oliver_curtis.movies_list.data.entity.MovieDetailsApiEntity
import com.oliver_curtis.movies_list.domain.model.Movie
import com.oliver_curtis.movies_list.helper.KMockito
import com.oliver_curtis.movies_list.helper.assertSame
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class MovieDBRepositoryTest {

    @Mock
    lateinit var movieDatabase: RemoteMovieDataSource

    private lateinit var movieDBRepository: MovieDBRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        movieDBRepository = MovieDBRepository(movieDatabase)
    }

    @Test
    fun given_onePageOfPopularMovies_whenGettingMovies_thenReturnMovies() {

        val expected  = Mockito.mock(MovieApiEntity::class.java)

        // WHEN we get movies from the database, we expect to receive the value stated
        KMockito.suspendedWhen {movieDatabase.getMovies(1)}.thenReturn(expected)

        // Given the following results from a real call
        val actual = runBlocking { movieDBRepository.getMovies(1) }

        if (actual != null){
            assertSame(expected.results.map { toMovie(it) }, actual) { i1, i2 -> i1.id == i2.id}
        }
    }

    private fun toMovie(entity: MovieDetailsApiEntity): Movie {

        entity.id = 528085

        val id = entity.id

        val posterPath = entity.poster_path

        val title = entity.title

        val votingAverage = entity.vote_average

        val releaseDate = entity.release_date
        val date = formatDate(releaseDate)

        return Movie(id, posterPath, title, votingAverage, date)

    }

}