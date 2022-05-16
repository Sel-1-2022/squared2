package sel.group9.squared2.ui.components.leaderBoard

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.StateFlow
import sel.group9.squared2.model.leaderboard.LeaderBoardSelection
import sel.group9.squared2.sound.ButtonBasics
import sel.group9.squared2.ui.components.SquaredTextButton
import sel.group9.squared2.ui.theme.SquaredTheme

@Composable
fun LeaderBoardMultiToggleSelection(
    leaderBoardSelection: State<LeaderBoardSelection>,
    setLeaderBoardSelection: (LeaderBoardSelection) -> Unit
) {
    Row(
        Modifier.fillMaxWidth()
    ) {
        SquaredTextButton(
            text = "global",
            ButtonBasics(onClick = { setLeaderBoardSelection(LeaderBoardSelection.GLOBAL) }),
            selected = leaderBoardSelection.value == LeaderBoardSelection.GLOBAL
        )

        Spacer(Modifier.weight(1.0f))

        SquaredTextButton(
            text = "by color",
            ButtonBasics(onClick = { setLeaderBoardSelection(LeaderBoardSelection.COLOR) }),
            selected = leaderBoardSelection.value == LeaderBoardSelection.COLOR
        )
    }
}

@Composable
@Preview
private fun LeaderBoardMultiToggleSelectionPreview() {
    SquaredTheme {
//        LeaderBoardMultiToggleSelection()
    }
}