package com.mudassir.wallpaper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mudassir.wallpaper.Adapters.ViewPagerAdapter
import com.mudassir.wallpaper.Fragments.AllWallpaperFragment
import com.mudassir.wallpaper.Fragments.AnimalsWallpaperFragment
import com.mudassir.wallpaper.Fragments.BookMarkedFragment
import com.mudassir.wallpaper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)




    }
}