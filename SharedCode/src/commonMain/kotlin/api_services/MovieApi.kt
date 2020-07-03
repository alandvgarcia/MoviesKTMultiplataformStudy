package com.alandvgarcia.kotlinmultiplataform.api_services

import com.alandvgarcia.kotlinmultiplataform.model.Movie
import com.alandvgarcia.kotlinmultiplataform.model.ResultPaging
import io.ktor.client.HttpClient
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.http.Url
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

internal expect val ApplicationDispatcher: CoroutineDispatcher

class MovieApi(private val apiKey: String, private val language: String = "en-US"): CoroutineScope {

    companion object {
        private const val TIME_OUT = 15000L
    }


    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + ApplicationDispatcher

    private var address =
        Url("https://api.themoviedb.org/3/movie/popular")

    private val client by lazy {
        HttpClient {
            install(HttpTimeout) {
                requestTimeoutMillis = TIME_OUT
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }

    fun getPopularMovies(page: Int = 1, callback: (List<Movie>) -> Unit) {
        launch {
            val result = client.get<ResultPaging<List<Movie>>>(address) {
                url.parameters.append("page", page.toString())
                url.parameters.append("api_key", apiKey)
                url.parameters.build()
            }
            callback(result.results)
        }
    }


}


