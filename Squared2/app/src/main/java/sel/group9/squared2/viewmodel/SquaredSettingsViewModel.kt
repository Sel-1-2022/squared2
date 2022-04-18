package sel.group9.squared2.viewmodel

import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import sel.group9.squared2.MainActivity
import sel.group9.squared2.R
import sel.group9.squared2.data.SquaredRepository
import javax.inject.Inject

@HiltViewModel
class SquaredSettingsViewModel@Inject constructor(val repository: SquaredRepository): ViewModel() {

    var player : MediaPlayer?= null

    fun startPlayer(act : MainActivity){
        if(player==null)
            player=MediaPlayer.create(act,R.raw.sliderbeep)
    }

    fun getBack(onBack:()->Unit) : ()->Unit{
        return {
            player?.stop()
            player?.release()
            onBack()
        }
    }

    fun beepSound(audio:Float){
        if(player?.isPlaying == true) {
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