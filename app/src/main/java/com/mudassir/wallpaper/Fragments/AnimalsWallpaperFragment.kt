package com.mudassir.wallpaper.Fragments

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mudassir.wallpaper.Adapters.AnimalWallpaperAdapter
import com.mudassir.wallpaper.Api.ApiClient
import com.mudassir.wallpaper.Api.ApiClientWithOutMVVM
import com.mudassir.wallpaper.Api.Api_Interface
import com.mudassir.wallpaper.Models.AnimalsWallpaperModel.Dir
import com.mudassir.wallpaper.Models.AnimalsWallpaperModel.GetAnimalsWallpaper
import com.mudassir.wallpaper.Interfaces.WallpaperOnClickListener
import com.mudassir.wallpaper.R
import com.mudassir.wallpaper.Repository.MainViewModelFactory
import com.mudassir.wallpaper.Repository.Repository
import com.mudassir.wallpaper.ViewModels.MainViewModel
import com.mudassir.wallpaper.databinding.FragmentAnimalsWallpaperBinding
import retrofit2.Call
import retrofit2.Response
import java.io.ObjectStreamException


class AnimalsWallpaperFragment : Fragment(), WallpaperOnClickListener {

private var binding: FragmentAnimalsWallpaperBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAnimalsWallpaperBinding.inflate(layoutInflater, container, false)

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()

            }
        })

//        binding?.animalsWallpaperRV?.layoutManager = GridLayoutManager(requireContext(), 3)
//        binding?.animalsWallpaperRV?.adapter = AnimalWallpaperAdapter(requireContext(), )

        val apiService = ApiClient.getInstance().create(Api_Interface::class.java)
        val repo = Repository(requireContext(), apiService)
        val mainViewModel = ViewModelProvider(this, MainViewModelFactory(repo)).get(MainViewModel::class.java)

//mainViewModel.animals().observe(viewLifecycleOwner, Observer {
//
//})

       ApiClientWithOutMVVM().getClient()?.getAnimalsWallpaper()?.enqueue(object :
            retrofit2.Callback<GetAnimalsWallpaper>{
            override fun onResponse(
                call: Call<GetAnimalsWallpaper>,
                response: Response<GetAnimalsWallpaper>
            ) {
                (response.body()?.dir as? ArrayList<Dir>)?.let {
                    setUpRecyclerView(it)
                }

            }

            override fun onFailure(call: Call<GetAnimalsWallpaper>, t: Throwable) {
                Toast.makeText(requireContext(), t.localizedMessage+" Failed", Toast.LENGTH_SHORT).show()

            }
        })




        return binding?.root
    }
    fun setUpRecyclerView(animalWallpaperList: ArrayList<Dir>) {
        binding?.animalsWallpaperRV?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.animalsWallpaperRV?.adapter = AnimalWallpaperAdapter(requireContext(), animalWallpaperList, this)
    }

    override fun onWallpaperClick(imageUrl: String) {
        val bundle = Bundle()
        bundle.putString("wallpaperUrl", imageUrl)
        findNavController().navigate(R.id.action_mainFragment_to_displayWallpaperFragment, bundle)

    }

    override fun onWallpaperLongClick(imageUrl: ArrayList<Dir>) {
        //        val wallpaperViewFragment = WallpaperViewFragment.newInstance(imageUrl)
        // Replace current fragment with wallpaper detail fragment
        val bundle = Bundle()
        bundle.putParcelableArrayList("imageUrl",  imageUrl as ArrayList<out Parcelable>)
//        parentFragmentManager.beginTransaction()
//            .replace(R.id.fragment_host, wallpaperViewFragment)
//            .addToBackStack(null)
//            .commit()
        findNavController().navigate(R.id.action_mainFragment_to_imageVeiwFragment, bundle)
//        findNavController().navigate(R.id.action_mainFragment_to_wallpaperViewFragment2, bundle)
    }


    /*  private fun openWallpaperDetailFragment(wallpaperUrl: String) {
          val wallpaperDetailFragment = WallpaperDetailFragment()
          wallpaperDetailFragment.setWallpaperUrl(wallpaperUrl)

          requireActivity().supportFragmentManager.beginTransaction()
              .replace(R.id.frameLayout, wallpaperDetailFragment)
              .addToBackStack(null)
              .commit()
      }*/

}