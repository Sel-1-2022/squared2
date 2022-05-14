package sel.group9.squared2.data

import android.app.Activity
import android.app.Application
import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.*
import sel.group9.squared2.R

class SoundManager(private val context: Context): DefaultLifecycleObserver {

    companion object{

        var addObserver : ((LifecycleObserver)->Unit) ? = null

        fun setup(act:ComponentActivity){
            addObserver = {x->act.lifecycle.addObserver(x)}
        }
    }

    init{
        if(addObserver!=null)
            addObserver!!(this)
    }

    fun playButton(){
        button?.start()
    }

    private  var button : MediaPlayer? = null
    private val slider = MediaPlayer.create(context, R.raw.sliderbeep)
    private  var background : MediaPlayer? = null

    fun loadMusic(music:Float,effect:Float){
        background = MediaPlayer.create(context, R.raw.backgroundsound)
        background?.isLooping=true;
        background?.setVolume(music,music)
        background?.start()

        button = MediaPlayer.create(context, R.raw.buttonsound)
        button?.setVolume(effect,effect)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onCreate(owner)
        background?.pause()
    }


    override fun onResume(owner: LifecycleOwner) {
        super.onDestroy(owner)
        background?.start()
    }

    fun playSlider(audio:Float){
        slider.setVolume(audio,audio)
        slider.start()
    }

    fun changeMusic(volume:Float){
        background?.setVolume(volume,volume)
    }

    fun changeEffect(volume:Float){
        button?.setVolume(volume,volume)
    }

}