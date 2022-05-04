package sel.group9.squared2.data

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.parse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.internal.wait
import java.util.ArrayList

//classes that add abstraction
data class UserLocation(val lat:Double, val lon: Double)

data class UserInfo(val name: String,val loc: UserLocation, val color: Int)


//classes to parse json to
data class PointSchema(val _id: String,val type:String,val coordinates:List<Double>)

data class User(val _id:String,val nickname:String,val color:Int,val location:PointSchema,val lastLocationUpdate : Long)

data class Square(val color:Int,val lon: Double,val lat: Double) {
    companion object {
        val size = 0.0001
    }
}

class Backend(test:Boolean=false) {

    //val url = "http://10.0.2.2:3000/api/"
    var url = "https://squared2.xyz/api/"
    init{
        if (test)
            url = "http://10.0.2.2:3000/api/"
    }
    suspend fun getUser(id:String):User{
        return withContext(
            Dispatchers.IO) {
            delay(5000)
            val url = (url+"user").toHttpUrl().newBuilder().addQueryParameter("id",id).build()
            val req = Request.Builder().url(url).get().build()
            val resp = OkHttpClient.Builder().build().newCall(req).execute()
            Gson().fromJson(resp.body?.string(),User::class.java)
        }
    }

    suspend fun postUser(info:UserInfo):String{
        return withContext(
            Dispatchers.IO) {
            val url = (url+"user").toHttpUrl().newBuilder().addQueryParameter("nickname",info.name).
                    addQueryParameter("color",info.color.toString()).addQueryParameter("longitude",info.loc.lon.toString())
                    .addQueryParameter("latitude",info.loc.lat.toString()).build()
            val req = Request.Builder().url(url).post("".toRequestBody()).build()
            val resp = OkHttpClient.Builder().build().newCall(req).execute()
            val text = resp.body?.string().toString()
            Log.v("test",url.toString())
            val id = Gson().fromJson(text,String::class.java)
            id
        }

    }

    suspend fun patchUser(id:String,info:UserInfo):User{
        return withContext(
            Dispatchers.IO) {
            val test = (url + "user").toHttpUrl().newBuilder().addQueryParameter("id", id).build()
            val nullTest =
                OkHttpClient.Builder().build().newCall(Request.Builder().url(test).get().build())
                    .execute()
            if (nullTest.body!!.string() == "null") {
                getUser(postUser(info))
            } else {
                val builder = (url + "user").toHttpUrl().newBuilder().addQueryParameter("id", id)
                    .addQueryParameter("nickname", info.name)
                    .addQueryParameter("color", info.color.toString())
                    .addQueryParameter("longitude", info.loc.lon.toString())
                    .addQueryParameter("latitude", info.loc.lat.toString())
                val url = builder.build()
                val req = Request.Builder().url(url).patch("".toRequestBody()).build()
                val resp = OkHttpClient.Builder().build().newCall(req).execute()
                Gson().fromJson(resp.body?.string().toString(), User::class.java)
            }
        }
    }
    suspend fun nearbyUsers(loc:UserLocation,dist:Double):List<User>{
        return withContext(Dispatchers.IO){
            val url = (url+"nearbyusers").toHttpUrl().newBuilder().addQueryParameter("latitude",loc.lat.toString())
                .addQueryParameter("longitude",loc.lon.toString()).addQueryParameter("distance",dist.toString()).build()
            val req = Request.Builder().url(url).get().build()
            val resp = OkHttpClient.Builder().build().newCall(req).execute().body?.string()
            Gson().fromJson(resp, object : TypeToken<List<User>>() {}.type)
        }
    }

    suspend fun addTile(id:String,loc:UserLocation,color:Int) {
        withContext(Dispatchers.IO) {
            val url = (url + "placesquare").toHttpUrl().newBuilder()
                .addQueryParameter("latitude", loc.lat.toString())
                .addQueryParameter("longitude", loc.lon.toString())
                .addQueryParameter("id", id)
                .addQueryParameter("color", color.toString()).build()
            Log.v("test",url.toString())
            val req = Request.Builder().url(url).post("".toRequestBody()).build()
            Log.v("test",OkHttpClient.Builder().build().newCall(req).execute().body?.string().toString())
        }
    }

    suspend fun nearbyTiles(loc:UserLocation,dist:Double):List<Square>{
        return withContext(Dispatchers.IO){
            val url = (url+"nearbysquares").toHttpUrl().newBuilder().addQueryParameter("latitude",loc.lat.toString())
                .addQueryParameter("longitude",loc.lon.toString()).addQueryParameter("distance",dist.toString()).build()
            val req = Request.Builder().url(url).get().build()
            val resp = OkHttpClient.Builder().build().newCall(req).execute().body?.string()
            Gson().fromJson(resp, object : TypeToken<List<Square>>() {}.type)
        }
    }
}


