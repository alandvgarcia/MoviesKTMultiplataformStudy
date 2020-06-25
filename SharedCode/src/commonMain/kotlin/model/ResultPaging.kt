package com.alandvgarcia.kotlinmultiplataform.model
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ResultPaging<T>(
    @SerialName("page")
    var page: Int? = 0,
    @SerialName("results")
    var results: T,
    @SerialName("total_pages")
    var totalPages: Int? = 0,
    @SerialName("total_results")
    var totalResults: Int? = 0
)

