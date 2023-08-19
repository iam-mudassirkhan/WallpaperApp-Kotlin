package com.mudassir.wallpaper.Api

import com.mudassir.wallpaper.Models.AllWallpaperThumbnail.GetAllWallpaperThumbnails
import com.mudassir.wallpaper.Models.AnimalsWallpaperModel.GetAnimalsWallpaper
import com.mudassir.wallpaper.Models.getWallpapers
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface Api_Interface {

    @GET("Animals")
    fun getAnimalsWallpaper(): Call<GetAnimalsWallpaper>

    @GET("Animals")
    fun getAnimalsWallpaperWithMvvm(): Response<GetAnimalsWallpaper>


    @GET("Thumbnail")
    fun getAllWallpaperThumbnail(): Call<GetAllWallpaperThumbnails>

    @GET("City")
    fun getWallpapers(): Call<getWallpapers>

    @GET("Cars")
    fun getCarsWallpapers(): Call<getWallpapers>

    @GET("Bikes")
    fun getBikesWallpapers(): Call<getWallpapers>

    @GET("Art")
    fun getArtWallpapers(): Call<getWallpapers>

    @GET("Fashion%20(America)")
 fun getFashionAmericaWallpaper(): Call<getWallpapers>
}