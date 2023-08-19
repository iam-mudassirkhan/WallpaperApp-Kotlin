package com.mudassir.wallpaper.RoomDb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WallpaperDao {

    @Insert
    suspend fun insertWallpaper(wallpaperEntity: WallpaperEntity)

    @Delete
     fun deleteWallpaper(wallpaperEntity: WallpaperEntity)


    @Query("SELECT * FROM wallpapers WHERE wallpaperUrl = :url")
     fun getWallpaperByUrl(url: String): WallpaperEntity?


    @Query("UPDATE wallpapers SET isFavorite = :isFavorite WHERE wallpaperUrl = :url")
    suspend fun updateFavoriteStatus(url: String, isFavorite: Boolean)

     @Query("Select * from wallpapers")
     fun getAllFavouriteWallpaper(): LiveData<List<WallpaperEntity>>


}