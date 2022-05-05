package sel.group9.squared2.data

import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import sel.group9.squared2.R

class SoundManager(context: Context):LifecycleObserver {

    companion object{
        private var button : MediaPlayer? = null

        fun playButton(){
            button!!.start()
        }
    }
    init{
        button = MediaPlayer.create(context, R.raw.buttonsound)
    }

    private val slider = MediaPlayer.create(context, R.raw.sliderbeep)
    private val background = MediaPlayer.create(context, R.raw.backgroundsound)

    fun loadMusic(music:Float,effect:Float){
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        background.isLooping=true;
        background.setVolume(music,music)
        background.start()

        button!!.setVolume(effect,effect)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        background.pause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForGrounded(){
        background.start()
    }

    fun playSlider(audio:Float){
        slider.setVolume(audio,audio)
        slider.start()
    }

    fun changeMusic(volume:Float){
        background.setVolume(volume,volume)
    }

    fun changeEffect(volume:Float){
        slider.setVolume(volume,volume)
        button!!.setVolume(volume,volume)
    }

}