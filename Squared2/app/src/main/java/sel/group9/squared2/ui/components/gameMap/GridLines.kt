package sel.group9.squared2.ui.components.gameMap

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.Polyline

// As in our SquaredGameScreenViewModel, we will approximate the size of the earth (in m)
// using 2^26 = 67 108 864. This is to slightly simplify some calculations and provide
// a greater margin of "culling" for our final grid.
private val meterPerDegree = 67108864/360

@Composable
fun GridLines(cameraPositionState: CameraPositionState) {
    val position = cameraPositionState.position.target
    val zoom = cameraPositionState.position.zoom
    val meterPerDp = 262144.0 / Math.pow(2.0, zoom.toDouble())

    val longitudeCenterOffset = 128.0 * meterPerDp/100000.0
    val minLongitudeLine = ((position.longitude - longitudeCenterOffset) * 10000).toInt()
    val maxLongitudeLine = ((position.longitude + longitudeCenterOffset) * 10000).toInt()

    val latitudeCenterOffset = 256.0 * meterPerDp/meterPerDegree
    val minLatitudeLine = ((position.latitude - latitudeCenterOffset) * 10000).toInt()
    val maxLatitudeLine = ((position.latitude + latitudeCenterOffset) * 10000).toInt()

    (minLongitudeLine..maxLongitudeLine).forEach {
        Polyline(points = listOf(
            LatLng(position.latitude-10.0, it.toDouble()/10000),
            LatLng(position.latitude+10.0, it.toDouble()/10000)),
            width = 4.0f,
            color = Color(0.7333f,0.7333f,0.7333f)
        )
    }

    (minLatitudeLine..maxLatitudeLine).forEach {
        Polyline(points = listOf(
            LatLng(it.toDouble()/10000, position.longitude-10.0),
            LatLng(it.toDouble()/10000, position.longitude+10.0)),
            width = 4.0f,
            color = Color(0.7333f,0.7333f,0.7333f)
        )
    }

}