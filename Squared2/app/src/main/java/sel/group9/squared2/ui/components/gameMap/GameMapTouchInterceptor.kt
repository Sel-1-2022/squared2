package sel.group9.squared2.ui.components.gameMap

import android.util.Log
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitTouchSlopOrCancellation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.anyChangeConsumed
import androidx.compose.ui.input.pointer.pointerInput
import okhttp3.internal.notify
import sel.group9.squared2.viewmodel.SquaredGameScreenViewModel

@Composable
fun GameMapTouchInterceptor(model: SquaredGameScreenViewModel, content: @Composable () -> Unit) {
    Box (
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    awaitPointerEvent(PointerEventPass.Initial)
                    Log.d("compose_study", "first layer")
                }
            }
//                detectDragGestures(
//                    onDragStart = {
//                        if (model.followPlayer.value) {
//                            model.setFollowPlayer(false)
//                        }
//                    },
//                    onDragEnd = { },
//                    onDrag = { _, _ -> },
//                    onDragCancel = { }
//                )

//                detectDragGestures {  change, dragAmount ->
//                    if (model.followPlayer.value) {
//                        model.setFollowPlayer(false)
//                    }
//                }

//                awaitPointerEventScope {
//                    val down = awaitFirstDown()
//
//                    awaitTouchSlopOrCancellation(down.id) { _, _ ->
//                        Log.v("TouchIntercept", "Intercept or something")
//                        if (model.followPlayer.value) {
//                            model.setFollowPlayer(false)
//                        }
//                    }
//                }
//            }
    ) {
        content()
    }
}