package com.anddevcorp.composenavigation.ui.ext

import android.net.Uri
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import coil.compose.AsyncImage
import com.anddevcorp.composenavigation.model.Movie
import kotlinx.serialization.json.Json

val customShape = RoundedCornerShape(
    topStart = CornerSize(15.dp),
    topEnd = CornerSize(15.dp),
    bottomStart = CornerSize(15.dp),
    bottomEnd = CornerSize(15.dp)
)
val customBorderShape = RoundedCornerShape(
    topStart = CornerSize(8.dp),
    topEnd = CornerSize(8.dp),
    bottomStart = CornerSize(8.dp),
    bottomEnd = CornerSize(8.dp)
)

@Composable
fun GetImage(imageUrl:String, modifier: Modifier){
    AsyncImage(
        model = "https://image.tmdb.org/t/p/w1280$imageUrl",
        contentDescription = null,
        modifier = modifier
    )
}


inline fun <reified T> NavBackStackEntry.toRoute(key:String): T {
    val json = arguments?.getString(key) ?: error("No route argument found")
    return Json.decodeFromString(Uri.decode(json))
}

fun Movie.toDisplayString(): String {
    return """
        Title: $title
        Original Title: $originalTitle
        Overview: $overview
        Release Date: $releaseDate
        Backdrop Path: $backdropPath
        Popularity: $popularity
        Vote Average: $voteAverage
        Vote Count: $voteCount
        Adult: $adult
        Video: $video
        Original Language: $originalLanguage
    """.trimIndent()
}
