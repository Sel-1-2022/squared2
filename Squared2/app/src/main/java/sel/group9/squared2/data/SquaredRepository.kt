package sel.group9.squared2.data

import android.location.Location
import sel.group9.squared2.data.UserLocation
import android.util.Log
import androidx.compose.ui.graphics.Color
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SquaredRepository@Inject constructor(private val sound: SoundManager,private val backend: Backend, private val settings: Settings) {

    init{
        sound.loadMusic(settings.getMusic(),settings.getSound())
    }


    fun playSlider(audio:Float){
        sound.playSlider(audio)
    }

    suspend fun postUser(loc:UserLocation):String{
        return backend.postUser(UserInfo(settings.getName(),loc,settings.getColorIndex()))
    }

    suspend fun getUser(): User{
        return backend.getUser(settings.getId()!!)
    }


    suspend fun patchUser(loc:UserLocation):User{
        val user = backend.patchUser(settings.getId()!!,UserInfo(settings.getName(),loc,settings.getColorIndex()))
        settings.setId(user._id)
        return user
    }

    suspend fun nearbyUser(loc:UserLocation,dist:Double):List<User>{
        return backend.nearbyUsers(loc,dist)
    }
    suspend fun placeTile(loc:UserLocation){
        backend.addTile(settings.getId()!!,loc,settings.getColorIndex())
    }

    suspend fun nearbyTiles(loc:UserLocation,dist:Double):List<Square>{
        return backend.nearbyTiles(loc,dist)
    }

    fun getLocation(): Task<Location>{
        return settings.getLocation()!!
    }
    fun getLocationFlow(millis:Long): Flow<Task<Location>>{
        return settings.getLocationFlow(millis)
    }

    fun getColor():StateFlow<Int>{
        return settings.playerColor
    }
    fun setColor(new:Int){
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
        sound.changeEffect(new)
    }
    fun setMusic(new:Float){
        settings.setMusic(new)
        sound.changeMusic(new)
    }
    fun getId():String?{
        return settings.getId()
    }
    fun setId(id:String){
        settings.setId(id)
    }
    fun getName():String{
        return settings.getName()
    }
    fun setName(name:String){
        settings.setName(name)
    }

}
