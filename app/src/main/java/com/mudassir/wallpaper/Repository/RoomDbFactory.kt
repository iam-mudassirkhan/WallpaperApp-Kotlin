package com.mudassir.wallpaper.Repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mudassir.wallpaper.ViewModels.RoomDbViewModel

class RoomDbFactory(private val roomDbRepository: RoomDbRepository):
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RoomDbViewModel(roomDbRepository) as T
    }


}