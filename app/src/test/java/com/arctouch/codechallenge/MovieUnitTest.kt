package com.arctouch.codechallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.repository.MovieRepository
import com.arctouch.codechallenge.util.Event
import com.arctouch.codechallenge.viewmodel.details.DetailsViewModel
import io.reactivex.Observable
import org.junit.*
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MovieUnitTest : KoinTest {

    private var movieId = 1L

    lateinit var detailsViewModel: DetailsViewModel

    private val movie = Movie(
            id = movieId,
            title = "",
            backdropPath = null,
            genreIds = null,
            genres = null,
            overview = null,
            posterPath = null,
            releaseDate = null
    )

    @Mock
    lateinit var observer: Observer<Event<Movie>>

    @Mock
    lateinit var repository: MovieRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @After
    fun after() {
        stopKoin()
    }

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        detailsViewModel = DetailsViewModel(repository)
    }

    @Test
    fun testGetMovie() {
        Mockito.`when`(repository.movie(movieId)).thenReturn(Observable.just(movie))

        detailsViewModel.event.observeForever(observer)
        detailsViewModel.movie(movieId)
        Mockito.verify(observer).onChanged(Event(data = movie))
    }

    @Test
    fun testGetMovieError(){
        val erro = IllegalAccessError("Error")
        Mockito.`when`(repository.movie(movieId)).thenReturn(Observable.error(erro))

        detailsViewModel.event.observeForever(observer)
        detailsViewModel.movie(movieId)
        Mockito.verify(observer).onChanged(Event(error = erro))
    }

}