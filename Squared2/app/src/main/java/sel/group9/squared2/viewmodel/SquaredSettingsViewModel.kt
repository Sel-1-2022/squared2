package sel.group9.squared2.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import sel.group9.squared2.data.SquaredRepository
import javax.inject.Inject

@HiltViewModel
class SquaredSettingsViewModel@Inject constructor(private val repository: SquaredRepository): ViewModel() {


    fun beepSound(audio:Float){
        repository.playSlider(audio)
    }
    private val _sound = MutableStateFlow(repository.getSound())
    var sound: StateFlow<Float> = _sound

    private val _music = MutableStateFlow(repository.getMusic())
    val music: StateFlow<Float> = _music

    fun setMusic(new:Float){
        _music.value=new
    }

    fun setSound(new:Float){
        _sound.value=new
    }

    fun confirmChanges(){
        repository.setSound(sound.value)
        repository.setMusic(music.value)
    }

}