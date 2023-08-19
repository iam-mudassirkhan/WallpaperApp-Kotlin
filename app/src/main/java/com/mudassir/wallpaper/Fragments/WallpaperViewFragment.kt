package com.mudassir.wallpaper.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.mudassir.wallpaper.R
import com.mudassir.wallpaper.databinding.FragmentWallpaperViewBinding


class WallpaperViewFragment : Fragment() {
private var binding: FragmentWallpaperViewBinding? = null

    companion object {
        fun newInstance(imageUrl2: String): WallpaperViewFragment {
            val fragment = WallpaperViewFragment()
            val args = Bundle()
            args.putString("imageUrl", imageUrl2)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWallpaperViewBinding.inflate(layoutInflater, container, false)

//        val imageUrl = arguments?.getString("imageUrl")
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
               findNavController().navigate(R.id.action_wallpaperViewFragment_to_mainFragment)
//                findNavController().popBackStack(R.id.action_wallpaperViewFragment_to_mainFragment, true)

            }
        })

        val imageUrl = arguments?.getString("imageUrl")
//        val imageUrl = requireArguments().getParcelableArrayList<Dir>("imageUrl")

        Glide.with(requireContext())
            .load(imageUrl)
            /* .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(false)*/
            .centerCrop()
            .into(binding!!.wallpaperImageView)

/*binding?.wallpaperSliderViewPager?.adapter =
    imageUrl?.let { WallpapersSliderAdapter(requireContext(), it.toString()) }

        binding?.apply {
            wallpaperSliderViewPager.clipToPadding =false
            wallpaperSliderViewPager.clipChildren=false
            wallpaperSliderViewPager.offscreenPageLimit=3
            wallpaperSliderViewPager.getChildAt(0).overScrollMode= RecyclerView.OVER_SCROLL_NEVER
            val compositePageTransformer= CompositePageTransformer()
            compositePageTransformer.addTransformer(MarginPageTransformer(40))
            compositePageTransformer.addTransformer { page, position ->
                val r: Float = 1 - abs(position)
                page.scaleY = 0.95f + r * 0.05f
            }
            wallpaperSliderViewPager.setPageTransformer(compositePageTransformer)


        }*/

        return binding?.root
    }



}