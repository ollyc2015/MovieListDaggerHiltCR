package com.oliver_curtis.movies_list.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import com.oliver_curtis.movies_list.common.viewmodel.livedata.DefaultLiveDataProvider
import com.oliver_curtis.movies_list.common.viewmodel.CallResult
import com.oliver_curtis.movies_list.common.viewmodel.cr.DefaultDispatcherProvider
import com.oliver_curtis.movies_list.domain.interactor.MovieUseCase
import com.oliver_curtis.movies_list.domain.model.Movie
import com.oliver_curtis.movies_list.helper.KMockito
import com.oliver_curtis.movies_list.helper.providers.TestDispatcherProvider
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MovieViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    // mocked repository since that isn't the class under test, we can
    @Mock lateinit var defaultLiveDataProvider: DefaultLiveDataProvider
    @Mock lateinit var dispatcherProvider: DefaultDispatcherProvider
    @Mock private val testDispatcher = TestDispatcherProvider()

    @Mock lateinit var useCase: MovieUseCase

    @Mock lateinit var movieObserver: Observer<CallResult<List<Movie>?>>
    private val movieLiveData = MutableLiveData<CallResult<List<Movie>>>()

    // the actual class under test
    private lateinit var viewModel: MovieViewModel

    private val movie = Movie(1, "/ugZW8ocsrfgI95pnQ7wrmKDxIe.jpg", "Hard Kill", 4.7, "2020-08-25")

    // runs once before each test method is run
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(dispatcherProvider.main()).thenReturn(testDispatcher.main())

        viewModel = MovieViewModel(useCase, defaultLiveDataProvider)
    }

    @Test
    fun `givenSingleMovieEntity_whenObservingGetMovies_thenChangesObserved`() {
        val expected = listOf(movie)

        Mockito.`when`(defaultLiveDataProvider.liveDataInstance<List<Movie>>()).thenReturn(movieLiveData)
        KMockito.suspendedWhen {useCase.fetchMovies(1)}.thenReturn(expected)

        // call our methods under test and apply our observer whilst we are doing it.
        viewModel.getMovies(1).observeForever(movieObserver)

        // run assertion against observer
        argumentCaptor<CallResult<List<Movie>?>>().apply {
            verify(movieObserver).onChanged(capture())
        }.run { Assert.assertEquals(expected, firstValue.result()) }
    }

    @Test
    fun `givenErrorWhilstObservingGetMovies_thenErrorObserved`() {

        Mockito.`when`(defaultLiveDataProvider.liveDataInstance<List<Movie>>()).thenReturn(movieLiveData)
        // manipulate the response to trigger an error state
        KMockito.suspendedWhen {useCase.fetchMovies(1)}.thenReturn(null)

        // call our methods under test and apply our observer whilst we are doing it.
        viewModel.getMovies(1).observeForever(movieObserver)

        // run assertion against observer
        argumentCaptor<CallResult<List<Movie>?>>().apply {
            verify(movieObserver).onChanged(capture())
        }.run { Assert.assertEquals(null, firstValue.error()) }
    }

}