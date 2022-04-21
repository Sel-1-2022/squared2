package sel.group9.squared2.ui.components.gameMap

import android.Manifest
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.internal.notify
import sel.group9.squared2.ui.theme.SquaredTheme
import sel.group9.squared2.ui.theme.blue
import sel.group9.squared2.ui.theme.red
import sel.group9.squared2.viewmodel.SquaredGameMapViewModel

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
fun GameMap(model: SquaredGameMapViewModel) {
    val cameraPositionState = rememberCameraPositionState()
    val locationState = model.location.collectAsState()
    val nearbyUsersState = model.users.collectAsState()

    var uiSettings by remember { mutableStateOf(MapUiSettings()) }
    var properties by remember { mutableStateOf(MapProperties(mapType = MapType.TERRAIN, isMyLocationEnabled = true)) }

    fun onReady() {
        cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(locationState.value ?: LatLng(0.0, 0.0), 18.0f))
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        uiSettings = uiSettings,
        properties = properties,
        onMapLoaded = { onReady() },
        cameraPositionState = cameraPositionState
    ) {
        UserDot(latLng = locationState.value ?: LatLng(0.0, 0.0), Color.Black)
//        nearbyUsersState.value.forEach { user -> UserDot(latLng = LatLng(user.location.coordinates.get(0), user.location.coordinates.get(1)), color = Color(Integer.parseInt(user.color, 16))) }
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