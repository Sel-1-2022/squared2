package sel.group9.squared2.ui.components.gameMap

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import sel.group9.squared2.ui.theme.SquaredTheme
import sel.group9.squared2.ui.theme.blue
import sel.group9.squared2.ui.theme.red

private class Tile(val lat: Double, val long: Double, val color: Color) {
    companion object {
        const val size = 0.0001
    }

    @Composable
    fun getPoly() {
        val upperLeft = LatLng(lat, long)
        val upperRight = LatLng(lat, long + size)
        val lowerRight = LatLng(lat + size, long + size)
        val lowerLeft = LatLng(lat + size, long)

        return Polygon(
            points = listOf(upperLeft, upperRight, lowerRight, lowerLeft),
            fillColor = color,
            strokeColor = Color(
                red = 0f,
                green = 0f,
                blue = 0f,
                alpha = 0.5f
            ),
            strokeWidth =2f
        )
    }
}

val sterre = LatLng(51.0259, 3.7128)
private val tilesSterre = listOf(Tile(sterre.latitude, sterre.longitude, red), Tile(sterre.latitude + Tile.size, sterre.longitude, blue))
//private val playersSterre = listOf()
val congo = LatLng(0.0, 21.5154)
private val tilesCongo = listOf(Tile(congo.latitude, congo.longitude, red), Tile(congo.latitude + Tile.size, congo.longitude, blue))

@Composable
fun GameMap() {
    val cameraPositionState = rememberCameraPositionState()

    var uiSettings by remember { mutableStateOf(MapUiSettings()) }
    var properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.TERRAIN, isMyLocationEnabled = true))
    }

    fun onReady() {
        val sydney = LatLng(-34.0, 151.0)
        cameraPositionState.move(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(sterre, 100f)))
    }

    AskMapsPermission {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            uiSettings = uiSettings,
            properties = properties,
            onMapLoaded = { onReady() },
            cameraPositionState = cameraPositionState
        ) {
            tilesSterre.forEach { tile -> tile.getPoly() }

        }
    }
}

@Composable
@Preview
fun GameMapPreview() {
    SquaredTheme {
        GameMap()
    }
}