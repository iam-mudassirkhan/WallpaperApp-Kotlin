package com.mudassir.wallpaper.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mudassir.wallpaper.Api.Api_Interface
import com.mudassir.wallpaper.Models.AnimalsWallpaperModel.GetAnimalsWallpaper


class Repository(val context: Context, private val apiInterface: Api_Interface) {

    private val animalWallpaperLiveData = MutableLiveData<GetAnimalsWallpaper>()


    val animalsWallpaper : LiveData<GetAnimalsWallpaper>
        get() = animalWallpaperLiveData

 /*   suspend fun getAnimalsWallpaperList(){
val result = apiInterface.getAnimalsWallpaperWithMvvm()
       if (result.body() != null){
           animalWallpaperLiveData.postValue(result.body())
       }
        else{
           Toast.makeText(context, "${result.errorBody()} Failed", Toast.LENGTH_SHORT).show()
        }
    }*/
}