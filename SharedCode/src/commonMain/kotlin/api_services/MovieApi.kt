package com.alandvgarcia.kotlinmultiplataform.api_services

import com.alandvgarcia.kotlinmultiplataform.model.Movie
import io.ktor.client.HttpClient
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.http.Url
import kotlinx.coroutines.*
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.parseList
import kotlin.coroutines.CoroutineContext

internal expect val ApplicationDispatcher: CoroutineDispatcher

class MovieApi(private val apiKey: String, private val language: String = "en-US") :
    CoroutineScope {

    companion object {
        private const val TIME_OUT = 15000L
    }

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + ApplicationDispatcher

    private var address =
        Url("https://api.themoviedb.org/3/movie/popular")

    private val client = HttpClient {
        install(HttpTimeout){
            requestTimeoutMillis = TIME_OUT
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    @ImplicitReflectionSerializer
    fun getPopularMovies(page: Int = 1, callback: (List<Movie>) -> Unit) {
        launch {
            val result = client.get<String>(address){
                url.parameters.append("page", page.toString())
                url.parameters.append("api_key", apiKey)
                url.parameters.build()
            }
            val json = Json(JsonConfiguration.Stable)
            val jsonElement = json.parseJson(result)
            val parsed =
                json.parseList<Movie>(jsonElement.jsonObject.getArray("results").toString())
            callback(parsed)
            client.close()
        }
    }
}


