package sel.group9.squared2.ui.components.leaderBoard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sel.group9.squared2.ui.theme.SquaredTheme

@Composable
fun LeaderBoard(
    modifier: Modifier = Modifier
) {
    val leaderBoard = listOf(
        LeaderBoardItem(1, "Hoed", 29),
        LeaderBoardItem(2, "Paard", 20)
    )
    Box(modifier) {
        Column {
            LeaderBoardMultiToggleSelection()

            Spacer(Modifier.height(25.dp))

            LeaderBoardList(leaderboard = leaderBoard)
        }
    }
}


@Composable
@Preview
private fun LeaderBoardPreview() {
    SquaredTheme {
        LeaderBoard()
    }
}