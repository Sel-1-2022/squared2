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


data class PointSchema(val _id: String,val type:String,val coordinates:List<Double>)

data class User(val _id:String,val nickname:String,val color:Int,val location:PointSchema,val lastLocationUpdate : Long)

data class Square(val color:Int,val lon: Double,val lat: Double) {
    companion object {
        val size = 0.0001
    }
}

class Backend {

    //val url = "http://10.0.2.2:3000/api/"
    val url = "http://squared2.xyz/api/"
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

    suspend fun postUser(name:String,color:Int,lat:Double,long:Double):String{
        return withContext(
            Dispatchers.IO) {
            val url = (url+"user").toHttpUrl().newBuilder().addQueryParameter("nickname",name).
                    addQueryParameter("color",color.toString()).addQueryParameter("longitude",long.toString())
                    .addQueryParameter("latitude",lat.toString()).build()
            val req = Request.Builder().url(url).post("".toRequestBody()).build()
            val resp = OkHttpClient.Builder().build().newCall(req).execute()
            Gson().fromJson(resp.body?.string().toString(),String::class.java)
        }

    }
    suspend fun patchUser(id:String,name:String?,color:Int?,lat:Double?,long:Double?,last:Int?):User{
        return withContext(
            Dispatchers.IO) {
            val builder = (url+"user").toHttpUrl().newBuilder().addQueryParameter("id",id)
            if(name!=null)
                builder.addQueryParameter("nickname",name)
            if(color!=null)
                builder.addQueryParameter("color",color.toString())
            if(last!=null)
                builder.addQueryParameter("lastsquare ",last.toString())
            if(lat!=null && long != null)
                builder.addQueryParameter("longitude",long.toString()).addQueryParameter("latitude",lat.toString())
            val url = builder.build()
            Log.v("test",builder.toString())
            val req = Request.Builder().url(url).patch("".toRequestBody()).build()
            val resp = OkHttpClient.Builder().build().newCall(req).execute()
            Gson().fromJson(resp.body?.string().toString(),User::class.java)
        }
    }
    suspend fun nearbyUsers(lat:Double,long:Double,dist:Double):List<User>{
        return withContext(Dispatchers.IO){
            val url = (url+"nearbyusers").toHttpUrl().newBuilder().addQueryParameter("latitude",lat.toString())
                .addQueryParameter("longitude",long.toString()).addQueryParameter("distance",dist.toString()).build()
            val req = Request.Builder().url(url).get().build()
            val resp = OkHttpClient.Builder().build().newCall(req).execute().body?.string()
            Gson().fromJson(resp, object : TypeToken<List<User>>() {}.type)
        }
    }

    suspend fun addTile(id:String,lat:Double,long:Double,color:Int) {
        withContext(Dispatchers.IO) {
            val url = (url + "placesquare").toHttpUrl().newBuilder()
                .addQueryParameter("latitude", lat.toString())
                .addQueryParameter("longitude", long.toString())
                .addQueryParameter("id", id)
                .addQueryParameter("color", color.toString()).build()
            val req = Request.Builder().url(url).post("".toRequestBody()).build()
            OkHttpClient.Builder().build().newCall(req).execute()
        }
    }

    suspend fun nearbyTiles(lat:Double,long:Double,dist:Double):List<Square>{
        return withContext(Dispatchers.IO){
            val url = (url+"nearbysquares").toHttpUrl().newBuilder().addQueryParameter("latitude",lat.toString())
                .addQueryParameter("longitude",long.toString()).addQueryParameter("distance",dist.toString()).build()
            val req = Request.Builder().url(url).get().build()
            val resp = OkHttpClient.Builder().build().newCall(req).execute().body?.string()
            Gson().fromJson(resp, object : TypeToken<List<Square>>() {}.type)
        }
    }
}


