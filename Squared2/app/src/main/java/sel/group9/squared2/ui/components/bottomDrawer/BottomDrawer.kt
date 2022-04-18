package sel.group9.squared2.ui.components.bottomDrawer

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
import sel.group9.squared2.ui.components.gameMap.GameMap
import sel.group9.squared2.ui.components.leaderBoard.LeaderBoard
import sel.group9.squared2.ui.theme.SquaredTheme
import sel.group9.squared2.ui.theme.borderModifier
import sel.group9.squared2.ui.theme.borderWidth

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SquaredBottomDrawer(content: @Composable () -> Unit) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingMapButtons()
        },
        floatingActionButtonPosition = FabPosition.End,
        sheetContent = {
            Column (Modifier.padding(horizontal = 25.dp, vertical = 0.dp)) {
                // The bottom drawer closed view
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    BottomDrawerDock()
                }

                Divider(modifier = Modifier.clip(MaterialTheme.shapes.small), color = Color.Black, thickness = borderWidth)

                // The bottom drawer open view
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
        sheetPeekHeight = 100.dp
    ) {
        _ -> content()
    }
}

@Composable
@Preview
private fun BottomDrawerPreview() {
    SquaredTheme {
        SquaredBottomDrawer {
            GameMap()
        }
    }
}

