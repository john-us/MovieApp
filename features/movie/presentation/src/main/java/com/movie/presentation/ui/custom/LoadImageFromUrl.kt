import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Scale

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
