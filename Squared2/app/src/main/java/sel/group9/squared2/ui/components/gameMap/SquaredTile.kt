package sel.group9.squared2.ui.components.gameMap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Polygon
import sel.group9.squared2.data.Square
import sel.group9.squared2.ui.theme.colorList

@Composable
fun SquaredTile(tile: Square) {
    val upperLeft = LatLng(tile.lat, tile.lon)
    val upperRight = LatLng(tile.lat, tile.lon + Square.size)
    val lowerRight = LatLng(tile.lat - Square.size, tile.lon + Square.size)
    val lowerLeft = LatLng(tile.lat - Square.size, tile.lon)
    val color = colorList[tile.color]

    val fillColor = Color(color.red, color.green, color.blue, 0.1f)
    val strokeColor = Color(color.red, color.green, color.blue, 0.2f)

    return Polygon(
        points = listOf(upperLeft, upperRight, lowerRight, lowerLeft),
        fillColor = fillColor,
        strokeColor = strokeColor,
        strokeWidth =2f
    )
}