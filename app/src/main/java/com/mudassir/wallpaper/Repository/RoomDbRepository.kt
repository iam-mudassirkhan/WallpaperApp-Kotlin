package com.mudassir.wallpaper.Repository

import androidx.lifecycle.LiveData
import com.mudassir.wallpaper.RoomDb.WallpaperDao
import com.mudassir.wallpaper.RoomDb.WallpaperEntity

class RoomDbRepository(val wallpaperDao: WallpaperDao) {


    suspend fun insertWallpaper(wallpaperEntity: WallpaperEntity){
        wallpaperDao.insertWallpaper(wallpaperEntity)
    }

    fun deleteWallpaper(wallpaperEntity: WallpaperEntity){
        wallpaperDao.deleteWallpaper(wallpaperEntity)
    }

    fun isWallpaperExists(url: String): Boolean {
        val wallpaperEntity = wallpaperDao.getWallpaperByUrl(url)
        return wallpaperEntity != null
    }

    suspend fun updateFavoriteStatus(url: String, isFavorite: Boolean) {
        wallpaperDao.updateFavoriteStatus(url, isFavorite)
    }

    fun getAllWallpapers() : LiveData<List<WallpaperEntity>>{
        return  wallpaperDao.getAllFavouriteWallpaper()
    }

}