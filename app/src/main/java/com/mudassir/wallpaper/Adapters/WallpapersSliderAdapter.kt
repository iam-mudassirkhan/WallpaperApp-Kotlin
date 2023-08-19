//package com.mudassir.wallpaper.Adapters
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import android.view.animation.AccelerateDecelerateInterpolator
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.mudassir.wallpaper.Models.AnimalsWallpaperModel.Dir
//import com.mudassir.wallpaper.databinding.WallpaperSliderEachItemBinding
//
//class WallpapersSliderAdapter(private val context: Context, private val list: ArrayList<Dir>) :
//    RecyclerView.Adapter<WallpapersSliderAdapter.Vh>() {
//
//    inner class Vh(private val binding: WallpaperSliderEachItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun onBind(wallpaper: Dir) {
//            binding.apply {
//                Glide.with(context)
//                    .load("http://199.231.185.126/project%231_wallpapers/Animals/" + wallpaper.file)
//                    /* .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .skipMemoryCache(false)*/
//                    .centerCrop()
//                    .into(wallpaperImageView)
//
//                // It is used to change the duration and
//                // the interpolator of transitions
//
//                // It is used to change the duration and
//                // the interpolator of transitions
//
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
//        return Vh(
//            WallpaperSliderEachItemBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(holder: Vh, position: Int) {
//        holder.onBind(list[position])
//    }
//
//    override fun getItemCount(): Int = list.size
//}