package sel.group9.squared2.viewmodel

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import sel.group9.squared2.data.SquaredRepository
import sel.group9.squared2.data.User
import javax.inject.Inject

@HiltViewModel
class SquaredGameMapViewModel@Inject constructor(private val backend: SquaredRepository) : ViewModel() {
    private val _location: MutableStateFlow<LatLng?> = MutableStateFlow(null)
    var location: StateFlow<LatLng?> = _location

    private val _users: MutableStateFlow<List<User>> = MutableStateFlow(listOf())
    var users: StateFlow<List<User>> = _users

    // TODO: tiles

    init {
        initialiseLocationUpdates()
//        initialiseNearbyUserUpdates()
    }

    private fun initialiseLocationUpdates() {
        viewModelScope.launch {
            backend
                .getLocationFlow(50)
                .stateIn(viewModelScope, SharingStarted.Lazily, null)
                .collect { x -> onLocationUpdate(x) }
        }
    }

    private fun onLocationUpdate(locationTask: Task<Location>?) {
        locationTask?.addOnCompleteListener { y ->
            _location.value =
                LatLng(y.result?.latitude ?: 0.0, y.result?.longitude ?: 0.0)
        }

        locationTask?.addOnCompleteListener { y ->
            viewModelScope.launch {
                val id = backend.getId()
                val latitude = y.result?.latitude
                val longitude = y.result?.longitude

                if (id !== null && latitude !== null && longitude !== null) {
                    backend.patchUser(id, lat = latitude, long = longitude)
                }
            }
        }
    }

    private fun initialiseNearbyUserUpdates() {
        viewModelScope.launch {
            val latLng = location.value
            if (latLng !== null) {
                val users = backend.nearbyUser(latLng.latitude, latLng.longitude, 100)
                _users.value = users
            }
        }
    }
}