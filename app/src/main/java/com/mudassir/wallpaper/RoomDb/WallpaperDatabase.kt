package com.mudassir.wallpaper.RoomDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WallpaperEntity::class], version = 1, exportSchema = false)
abstract class WallpaperDatabase : RoomDatabase(){

    abstract fun getWallpapersDao(): WallpaperDao

    companion object{
        private var INSTANCE: WallpaperDatabase?= null

        fun getDatabase(context: Context) : WallpaperDatabase{
            if (INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context, WallpaperDatabase::class.java, "Wallpaper_database")
                        .allowMainThreadQueries().build()
                }
            }
            return INSTANCE!!
        }
    }


}