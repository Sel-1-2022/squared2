package sel.group9.squared2.data

import androidx.compose.ui.graphics.Color
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SquaredRepository@Inject constructor(val backend: Backend, val settings: Settings) {
    fun getColor():Color{
        return backend.color()
    }
    fun setColor(new:Color){
        backend.changeColor(new)
    }
    fun getSound():Float{
        return settings.sound
    }
    fun getMusic():Float{
        return settings.music
    }
    fun setSound(new:Float){
        settings.sound=new
    }
    fun setMusic(new:Float){
        settings.music=new
    }
}
