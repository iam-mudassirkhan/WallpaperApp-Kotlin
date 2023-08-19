package com.mudassir.wallpaper.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mudassir.wallpaper.Interfaces.WallpaperOnClickListener
import com.mudassir.wallpaper.Models.Dir
import com.mudassir.wallpaper.databinding.WallpaperViewBinding

class BikesWallpaperAdapter(val context: Context, val wallpapersList: List<Dir>, val onWallpaperClick : WallpaperOnClickListener)
    : RecyclerView.Adapter<BikesWallpaperAdapter.WallpapersViewHolder> () {

    class WallpapersViewHolder(val binding: WallpaperViewBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpapersViewHolder {
        val binding = WallpaperViewBinding.inflate(LayoutInflater.from(context), parent, false)
        return (WallpapersViewHolder(binding))
    }

    override fun getItemCount(): Int {
        return wallpapersList.size
    }

    override fun onBindViewHolder(holder: WallpapersViewHolder, position: Int) {

        val myWallpapersList = wallpapersList[position]

        Glide.with(context)
            .load("http://199.231.185.126/project%231_wallpapers/Bikes/" + myWallpapersList.file)
            /* .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(false)*/
            .centerCrop()
            .into(holder.binding.wallpaperImageView)

        holder.itemView.setOnClickListener {
            onWallpaperClick.onWallpaperClick("http://199.231.185.126/project%231_wallpapers/Bikes/" + myWallpapersList.file)
        }


    }
}