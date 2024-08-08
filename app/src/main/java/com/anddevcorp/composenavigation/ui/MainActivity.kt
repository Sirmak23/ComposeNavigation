package com.anddevcorp.composenavigation.ui

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.anddevcorp.composenavigation.data.request.getRequestModel
import com.anddevcorp.composenavigation.model.Movie
import com.anddevcorp.composenavigation.model.MovieUiState
import com.anddevcorp.composenavigation.ui.ext.GetImage
import com.anddevcorp.composenavigation.ui.ext.customBorderShape
import com.anddevcorp.composenavigation.ui.ext.customShape
import com.anddevcorp.composenavigation.ui.ext.toDisplayString
import com.theapache64.rebugger.Rebugger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            MyNav(viewModel)
        }
    }

    @Composable
    fun MyNav(viewModel: MovieViewModel) {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "auth") {
            navigation(startDestination = "login", route = "auth") {
                composable("login") {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Login Screen")
                        Button(onClick = { navController.navigate("movie") }) {
                            Text("Go to Movies")
                        }
                    }
                }
            }
            navigation(startDestination = "movieList", route = "movie") {
                composable("movieList") {
                    MovieScreen(
                        onNavigateToImage = { movie ->
                            val movieJson = Json.encodeToString(movie)
                            val movies = Uri.encode(movieJson)
                            navController.navigate("movieImage/$movies")
                        },
                        viewModel = viewModel
                    )
                }
                composable("movieImage/{movie}") { backStackEntry ->
                    val movieJson = backStackEntry.arguments?.getString("movie") ?: ""
                    val decodedMovieJson = Uri.decode(movieJson)
                    val movie = Json.decodeFromString<Movie>(decodedMovieJson)

                    DetailScreen(movie) { selectedMovie ->
                        val movieJson = Json.encodeToString(selectedMovie)
                        val movies = Uri.encode(movieJson)
                        navController.navigate("movieDetail/$movies")
                    }
                }
                composable("movieDetail/{movie}") { backStackEntry ->
                    val movieJson = backStackEntry.arguments?.getString("movie")
                    val movie = Json.decodeFromString<Movie>(Uri.decode(movieJson))
                    MovieDetailTextScreen(movie)
                }
            }
        }
    }

//    @Composable
//    fun MyNav(viewModel: MovieViewModel) {
//        val navController = rememberNavController()
//
//        NavHost(navController = navController, startDestination = "movie") {
//
//            navigation(startDestination = "movieList", route = "movie") {
//                composable("movieList") {
//                    MovieScreen(
//                        onNavigateToImage = { movie ->
//                            val movies = movie.posterPath.replace("/","")
//                            navController.navigate("movieImage/$movies")
//                        },
//                        viewModel = viewModel
//                    )
//                }
//                composable("movieImage/{movie}") { backStackEntry ->
//                    val movie: String = backStackEntry.arguments?.getString("movie") ?: ""
//
//                    DetailScreen(movie) { selectedMovie ->
//                        navController.navigate("movieDetail")
//                    }
//                }
//
//            }
//        }
//    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MovieDetailTextScreen(movie: Movie) {
        CenterAlignedTopAppBar(title =
        {
            Text(
                modifier = Modifier, text = "Movie Detail",
                color = Color.Black
            )
        })
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = movie.toDisplayString(),
                style = TextStyle(fontSize = 16.sp)
            )
        }
    }


    @Composable
    fun DetailScreen(movie: Movie?, onClick: (Movie) -> Unit) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (movie != null) {
                GetImage(
                    "/${movie.posterPath}",
                    modifier = Modifier
                        .clickable { onClick(movie) }
                        .fillMaxSize()
                        .scale(1.8f)
                        .aspectRatio(1f)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "No Image Available",
                    modifier = Modifier.size(100.dp)
                )
            }
        }
    }

    @Composable
    fun MovieScreen(
        viewModel: MovieViewModel,
        onNavigateToImage: (Movie) -> Unit,
    ) {

        LaunchedEffect(Unit) {
            viewModel.getMovies(getRequestModel())
        }
        val movieUiState by viewModel.movies.collectAsState()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            when (movieUiState) {
                is MovieUiState.Success -> {
                    val movies = (movieUiState as MovieUiState.Success).movies
                    MovieList(movies = movies, onClick = { index ->
                        onNavigateToImage(movies[index])
                    })
                }

                MovieUiState.Empty -> {
                    Text(
                        text = "No Movie available",
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }

                is MovieUiState.Error -> {
                    Text(
                        text = "Error: ${(movieUiState as MovieUiState.Error).error}",
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }

                is MovieUiState.Loading -> {
                    CircularProgressIndicator()
                }

                else -> {}
            }
        }
    }

    @Composable
    fun MovieItem(movie: Movie, position: Int, onClick: (Int) -> Unit) {
        Rebugger(trackMap = mapOf("movie" to movie, "onClick" to onClick))
        Box(
            modifier = Modifier
                .padding(5.dp)
                .border(2.dp, Color.Black, customBorderShape)
                .background(Color.White, customShape)
                .height(250.dp)
                .clip(customShape)
                .clickable { onClick(position) }
        ) {
            GetImage(
                movie.posterPath,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
                    .scale(1.8f)
                    .aspectRatio(1f)
            )
        }
    }

    @Composable
    fun MovieList(
        movies: List<Movie>,
        onClick: (Int) -> Unit
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(10.dp),
        ) {
            itemsIndexed(
                movies,
                key = { _, movie -> movie.id },
                span = { _, _ -> GridItemSpan(1) }
            ) { index, movie ->
                MovieItem(movie = movie, position = index, onClick = onClick)
            }
        }
    }
}
