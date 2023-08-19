package com.mudassir.wallpaper.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mudassir.wallpaper.Repository.RoomDbRepository
import com.mudassir.wallpaper.RoomDb.WallpaperEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RoomDbViewModel(val roomDbRepository: RoomDbRepository) : ViewModel(){

    val getAllWallpaper : LiveData<List<WallpaperEntity>> = roomDbRepository.getAllWallpapers()

    fun insertWallpaper(wallpaperUrl: String){

        GlobalScope.launch {
            val insertWallpaper = WallpaperEntity(null,wallpaperUrl, true)
            roomDbRepository.insertWallpaper(insertWallpaper)
        }

    }


    suspend fun updateFavoriteStatus(url: String, isFavorite: Boolean) {
        roomDbRepository.updateFavoriteStatus(url, isFavorite)
    }

     fun checkIfWallpaperExists(url: String): Boolean {

        return roomDbRepository.isWallpaperExists(url)
    }

   fun removeWallpaperFromFavourite(wallpaperEntity: WallpaperEntity){
       GlobalScope.launch {
           roomDbRepository.deleteWallpaper(wallpaperEntity)
       }
   }

}