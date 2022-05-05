package sel.group9.squared2.ui.components.gameMap

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import sel.group9.squared2.viewmodel.SquaredGameScreenViewModel

/*
This intercepts all touch events to the game map.
In case of a touch event, the follow player option will be toggled off.
This special interceptor is necessary as we want to reset the map location prior to
moving the screen. Else the GoogleMap may crash due to a move animation being interrupted.
 */
@Composable
fun GameMapTouchInterceptor(model: SquaredGameScreenViewModel, content: @Composable () -> Unit) {
    val followPlayer = model.followPlayer.collectAsState()

    Box (
        Modifier
            .fillMaxSize()
            .pointerInput(followPlayer.value) {
                // The followPlayer.value argument will make sure the awaitPointerEvent will be
                // reset every time the followPlayer state is changed.
                awaitPointerEventScope {
                    awaitPointerEvent()
                    model.setFollowPlayer(false)
                }
            }
    ) {
        content()
    }
}