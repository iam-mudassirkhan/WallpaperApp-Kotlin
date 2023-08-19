package com.mudassir.wallpaper.Interfaces

import com.mudassir.wallpaper.Models.AnimalsWallpaperModel.Dir

interface WallpaperOnClickListener {

    fun onWallpaperClick(imageUrl: String)
    fun onWallpaperLongClick(imageUrl: ArrayList<Dir>)
}