package com.mudassir.wallpaper.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mudassir.wallpaper.Interfaces.WallpaperOnClickListener
import com.mudassir.wallpaper.Models.AllWallpaperThumbnail.Dir
import com.mudassir.wallpaper.databinding.WallpaperViewBinding
import java.util.ArrayList

class AllWallpaperAdapter(val context: Context, val thumbnailList: ArrayList<Dir>, val onClick: WallpaperOnClickListener) : RecyclerView.Adapter<AllWallpaperAdapter.AllWallpaperViewHolder>() {

    class AllWallpaperViewHolder(val binding : WallpaperViewBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllWallpaperViewHolder {
        val binding = WallpaperViewBinding.inflate(LayoutInflater.from(context), parent, false)
        return AllWallpaperViewHolder(binding)
    }

    override fun getItemCount(): Int {
    return thumbnailList.size
    }

    override fun onBindViewHolder(holder: AllWallpaperViewHolder, position: Int) {

        val myThumbnailList = thumbnailList[position]

        Glide.with(context).load("http://199.231.185.126/project%231_wallpapers/Thumbnail/"+ myThumbnailList.file)
            .centerCrop().into(holder.binding.wallpaperImageView)

        holder.itemView.setOnClickListener {
            onClick.onWallpaperClick("http://199.231.185.126/project%231_wallpapers/Thumbnail/"+ myThumbnailList.file)
        }
    }


}