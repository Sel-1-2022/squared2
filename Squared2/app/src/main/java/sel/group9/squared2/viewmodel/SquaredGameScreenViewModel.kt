package sel.group9.squared2.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import sel.group9.squared2.data.Square
import sel.group9.squared2.data.SquaredRepository
import sel.group9.squared2.data.User
import sel.group9.squared2.data.UserLocation
import java.lang.Math.ceil
import java.lang.Math.floor
import javax.inject.Inject

@HiltViewModel
class SquaredGameScreenViewModel@Inject constructor(private val backend: SquaredRepository) : ViewModel() {
    private class SquareCaptureProgress(val lat: Double, val long: Double, var startMillis: Long)

    private val _location: MutableStateFlow<LatLng> = MutableStateFlow(LatLng(0.0, 0.0))
    var location: StateFlow<LatLng> = _location

    val mapUiSettings = mutableStateOf(MapUiSettings(scrollGesturesEnabled = true, myLocationButtonEnabled = false, zoomControlsEnabled = false, compassEnabled = false))
    val mapProperties = mutableStateOf(MapProperties(mapType = MapType.TERRAIN, isMyLocationEnabled = true, minZoomPreference = 18.5.toFloat()))

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

    private var isPlacingTileLock = false

    init {
        initialiseLocationUpdates()
        initialiseNetworkRequests()
//        initialiseFrequentNetworkRequests()
    }

    private fun initialiseLocationUpdates() {
        viewModelScope.launch {
            backend
                .getLocationFlow(100)
                .stateIn(viewModelScope, SharingStarted.Lazily, null)
                .collect { x ->
                    x?.addOnCompleteListener { y ->
                        _location.value = LatLng(y.result?.latitude ?: 0.0,
                                                y.result?.longitude ?: 0.0)
                    }
                }
        }
    }

    private fun initialiseNetworkRequests() {
        viewModelScope.launch {
            while (true) {
                nearbyUserUpdates()
                serverLocationUpdates()
                serverPlaceTile()
                updateNearbySquares()
                delay(1000)
            }
        }
    }

    private suspend fun nearbyUserUpdates() {
        val id = backend.getId()
        val location = cameraPositionState.position.target
        if (id !== null) {
            val users = backend
                .nearbyUser(UserLocation(location.latitude, location.longitude), 100.0)
            if(users!=null)
            _users.value = users.filter { user -> user._id != id }
        }
    }

    private suspend fun serverLocationUpdates() {
        val id = backend.getId()
        val location = location.value
        val latitude =  location.latitude
        val longitude = location.longitude

        if (id !== null) {
            backend.patchUser( UserLocation(lat = latitude, lon = longitude))
        }
    }


    private suspend fun updateNearbySquares() {
        val position = cameraPositionState.position.target
        val nearby = backend.nearbyTiles(UserLocation(position.latitude, position.longitude),
            calculateSquareDistance())
        if(nearby!=null)
            _squares.value = nearby
    }

    /*
    The relation between GoogleMap zoom level (n) and the width of the earth in dp is given by:
    256*2^n
    We can derive the amount of m per dp displayed as approx. 40 075 000 / (256 * 2^n)
    To simplify our calculation we will use 2^26 = 67 108 864 as the width of the earth.
    (This will also give us some larger margins out of screen.)
    In doing so we can simplify our calculation to: 262144 / 2^n
    To calculate the distance (in amount of tiles) relative to the camera position that we wish
    to request we have to do:
    (h/2) * 262144 / (t * 2^n), with h = screen height; t = tile height in meters.
    */
    private fun calculateSquareDistance(): Double {
        val zoom = cameraPositionState.position.zoom
        val squaresPerDp = 262144.0 / (10 * Math.pow(2.0, zoom.toDouble()))
        return 256 * squaresPerDp // TODO: Change 256 to screen height
    }

    private fun inCaptureSquare(latitude: Double, longitude: Double): Boolean {
        val captureProgress = squareCaptureProgress.value
        return captureProgress != null &&
                latitude <= captureProgress.lat && latitude >= captureProgress.lat - Square.size &&
                longitude >= captureProgress.long && longitude <= captureProgress.long + Square.size
    }

    private fun serverPlaceTile() {
        viewModelScope.launch {
            location.collect { y ->
                val latitude = y.latitude
                val longitude = y.longitude
                val captureProgress = squareCaptureProgress.value
                val currentMillis = System.currentTimeMillis()
                if ( captureProgress != null && inCaptureSquare(latitude, longitude)) {
                    if (!isPlacingTileLock && currentMillis - captureProgress.startMillis > 1000) {
                        isPlacingTileLock = true
                        backend.placeTile(UserLocation(captureProgress.lat, captureProgress.long))
                        squareCaptureProgress.value = SquareCaptureProgress(ceil(latitude * 10000)/10000, floor(longitude * 10000)/10000, currentMillis)
                        isPlacingTileLock = false
                    }
                } else {
                    squareCaptureProgress.value = SquareCaptureProgress(ceil(latitude * 10000)/10000, floor(longitude * 10000)/10000, currentMillis)
                }
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