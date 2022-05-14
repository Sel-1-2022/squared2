package sel.group9.squared2.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import sel.group9.squared2.data.SquaredRepository
import javax.inject.Inject

@HiltViewModel
class SquaredMediaViewModel @Inject constructor(private val repository: SquaredRepository): ViewModel() {
    fun playButton(){
        repository.playButton()
    }
}