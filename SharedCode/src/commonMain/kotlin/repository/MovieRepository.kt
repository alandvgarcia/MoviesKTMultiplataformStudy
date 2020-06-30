package com.alandvgarcia.kotlinmultiplataform.repository

import com.alandvgarcia.kotlinmultiplataform.api_services.MovieApi
import com.alandvgarcia.kotlinmultiplataform.db.MovieDatabase
import com.alandvgarcia.kotlinmultiplataform.db.MovieEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MovieRepository(private val movieDatabase: MovieDatabase, private val movieApi: MovieApi) :
    CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default


    fun getMovies(page: Int, callback: (List<MovieEntity>) -> Unit) {
        launch {
            movieApi.getPopularMovies(page) {
                val listMovies = mutableListOf<MovieEntity>()
                it.forEach {
                    val movieEntity = MovieEntity(
                        key = -1,
                        id = it.id?.toLong() ?: 0,
                        title = it.title ?: "No description"
                    )
                    listMovies.add(movieEntity)
                }
                callback(listMovies)
            }
        }
    }

    fun getSavedMovies(callback: (List<MovieEntity>) -> Unit){
        launch {
            callback(movieDatabase.movieQueries.selectAll().executeAsList())
        }
    }

    fun insertMovie(movie: MovieEntity) {
        launch {
            movieDatabase.movieQueries.insertItem(movie.id, movie.title)
        }
    }

    fun removeAllMovies(){
        launch {
            movieDatabase.movieQueries.deleteAll()
        }
    }

}