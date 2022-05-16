package sel.group9.squared2.ui.components.leaderBoard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sel.group9.squared2.model.leaderboard.LeaderBoardSelection
import sel.group9.squared2.ui.theme.SquaredTheme
import sel.group9.squared2.viewmodel.SquaredLeaderBoardViewModel

@Composable
fun LeaderBoard(model: SquaredLeaderBoardViewModel) {
    val leaderBoardSelection = model.leaderBoardSelection.collectAsState()
    val topUsers = model.topUsers.collectAsState()
    val colorScores = model.colorScores.collectAsState()
    val scrollState = rememberScrollState()

    Box {
        Column {
            LeaderBoardMultiToggleSelection(leaderBoardSelection, model::setLeaderBoardSelection)

            Spacer(Modifier.height(25.dp))

            if (leaderBoardSelection.value == LeaderBoardSelection.GLOBAL) {
                LeaderBoardUserList(users = topUsers.value, scrollState = scrollState)
            } else {
                LeaderBoardColorList(colorScores = colorScores.value)
            }
        }
    }
}


@Composable
@Preview
private fun LeaderBoardPreview() {
    SquaredTheme {
//        LeaderBoard()
    }
}