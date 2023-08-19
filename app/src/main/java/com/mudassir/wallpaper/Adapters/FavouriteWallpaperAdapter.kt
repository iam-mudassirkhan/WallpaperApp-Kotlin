package com.mudassir.wallpaper.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mudassir.wallpaper.Interfaces.WallpaperOnClickListener
import com.mudassir.wallpaper.RoomDb.WallpaperEntity
import com.mudassir.wallpaper.databinding.WallpaperViewBinding

class FavouriteWallpaperAdapter(val context: Context, val favWallpaperList: List<WallpaperEntity>, val onClick: WallpaperOnClickListener): RecyclerView.Adapter<FavouriteWallpaperAdapter.FavWallpaperViewHolder>() {

    inner class FavWallpaperViewHolder(val binding: WallpaperViewBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavWallpaperViewHolder {
        val binding = WallpaperViewBinding.inflate(LayoutInflater.from(context), parent, false)
        return FavWallpaperViewHolder(binding)
    }

    override fun getItemCount(): Int {
      return  favWallpaperList.size
    }

    override fun onBindViewHolder(holder: FavWallpaperViewHolder, position: Int) {
       val myFavWallpapers = favWallpaperList[position]

        Glide.with(context).load(myFavWallpapers.wallpaperUrl)
            .centerCrop()
            .into(holder.binding.wallpaperImageView)

        holder.itemView.setOnClickListener {
            onClick.onWallpaperClick(myFavWallpapers.wallpaperUrl)
        }

    }

}