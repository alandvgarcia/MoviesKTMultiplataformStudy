package com.alandvgarcia.kotlinmultiplataform

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alandvgarcia.kotlinmultiplataform.api_services.MovieApi
import com.alandvgarcia.kotlinmultiplataform.db.MovieEntity
import com.alandvgarcia.kotlinmultiplataform.db.MovieQueries
import com.alandvgarcia.kotlinmultiplataform.model.Movie
import com.alandvgarcia.kotlinmultiplataform.repository.DriverFactory
import com.alandvgarcia.kotlinmultiplataform.repository.createDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    val movies = MutableLiveData<List<Movie>>()
    val movieApi = MovieApi("8aa61303fe43973122e7b287a5c13c42")

    init {

        val database = createDatabase(DriverFactory(application))

        val newMovie = MovieEntity(key = 1, id = 1, title = "Filme legal")

        database.movieQueries.insertItem(newMovie.id, newMovie.title)

        println(database.movieQueries.selectAll().executeAsList())

        viewModelScope.launch(Dispatchers.IO) {
            movieApi.getPopularMovies {

                movies.postValue(it)
            }
        }

    }


}