package sel.group9.squared2.ui.components.gameMap

import android.content.Context
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Looper
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import sel.group9.squared2.ui.theme.SquaredTheme
import sel.group9.squared2.ui.theme.blue
import sel.group9.squared2.ui.theme.red

class Tile(val lat: Double, val long: Double, val color: Color) {
    companion object {
        const val size = 0.0001
    }
}

private class TestUser(val color: Color, val latLng: LatLng)

val sterre = LatLng(51.0259, 3.7128)
private val tilesSterre = listOf(Tile(sterre.latitude, sterre.longitude, red), Tile(sterre.latitude + Tile.size, sterre.longitude, blue))
//private val playersSterre = listOf()
val congo = LatLng(0.0, 21.5154)
private val tilesCongo = listOf(Tile(congo.latitude, congo.longitude, red), Tile(congo.latitude + Tile.size, congo.longitude, blue))


private val users = listOf(TestUser(Color.Yellow, LatLng(sterre.latitude + 0.00005, sterre.longitude + 0.00005)))
@Composable
fun GameMap(fusedLocationClient: FusedLocationProviderClient) {
    val cameraPositionState = rememberCameraPositionState()

    var uiSettings by remember { mutableStateOf(MapUiSettings()) }
    var properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.TERRAIN, isMyLocationEnabled = true))
    }
    var centerToggle by remember { mutableStateOf(true) }

//    fusedLocationClient()

    fun onReady() {
        cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(cameraPositionState.position.target, 18.0f))
    }

    AskMapsPermission {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            uiSettings = uiSettings,
            properties = properties,
            onMapLoaded = { onReady() },
            cameraPositionState = cameraPositionState
        ) {
            users.forEach { user -> UserDot(latLng = user.latLng, color = user.color) }
//            UserDot(latLng = cameraPositionState.position.target)
            tilesSterre.forEach { tile -> SquaredTile(tile) }
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