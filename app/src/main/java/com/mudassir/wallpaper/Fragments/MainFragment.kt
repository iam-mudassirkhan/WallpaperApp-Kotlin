package com.mudassir.wallpaper.Fragments

import BikesWallpaperFragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.fragment.findNavController
import com.mudassir.wallpaper.Adapters.ViewPagerAdapter
import com.mudassir.wallpaper.R
import com.mudassir.wallpaper.databinding.FragmentMainBinding

class MainFragment : Fragment() {
private var binding: FragmentMainBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)

        val adapter = activity?.supportFragmentManager?.let { ViewPagerAdapter(it) }
        adapter?.addFragment(AllWallpaperFragment(), "All")
        adapter?.addFragment(AnimalsWallpaperFragment(), "Animals")
        adapter?.addFragment(CarsWallpaperFragment(), "Cars")
        adapter?.addFragment(BikesWallpaperFragment(), "Bikes")
        adapter?.addFragment(CityWallpapersFragment(), "City")
        adapter?.addFragment(ArtWallpaperFragment(), "Art")
        adapter?.addFragment(FashionAmericaFragment(), "Fashion")
        adapter?.addFragment(BookMarkedFragment(), "favourite")

        binding?.viewPager?.adapter = adapter
        binding?.tbLayout?.setupWithViewPager(binding?.viewPager)


        // open and close drawer by sliding
                binding?.apply {
                  val  toggle= ActionBarDrawerToggle(
                        requireActivity(),
                        drawerLayout,
                        R.string.downloadbutton,
                        R.string.wallpaper
                    )
                    drawerLayout.addDrawerListener(toggle!!)
                    toggle?.syncState()

                }
        binding?.customToolbar?.btnDrawerOpenClose?.setOnClickListener {
            binding?.drawerLayout?.open()
        }

        binding?.nav?.closeDrawer?.setOnClickListener {
            binding?.drawerLayout?.close()
        }

        binding?.nav?.btnGoToFavouriteFragment?.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_bookMarkedFragment)
        }

        return binding?.root
    }

}