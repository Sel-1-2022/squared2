package sel.group9.squared2.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.CameraUpdate
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
import sel.group9.squared2.data.SquaredRepository
import sel.group9.squared2.data.User
import javax.inject.Inject

@HiltViewModel
class SquaredGameScreenViewModel@Inject constructor(private val backend: SquaredRepository) : ViewModel() {
    private val sterre = LatLng(51.0259, 3.7128)
    private val _location: MutableStateFlow<LatLng> = MutableStateFlow(sterre)
    var location: StateFlow<LatLng> = _location

    var mapUiSettings = mutableStateOf(MapUiSettings(scrollGesturesEnabled = true, myLocationButtonEnabled = false, zoomControlsEnabled = false))
    var mapProperties = mutableStateOf(MapProperties(mapType = MapType.TERRAIN, isMyLocationEnabled = true))

    private val _users: MutableStateFlow<List<User>> = MutableStateFlow(listOf())
    var users: StateFlow<List<User>> = _users

    var cameraPositionState: CameraPositionState = CameraPositionState(CameraPosition(LatLng(0.0, 0.0), 18.0f, 0.0f, 0.0f))
    var cameraPositionStateJob: Job? = null

    private val followPlayer: MutableStateFlow<Boolean> = MutableStateFlow(true)

    init {
        initialiseLocationUpdates()
        initialiseServerLocationUpdate()
        initialiseNearbyUserUpdates()
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

                val users = backend.nearbyUser(latLng.latitude, latLng.longitude, 1000.0)
                Log.v("test","i guess")

                _users.value = users
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

    fun initialiseCameraPositionState() {
        cameraPositionStateJob = viewModelScope.launch {
            location.collect { latLng ->
                Log.v("FollowPlayer", "${followPlayer.value}")
                if (followPlayer.value) {
                    cameraPositionState.move(CameraUpdateFactory.newLatLng(latLng))
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

    fun toggleCenter() {
        followPlayer.value = !followPlayer.value
    }

    fun resetOrientation() {
        val latLng = cameraPositionState.position.target
        val tilt = cameraPositionState.position.tilt
        val zoom = cameraPositionState.position.zoom
        cameraPositionState.move(CameraUpdateFactory.newCameraPosition(CameraPosition(latLng, zoom, tilt, 0.0f)))
    }
}