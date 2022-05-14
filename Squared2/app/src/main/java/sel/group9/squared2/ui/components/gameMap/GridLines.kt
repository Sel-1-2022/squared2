package sel.group9.squared2.ui.components.gameMap

import androidx.compose.runtime.Composable
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.Polyline

@Composable
fun GridLines(cameraPositionState: CameraPositionState) {
    val position = cameraPositionState.position.target
    val zoom = cameraPositionState.position.zoom
    val meterPerDp = 262144.0 / Math.pow(2.0, zoom.toDouble())

    val longitudeCenterOffset = 128.0 * meterPerDp/1000.0
    val minLongitudeLine = ((position.longitude - longitudeCenterOffset) * 10000).toInt()
    val maxLongitudeLine = ((position.longitude + longitudeCenterOffset) * 10000).toInt()

    (minLongitudeLine..maxLongitudeLine).forEach {
        Polyline(points = listOf(LatLng(-90.0, it.toDouble()/10000), LatLng(90.0, it.toDouble()/10000)))
    }

}