package com.mudassir.wallpaper.Interfaces

import com.mudassir.wallpaper.RoomDb.WallpaperEntity

interface passWallpaperEntityModel {
    fun onFavWallpaperClick(wallpaperEntity: WallpaperEntity)
}