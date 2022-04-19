package sel.group9.squared2.ui.components.leaderBoard

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sel.group9.squared2.ui.components.SquaredTextButton
import sel.group9.squared2.ui.theme.SquaredTheme

enum class LeaderBoardRegion {
    Global,
    Country,
    Local
}

@Composable
fun LeaderBoardMultiToggleSelection() {
    var selected = LeaderBoardRegion.Global

    Row(
        Modifier.fillMaxWidth()
    ) {
        SquaredTextButton(
            text = "global",
            onClick = { /*TODO*/ },
            selected = selected == LeaderBoardRegion.Global
        )

        Spacer(Modifier.weight(1.0f))

        SquaredTextButton(
            text = "country",
            onClick = { /*TODO*/ },
            selected = selected == LeaderBoardRegion.Country
        )

        Spacer(Modifier.width(20.dp))

        SquaredTextButton(
            text = "local",
            onClick = { /*TODO*/ },
            selected = selected == LeaderBoardRegion.Local
        )
    }
}

@Composable
@Preview
private fun LeaderBoardMultiToggleSelectionPreview() {
    SquaredTheme {
        LeaderBoardMultiToggleSelection()
    }
}