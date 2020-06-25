package com.alandvgarcia.kotlinmultiplataform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.Composable
import androidx.compose.onCommit
import androidx.compose.remember
import androidx.compose.state
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.ui.core.Text
import androidx.ui.core.setContent
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.layout.LayoutPadding
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Card
import androidx.ui.unit.dp
import com.alandvgarcia.kotlinmultiplataform.model.Movie
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val viewModel = MoviesViewModel()

        super.onCreate(savedInstanceState)
        setContent {
            mainLayout(viewModel)
        }

    }
}

@Composable
fun mainLayout(moviesViewModel: MoviesViewModel) {
    MaterialTheme {
        val movies = observe(moviesViewModel.movies )
        VerticalScroller {
            Column {
                movies?.forEach { movie ->
                    Row(movie)
                }
            }
        }
    }
}

@Composable
fun <T> observe(data: LiveData<T>): T? {
    var result by state { data.value }
    val observer = remember { Observer<T> { result = it } }

    onCommit(data) {
        data.observeForever(observer)
        onDispose { data.removeObserver(observer) }
    }

    return result
}

@Composable
fun Row(movie: Movie) {
    MaterialTheme {
            Text(text = "${movie.title} (${movie.releaseDate})", modifier = LayoutPadding(16.dp))

    }
}
