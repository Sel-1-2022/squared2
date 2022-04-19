package sel.group9.squared2.ui.components.leaderBoard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import sel.group9.squared2.ui.theme.SquaredTheme

class LeaderBoardItem(val ranking: Int, val username: String, val score: Int) {}

@Composable
fun LeaderBoardList(leaderboard: List<LeaderBoardItem>) {
    LazyColumn {
        items(leaderboard) {
            item -> LeaderBoardListItem(
                ranking = item.ranking,
                name = item.username,
                score = item.score
            )
        }
    }
}

@Composable
@Preview
private fun LeaderBoardListPreview() {
    SquaredTheme {
        LeaderBoardList(listOf(
            LeaderBoardItem(1, "Hoed", 29),
            LeaderBoardItem(2, "Paard", 20)
        ))
    }
}