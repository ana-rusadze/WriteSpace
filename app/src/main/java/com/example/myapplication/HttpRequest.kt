package com.example.myapplication

import android.util.Log.d
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

object HttpRequest {

    const val REQUEST = "prompt"
    private var retrofit = Retrofit.Builder()
        .baseUrl("https://ineedaprompt.com/dictionary/default/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()


    private var service = retrofit.create(ApiService::class.java)

    interface ApiService {
        @GET("{path}")
        fun getRequest(@Path("path") path: String): Call<String>
    }

    fun getRequest(path: String, callback: CustomCallback){
        val call = service.getRequest(path)
        call.enqueue(onCallback(callback))
    }

    private fun onCallback(callback: CustomCallback) = object : Callback<String>{
        override fun onFailure(call: Call<String>, t: Throwable) {
            d("onFailure", "${t.message}")
            callback.onFailure(t.message.toString())
        }
        override fun onResponse(call: Call<String>, response: Response<String>) {
//            for(i in 1..7) {
                callback.onResponse(response.body().toString())
//            }


        }

    }
}