package sel.group9.squared2.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import sel.group9.squared2.data.Square
import sel.group9.squared2.data.SquaredRepository
import sel.group9.squared2.data.User
import java.lang.Math.ceil
import java.lang.Math.floor
import javax.inject.Inject

@HiltViewModel
class SquaredGameScreenViewModel@Inject constructor(private val backend: SquaredRepository) : ViewModel() {
    private class SquareCaptureProgress(val lat: Double, val long: Double, var startMillis: Long)

    private val sterre = LatLng(51.0259, 3.7128)
    private val _location: MutableStateFlow<LatLng> = MutableStateFlow(sterre)
    var location: StateFlow<LatLng> = _location

    val mapUiSettings = mutableStateOf(MapUiSettings(scrollGesturesEnabled = true, myLocationButtonEnabled = false, zoomControlsEnabled = false, compassEnabled = false))
    val mapProperties = mutableStateOf(MapProperties(mapType = MapType.TERRAIN, isMyLocationEnabled = true))

    var cameraPositionState: CameraPositionState = CameraPositionState(CameraPosition(LatLng(0.0, 0.0), 19.0f, 0.0f, 0.0f))
    var cameraPositionStateJob: Job? = null

    private val _users: MutableStateFlow<List<User>> = MutableStateFlow(listOf())
    var users: StateFlow<List<User>> = _users

    private val _followPlayer: MutableStateFlow<Boolean> = MutableStateFlow(true)
    var followPlayer: StateFlow<Boolean> = _followPlayer

    private val _squares: MutableStateFlow<List<Square>> = MutableStateFlow(listOf())
    var squares: StateFlow<List<Square>> = _squares

    private val _showGrid: MutableStateFlow<Boolean> = MutableStateFlow(true)
    var showGrid: StateFlow<Boolean> = _showGrid

    private val squareCaptureProgress: MutableStateFlow<SquareCaptureProgress?> = MutableStateFlow(null)

    init {
        initialiseLocationUpdates()
        initialiseServerLocationUpdate()
        initialiseServerPlaceTile()
        initialiseNearbyUserUpdates()
        initialiseSquares()
    }

    private fun initialiseLocationUpdates() {
        viewModelScope.launch {
            backend
                .getLocationFlow(50)
                .stateIn(viewModelScope, SharingStarted.Lazily, null)
                .collect { x ->
                    x?.addOnCompleteListener { y ->
                        _location.value = LatLng(y.result?.latitude ?: 0.0, y.result?.longitude ?: 0.0)
                    }
                }
        }
    }

    private fun initialiseNearbyUserUpdates() {
        viewModelScope.launch {
            location.collect {
                    latLng ->
                val id = backend.getId()
                if (id !== null) {
                    val users = backend
                        .nearbyUser(latLng.latitude, latLng.longitude, 10.0)
                        .filter {
                        user -> user._id != id
                    }
                    _users.value = users
                }
            }
        }
    }

    private fun initialiseServerLocationUpdate() {
        viewModelScope.launch {
            location.collect { y ->
                val id = backend.getId()
                val latitude = y.latitude
                val longitude = y.longitude

                if (id !== null) {
                    backend.patchUser( lat = latitude, long = longitude)
                }
            }
        }
    }

    private fun initialiseServerPlaceTile() {
        viewModelScope.launch {
            location.collect { y ->
                val latitude = y.latitude
                val longitude = y.longitude
                val captureProgress = squareCaptureProgress.value
                val currentMillis = System.currentTimeMillis()

                if (captureProgress !== null &&
                    latitude >= captureProgress.lat && latitude <= captureProgress.lat + Square.size &&
                    longitude >= captureProgress.long && longitude <= captureProgress.long + Square.size) {
                        if (currentMillis - captureProgress.startMillis > 5000) {
                            backend.placeTile(captureProgress.lat, captureProgress.long)
                        }
                } else {
                    squareCaptureProgress.value = SquareCaptureProgress(floor(latitude * 10000)/10000, floor(longitude * 10000)/10000, currentMillis)
                }
            }
        }
    }

    /*
    The relation between GoogleMap zoom level (n) and the width of the earth in dp is given by:
    256*2^n
    We can derive the amount of m per dp displayed as approx. 40 075 000 / (256 * 2^n)
    To simplify our calculation we will use 67 108 864 as the width of the earth.
    In doing so we can simplify our calculation to: 262144 / 2^n
    To calculate the distance of tiles from ourselves that we wish to request we have to do:

     */
    private fun initialiseSquares() {
        viewModelScope.launch {
            location.collect { location ->
                val position = cameraPositionState.position.target
                val zoom = cameraPositionState.position.zoom
                val squaresPerDp = 262144.0 / (10 * Math.pow(2.0, zoom.toDouble()))
                val squareDistance = 256 * squaresPerDp
                cameraPositionState.position
                _squares.value = backend.nearbyTiles(position.latitude, position.longitude, squareDistance)
            }
        }
    }

    fun initialiseCameraPositionState() {
        cameraPositionStateJob = viewModelScope.launch {
            location.collect { latLng ->
                if (followPlayer.value && !cameraPositionState.isMoving) {
                    cameraPositionState.animate(CameraUpdateFactory.newLatLng(latLng))
                }
            }
        }
    }

    fun createNewCameraPositionState() {
        if (cameraPositionStateJob !== null) {
            cameraPositionStateJob?.cancel()
        }

        val position = cameraPositionState.position
        cameraPositionState = CameraPositionState(position)
    }

    fun setFollowPlayer(value: Boolean) {
        if (!value) {
            cameraPositionState.move(CameraUpdateFactory.newCameraPosition(cameraPositionState.position))
            cameraPositionStateJob?.cancel()
        }

        _followPlayer.value = value

        if (value) initialiseCameraPositionState()
    }

    fun toggleShowGrid() {
        _showGrid.value = !_showGrid.value
    }

    fun resetOrientation() {
        val latLng = cameraPositionState.position.target
        val tilt = cameraPositionState.position.tilt
        val zoom = cameraPositionState.position.zoom
        cameraPositionState.move(CameraUpdateFactory.newCameraPosition(CameraPosition(latLng, zoom, tilt, 0.0f)))
    }

    fun getColor(): StateFlow<Int> {
        return backend.getColor()
    }
}