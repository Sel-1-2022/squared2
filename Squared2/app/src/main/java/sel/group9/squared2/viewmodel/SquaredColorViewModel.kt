package sel.group9.squared2.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import sel.group9.squared2.data.SquaredRepository
import javax.inject.Inject

@HiltViewModel
class SquaredColorViewModel@Inject constructor(private val backend: SquaredRepository) : ViewModel(){
    fun setColor(new: Color){
        backend.setColor(new)
    }
}

