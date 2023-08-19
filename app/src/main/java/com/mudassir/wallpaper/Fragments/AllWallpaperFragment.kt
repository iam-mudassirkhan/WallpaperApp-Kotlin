package com.mudassir.wallpaper.Fragments

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mudassir.wallpaper.Adapters.AllWallpaperAdapter
import com.mudassir.wallpaper.Models.AllWallpaperThumbnail.GetAllWallpaperThumbnails
import com.mudassir.wallpaper.Api.ApiClientWithOutMVVM
import com.mudassir.wallpaper.Interfaces.WallpaperOnClickListener
import com.mudassir.wallpaper.Models.AllWallpaperThumbnail.Dir
import com.mudassir.wallpaper.R
import com.mudassir.wallpaper.databinding.FragmentAllWallpaperBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllWallpaperFragment : Fragment(), WallpaperOnClickListener {

 private var binding: FragmentAllWallpaperBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllWallpaperBinding.inflate(layoutInflater, container, false)

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.mainFragment)

            }
        })

        ApiClientWithOutMVVM().getClient()?.getAllWallpaperThumbnail()?.enqueue(object : Callback<GetAllWallpaperThumbnails>{
            override fun onResponse(
                call: Call<GetAllWallpaperThumbnails>,
                response: Response<GetAllWallpaperThumbnails>
            ) {

                (response.body()?.dir as? ArrayList<Dir>)?.let {
                    setUpRecyclerView(it)
                    binding?.progressBar?.visibility = View.GONE
                }

            }

            override fun onFailure(call: Call<GetAllWallpaperThumbnails>, t: Throwable) {
                Toast.makeText(requireContext(), "${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                binding?.progressBar?.visibility = View.GONE
            }
        })


        return binding?.root
    }

    fun setUpRecyclerView(allWallpaperList: ArrayList<Dir>) {
        binding?.allWallpaperRV?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.allWallpaperRV?.adapter = AllWallpaperAdapter(requireContext(), allWallpaperList, this)
    }

    override fun onWallpaperClick(imageUrl: String) {
        val bundle = Bundle()
        bundle.putString("wallpaperUrl", imageUrl)
        findNavController().navigate(R.id.action_mainFragment_to_displayWallpaperFragment, bundle)
    }

    override fun onWallpaperLongClick(imageUrl: ArrayList<com.mudassir.wallpaper.Models.AnimalsWallpaperModel.Dir>) {
        TODO("Not yet implemented")
    }

}