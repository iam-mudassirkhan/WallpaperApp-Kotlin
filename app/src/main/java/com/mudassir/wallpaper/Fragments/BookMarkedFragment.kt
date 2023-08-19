package com.mudassir.wallpaper.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mudassir.wallpaper.Adapters.FavouriteWallpaperAdapter
import com.mudassir.wallpaper.Models.AnimalsWallpaperModel.Dir
import com.mudassir.wallpaper.Interfaces.WallpaperOnClickListener
import com.mudassir.wallpaper.R
import com.mudassir.wallpaper.Repository.RoomDbFactory
import com.mudassir.wallpaper.Repository.RoomDbRepository
import com.mudassir.wallpaper.RoomDb.WallpaperDatabase
import com.mudassir.wallpaper.RoomDb.WallpaperEntity
import com.mudassir.wallpaper.ViewModels.RoomDbViewModel
import com.mudassir.wallpaper.databinding.FragmentBookMarkedBinding


class BookMarkedFragment : Fragment(), WallpaperOnClickListener {
private var binding : FragmentBookMarkedBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val wallpaperDB = WallpaperDatabase.getDatabase(requireContext()).getWallpapersDao()
        val roomDbRepo = RoomDbRepository(wallpaperDB)
        val roomDbViewModel = ViewModelProvider(this, RoomDbFactory(roomDbRepo))[RoomDbViewModel::class.java]

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.mainFragment)

            }
        })

        roomDbViewModel.getAllWallpaper.observe(viewLifecycleOwner, Observer {
            setUpRecyclerView(it)
        }

        )


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBookMarkedBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    fun setUpRecyclerView(favWallpaperList: List<WallpaperEntity>) {
        binding?.favouriteWallpaperRV?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.favouriteWallpaperRV?.adapter = FavouriteWallpaperAdapter(requireContext(), favWallpaperList, this)
    }

    override fun onWallpaperClick(imageUrl: String) {
        val bundle = Bundle()
        bundle.putString("wallpaperUrl", imageUrl)
        findNavController().navigate(R.id.action_mainFragment_to_displayWallpaperFragment, bundle)
    }

    override fun onWallpaperLongClick(imageUrl: ArrayList<Dir>) {
        TODO("Not yet implemented")
    }

}