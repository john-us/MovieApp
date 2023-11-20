import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Scale
import com.movie.presentation.R
import com.movie.presentation.ui.customcomposable.MovieProgressBar

@Composable
fun CustomImage(
    url: String,
    fallbackResId: Int,
    defaultResId: Int,
    contentDescription: String,
    contentScale: ContentScale = ContentScale.Crop,
    modifier: Modifier
) {

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(data = url)
            .apply(block = fun ImageRequest.Builder.() {
                fallback(fallbackResId)
                error(defaultResId)
                placeholder(fallbackResId)
                scale(Scale.FILL)
                diskCachePolicy(CachePolicy.ENABLED)
            }).build()
    )

    Image(
        painter = painter,
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier
    )
}
@Preview(showBackground = true)
@Composable
fun CustomImagePreview() {
    MaterialTheme {
        CustomImage(
            url = "",
            fallbackResId = R.drawable.ic_launcher_background,
            defaultResId = R.drawable.ic_launcher_background,
            contentDescription = "",
            modifier = Modifier
                .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.space_5)))
                .size(dimensionResource(id = R.dimen.space_60))
        )
    }
}
