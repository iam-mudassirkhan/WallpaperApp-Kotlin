package com.mudassir.wallpaper.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mudassir.wallpaper.R
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.mudassir.wallpaper.Adapters.ImageAdapter
import com.mudassir.wallpaper.Models.AnimalsWallpaperModel.Dir
import com.mudassir.wallpaper.databinding.FragmentImageVeiwBinding
import kotlin.math.abs


class ImageVeiwFragment : Fragment() {
    private lateinit var binding: FragmentImageVeiwBinding
//    private lateinit var  viewPager2: ViewPager2
    private lateinit var handler : Handler
    private lateinit var imageList:ArrayList<Int>
    private lateinit var adapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentImageVeiwBinding.inflate(layoutInflater, container, false)

        init()
        setUpTransformer()

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
//                handler.removeCallbacks(runnable)
//                handler.postDelayed(runnable , 2000)

                val imageList2 = requireArguments().getParcelableArrayList<Parcelable>("imageList")
                adapter = ImageAdapter(imageList2 as java.util.ArrayList<Dir>?, binding.viewPager2)
                binding.apply {
                    viewPager2.adapter = adapter
                    viewPager2.offscreenPageLimit = 3
                    viewPager2.clipToPadding = false
                    viewPager2.clipChildren = false
                    viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

                }

            }
        })

        return binding.root
    }


    override fun onPause() {
        super.onPause()

        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()

        handler.postDelayed(runnable , 2000)
    }

    private val runnable = Runnable {
        binding.viewPager2.currentItem = binding.viewPager2.currentItem + 1
    }

    private fun setUpTransformer(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        binding.viewPager2.setPageTransformer(transformer)
    }


    private fun init(){
//        viewPager2 = findViewById(R.id.viewPager2)

        handler = Handler(Looper.myLooper()!!)
        imageList = ArrayList()

        imageList.add(R.drawable.one)
        imageList.add(R.drawable.two)
        imageList.add(R.drawable.three)
        imageList.add(R.drawable.four)
        imageList.add(R.drawable.five)
        imageList.add(R.drawable.six)
        imageList.add(R.drawable.seven)
        imageList.add(R.drawable.eight)

        val imageList2 = requireArguments().getParcelableArrayList<Parcelable>("imageList")
        adapter = ImageAdapter(imageList2 as java.util.ArrayList<Dir>?, binding.viewPager2)
binding.apply {
    viewPager2.adapter = adapter
    viewPager2.offscreenPageLimit = 3
    viewPager2.clipToPadding = false
    viewPager2.clipChildren = false
    viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

}

    }


}