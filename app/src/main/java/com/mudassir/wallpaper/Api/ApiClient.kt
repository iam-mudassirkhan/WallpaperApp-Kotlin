package com.mudassir.wallpaper.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {


    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://199.231.185.126/project%231_wallpapers/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    }

   /* private var retrofit : Retrofit? = null

    fun getClient(): Api_Interface? {
        if (retrofit == null)
        {
            retrofit = Retrofit.Builder().baseUrl("http://199.231.185.126/project%231_wallpapers/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit?.create(Api_Interface::class.java)
    }*/


}