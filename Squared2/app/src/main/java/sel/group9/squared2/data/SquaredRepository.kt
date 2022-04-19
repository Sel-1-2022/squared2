package sel.group9.squared2.data

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SquaredRepository@Inject constructor(val backend: Backend, val settings: Settings) {
    fun getColor():StateFlow<Color>{
        return settings.getColor()
    }
    fun setColor(new:Color){
        settings.setColor(new)
    }
    fun getSound():Float{
        return settings.getSound()
    }
    fun getMusic():Float{
        return settings.getMusic()
    }
    fun setSound(new:Float){
        settings.setSound(new)
    }
    fun setMusic(new:Float){
        settings.setMusic(new)
    }
}
