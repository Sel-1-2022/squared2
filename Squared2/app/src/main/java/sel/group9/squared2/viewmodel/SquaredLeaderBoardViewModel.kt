package sel.group9.squared2.viewmodel

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import sel.group9.squared2.data.ColorScore
import sel.group9.squared2.data.SquaredRepository
import sel.group9.squared2.data.User
import sel.group9.squared2.model.leaderboard.LeaderBoardSelection
import javax.inject.Inject

@HiltViewModel
class  SquaredLeaderBoardViewModel @Inject constructor(private val backend: SquaredRepository) : ViewModel() {
    private var amountOfTopUsersToFetch = 50

    private val _topUsers: MutableStateFlow<List<User>> = MutableStateFlow(listOf())
    var topUsers: StateFlow<List<User>> = _topUsers

    private val _self : MutableStateFlow<User?> = MutableStateFlow(null)
    var self : StateFlow<User?> = _self

    private val _colorScores: MutableStateFlow<List<ColorScore>> = MutableStateFlow(listOf())
    var colorScores: StateFlow<List<ColorScore>> = _colorScores

    private val _leaderBoardSelection: MutableStateFlow<LeaderBoardSelection> = MutableStateFlow(LeaderBoardSelection.GLOBAL)
    var leaderBoardSelection: StateFlow<LeaderBoardSelection> = _leaderBoardSelection

    val scrollState = ScrollState(0)


    init {
        initialiseNetworkRequests();
    }

    private fun initialiseNetworkRequests() {
        viewModelScope.launch {
            while (true) {
                updateSelf()
                updateTopUsers()
                updateColorScores()
                delay(5000)
            }
        }
    }

    private suspend fun updateSelf(){
        val user = backend.getUser()
        if(user!=null){
            _self.value=user
        }
    }

    private suspend fun updateTopUsers() {
        val topUsers = backend.getTopUsers(amountOfTopUsersToFetch)

        if (topUsers != null) {
            _topUsers.value = topUsers
        }
    }

    private suspend fun updateColorScores() {
        val colorScores = backend.getColorScores()
        Log.v("Squared2", "colorScores = $colorScores")

        if (colorScores != null) {
            _colorScores.value = colorScores.sortedByDescending { it.squaresCaptured }
        }
    }

    fun setLeaderBoardSelection(selection: LeaderBoardSelection) {
        _leaderBoardSelection.value = selection
    }
//    private suspend fun loadMoreUsers() {
//
//    }
}