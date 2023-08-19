package com.mudassir.wallpaper.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mudassir.wallpaper.Interfaces.WallpaperOnClickListener
import com.mudassir.wallpaper.Models.AnimalsWallpaperModel.Dir
import com.mudassir.wallpaper.R
import com.mudassir.wallpaper.databinding.WallpaperViewBinding


class AnimalWallpaperAdapter(val context: Context, var animalsWallpaperList: ArrayList<Dir>, val wallpaperClickListener: WallpaperOnClickListener ): RecyclerView.Adapter<AnimalWallpaperAdapter.AnimalsViewHolder>() {




    inner class AnimalsViewHolder(val binding: WallpaperViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalsViewHolder {
        val binding =
            WallpaperViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return AnimalsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return animalsWallpaperList.size
    }

    override fun onBindViewHolder(holder: AnimalsViewHolder, position: Int) {
        val getAnimalsList = animalsWallpaperList[position]

        Glide.with(context)
            .load("http://199.231.185.126/project%231_wallpapers/Animals/" + getAnimalsList.file)
            /* .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(false)*/
            .centerCrop()
            .into(holder.binding.wallpaperImageView)

        holder.itemView.setOnClickListener {
            wallpaperClickListener.onWallpaperClick("http://199.231.185.126/project%231_wallpapers/Animals/" + getAnimalsList.file)
        }
        holder.itemView.setOnLongClickListener {

            wallpaperClickListener.onWallpaperLongClick(animalsWallpaperList)
            true
        }


    }

    fun fragmentTransaction(fragment: Fragment) {

        (context as AppCompatActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .addToBackStack(null)
            .commit()
    }
}