package com.mudassir.wallpaper.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.mudassir.wallpaper.Models.AnimalsWallpaperModel.Dir
import com.mudassir.wallpaper.R
import java.util.ArrayList

class ImageAdapter(private val imageList: ArrayList<Dir>?, private val viewPager2: ViewPager2) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.image_container, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
      val  getImageList = imageList?.get(position)
        holder.imageView.setImageResource((imageList?.get(position) ?: "") as Int)
        if (position == imageList?.size?.minus(1) ?: ""){
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return imageList?.size ?: 0
    }

    private val runnable = Runnable {
        imageList?.addAll(imageList)
        notifyDataSetChanged()
    }
}