package sel.group9.squared2.ui.components.gameMap
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Polygon

@Composable
fun SquaredTile(tile: Tile) {
    val upperLeft = LatLng(tile.lat, tile.long)
    val upperRight = LatLng(tile.lat, tile.long + Tile.size)
    val lowerRight = LatLng(tile.lat + Tile.size, tile.long + Tile.size)
    val lowerLeft = LatLng(tile.lat + Tile.size, tile.long)

    val fillColor = Color(tile.color.red, tile.color.green, tile.color.blue, 0.2f)
    val strokeColor = Color(tile.color.red, tile.color.green, tile.color.blue, 0.4f)

    return Polygon(
        points = listOf(upperLeft, upperRight, lowerRight, lowerLeft),
        fillColor = fillColor,
        strokeColor = strokeColor,
        strokeWidth =2f
    )
}