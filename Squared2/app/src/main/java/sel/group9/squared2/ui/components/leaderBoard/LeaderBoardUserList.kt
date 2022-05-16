package sel.group9.squared2.ui.components.leaderBoard

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import sel.group9.squared2.data.User
import sel.group9.squared2.ui.theme.SquaredTheme

@Composable
fun LeaderBoardUserList(leaderboard: List<User>) {
    LazyColumn {
        itemsIndexed(leaderboard) {
            index, user -> LeaderBoardUserListItem(
                ranking = index.toLong(),
                name = user.nickname,
                score = user.squaresCaptured
            )
        }
    }
}

@Composable
@Preview
private fun LeaderBoardUserListPreview() {
    SquaredTheme {
//        LeaderBoardUserList(listOf(
//            LeaderBoardItem(1, "Hoed", 29),
//            LeaderBoardItem(2, "Paard", 20)
//        ))
    }
}