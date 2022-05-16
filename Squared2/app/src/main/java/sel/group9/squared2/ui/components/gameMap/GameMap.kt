package sel.group9.squared2.ui.components.gameMap

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import sel.group9.squared2.ui.theme.SquaredTheme
import sel.group9.squared2.ui.theme.colorList
import sel.group9.squared2.viewmodel.SquaredGameScreenViewModel

@Composable
fun GameMap(model: SquaredGameScreenViewModel) {
    // The model needs to create a new CameraPositionState every time the map view is
    // rerendered because only one map can be associated with a CameraPositionState.
    model.createNewCameraPositionState()
    val cameraPositionState: CameraPositionState = model.cameraPositionState

    val nearbyUsersState = model.users.collectAsState()
    val nearbySquaresState = model.squares.collectAsState()
    val locationState = model.location.collectAsState()
//    val color = model.getColor().collectAsState()
    val showGrid = model.showGrid.collectAsState()
    var uiSettings by remember { model.mapUiSettings }
    var properties by remember { model.mapProperties }

    fun onReady() {
        // The model has to initialise the camera position state after the map
        // has initialised because otherwise the CameraUpdateFactory singleton (?)
        // will not be properly initialised.
        model.initialiseCameraPositionState()
    }

    GameMapTouchInterceptor(model = model) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            uiSettings = uiSettings,
            properties = properties,
            onMapLoaded = { onReady() },
            cameraPositionState = cameraPositionState
        ) {
            nearbyUsersState.value.forEach { user ->
                UserDot(
                    latLng = LatLng(
                        user.location.coordinates[1],
                        user.location.coordinates[0]
                    ), color = colorList[user.color]
                )
            }
            if (showGrid.value) {
                nearbySquaresState.value.forEach { square ->
                    SquaredTile(square)
                }
                GridLines(cameraPositionState = cameraPositionState)
            }
            
        }
    }
}

@Composable
@Preview
fun GameMapPreview() {
    SquaredTheme {
//        GameMap()
    }
}