package sel.group9.squared2.ui.components.gameMap

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import sel.group9.squared2.ui.theme.SquaredTheme
import sel.group9.squared2.ui.theme.blue
import sel.group9.squared2.ui.theme.colorList
import sel.group9.squared2.ui.theme.red
import sel.group9.squared2.viewmodel.SquaredGameScreenViewModel

class Tile(val lat: Double, val long: Double, val color: Color) {
    companion object {
        const val size = 0.0001
    }
}

val sterre = LatLng(51.0259, 3.7128)
private val tilesSterre = listOf(Tile(sterre.latitude, sterre.longitude, red), Tile(sterre.latitude + Tile.size, sterre.longitude, blue))

@Composable
fun GameMap(model: SquaredGameScreenViewModel) {
    // The model needs to create a new CameraPositionState every time the map view is
    // rerendered because only one map can be associated with a CameraPositionState.
    model.createNewCameraPositionState()
    val cameraPositionState: CameraPositionState = model.cameraPositionState

//    var uiSettings by remember { model.mapUiSettings }
//    var properties by remember { model.mapProperties }
//    val properties = model.mapProperties
    val nearbyUsersState = model.users.collectAsState()
    var uiSettings by remember { model.mapUiSettings }
    var properties by remember { model.mapProperties }

    fun onReady() {
        // The model has to initialise the camera position state after the map
        // has initialised because otherwise the CameraUpdateFactory singleton (?)
        // will not be properly initialised.
        model.initialiseCameraPositionState()
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        uiSettings = uiSettings,
        properties = properties,
        onMapLoaded = { onReady() },
        cameraPositionState = cameraPositionState
    ) {
        nearbyUsersState.value.forEach { user ->
            UserDot(latLng = LatLng(user.location.coordinates.get(1), user.location.coordinates.get(0)), color = colorList[user.color])
        }
        tilesSterre.forEach { tile -> SquaredTile(tile) }
    }
}

@Composable
@Preview
fun GameMapPreview() {
    SquaredTheme {
//        GameMap()
    }
}