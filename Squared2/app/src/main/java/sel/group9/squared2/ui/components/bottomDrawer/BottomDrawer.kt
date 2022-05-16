package sel.group9.squared2.ui.components.bottomDrawer

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import sel.group9.squared2.ui.components.bottomDrawer.floatingMapButtons.FloatingMapButtons
import sel.group9.squared2.ui.components.leaderBoard.LeaderBoard
import sel.group9.squared2.ui.theme.SquaredTheme
import sel.group9.squared2.ui.theme.borderWidth
import sel.group9.squared2.ui.theme.iconSize
import sel.group9.squared2.viewmodel.SquaredGameScreenViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SquaredBottomDrawer(model: SquaredGameScreenViewModel, onSettings:()->Unit, content: @Composable () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()

    fun toggleGrid() {
        model.toggleShowGrid()
    }

    fun onToggle() {
        coroutineScope.launch {
            if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                bottomSheetScaffoldState.bottomSheetState.collapse()
            } else {
                bottomSheetScaffoldState.bottomSheetState.expand()
            }
        }
    }

    val buttonHandlers = BottomDrawerButtonHandlers(
        onSettings = onSettings,
        toggleGrid = { toggleGrid() },
        onExpand = { onToggle() },
    )

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        floatingActionButton = {
            FloatingMapButtons(
                onCenter = { model.setFollowPlayer(true) },
                resetOrientation = { model.resetOrientation() }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        sheetContent = {
            Column (Modifier.padding(horizontal = 25.dp, vertical = 0.dp)) {
                // The bottom drawer closed view
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(iconSize * 2),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    BottomDrawerDock(
                        buttonHandlers = buttonHandlers,
                        isExpanded = bottomSheetScaffoldState.bottomSheetState.isExpanded
                    )
                }

                // The bottom drawer open view
                Divider(modifier = Modifier.clip(MaterialTheme.shapes.small), color = Color.Black, thickness = borderWidth)

                Column(
                    Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LeaderBoard(
                        modifier = Modifier.padding(vertical = 25.dp)
                    )
                }
            }

        },
        sheetPeekHeight = iconSize * 2
    ) {
        _ -> content()
    }
}

@Composable
@Preview
private fun BottomDrawerPreview() {
    SquaredTheme {
//        SquaredBottomDrawer({}) {
//            GameMap()
//        }
    }
}

