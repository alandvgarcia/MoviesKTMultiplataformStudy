package com.alandvgarcia.kotlinmultiplataform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alandvgarcia.kotlinmultiplataform.api_services.MovieApi
import com.alandvgarcia.kotlinmultiplataform.model.Movie
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel(){

    val movies = MutableLiveData<List<Movie>>()

    init {
        MovieApi("8aa61303fe43973122e7b287a5c13c42")
            .getPopularMovies {
            viewModelScope.launch {
                movies.postValue(it)
            }
        }
    }


}