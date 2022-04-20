package sel.group9.squared2.viewmodel

import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sel.group9.squared2.MainActivity
import sel.group9.squared2.R
import sel.group9.squared2.data.SquaredRepository
import sel.group9.squared2.data.User
import javax.inject.Inject

@HiltViewModel
class SquaredSettingsViewModel@Inject constructor(private val repository: SquaredRepository): ViewModel() {

    var player: MediaPlayer? = null

    fun startPlayer(act: MainActivity) {
        if (player == null)
            player = MediaPlayer.create(act, R.raw.sliderbeep)
    }

    fun getBack(onBack: () -> Unit): () -> Unit {
        return {
            player?.stop()
            player?.release()
            onBack()
        }
    }

    fun beepSound(audio:Float){
        if(player?.isPlaying == false) {
            player?.setVolume(audio, audio)
            player?.start()
        }
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