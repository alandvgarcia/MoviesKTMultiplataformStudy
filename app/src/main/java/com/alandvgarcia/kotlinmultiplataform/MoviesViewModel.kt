package com.alandvgarcia.kotlinmultiplataform

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alandvgarcia.kotlinmultiplataform.api_services.MovieApi
import com.alandvgarcia.kotlinmultiplataform.db.MovieEntity
import com.alandvgarcia.kotlinmultiplataform.db.MovieQueries
import com.alandvgarcia.kotlinmultiplataform.model.Movie
import com.alandvgarcia.kotlinmultiplataform.repository.DriverFactory
import com.alandvgarcia.kotlinmultiplataform.repository.MovieRepository
import com.alandvgarcia.kotlinmultiplataform.repository.createDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    val movies = MutableLiveData<List<MovieEntity>>()
    val movieApi = MovieApi("8aa61303fe43973122e7b287a5c13c42")
    val database = createDatabase(DriverFactory(application))

    private val repository = MovieRepository(database, movieApi)

    init {
        repository.getMovies(1){
            movies.postValue(it)
        }

        repository.getSavedMovies {
            it.forEach {
                Log.d("Database", it.title)
            }
        }
    }


}