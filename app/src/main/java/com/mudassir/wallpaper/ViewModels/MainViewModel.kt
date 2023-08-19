package com.mudassir.wallpaper.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mudassir.wallpaper.Models.AnimalsWallpaperModel.GetAnimalsWallpaper
import com.mudassir.wallpaper.Repository.Repository

class MainViewModel(private val repository: Repository): ViewModel() {

//    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.getAnimalsWallpaperList()
//        }

//        val animalWallpaperList: LiveData<GetAnimalsWallpaper>
//            get() = repository.animalsWallpaper

        fun animals (): LiveData<GetAnimalsWallpaper>  {
            return repository.animalsWallpaper
        }

    }
