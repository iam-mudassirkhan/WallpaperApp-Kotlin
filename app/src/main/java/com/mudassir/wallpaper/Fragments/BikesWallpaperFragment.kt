import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mudassir.wallpaper.Adapters.BikesWallpaperAdapter
import com.mudassir.wallpaper.Adapters.CarsWallpaperAdapter
import com.mudassir.wallpaper.Api.ApiClientWithOutMVVM
import com.mudassir.wallpaper.Interfaces.WallpaperOnClickListener
import com.mudassir.wallpaper.Models.AnimalsWallpaperModel.Dir
import com.mudassir.wallpaper.Models.getWallpapers
import com.mudassir.wallpaper.R
import com.mudassir.wallpaper.databinding.FragmentBikesWallpaperBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BikesWallpaperFragment : Fragment() , WallpaperOnClickListener{
private var binding : FragmentBikesWallpaperBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBikesWallpaperBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.mainFragment)

            }
        })


        ApiClientWithOutMVVM().getClient()?.getBikesWallpapers()?.enqueue( object :
            Callback<getWallpapers> {
            override fun onResponse(call: Call<getWallpapers>, response: Response<getWallpapers>) {
                (response.body()?.dir as? ArrayList<com.mudassir.wallpaper.Models.Dir>)?.let {
                    setUpRecyclerView(it)
                }
            }

            override fun onFailure(call: Call<getWallpapers>, t: Throwable) {
                Toast.makeText(requireContext(), "Failed ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }

        }
        )

    }



    fun setUpRecyclerView(wallpaperList: ArrayList<com.mudassir.wallpaper.Models.Dir>) {
        binding?.bikesWallpaperRV?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.bikesWallpaperRV?.adapter = BikesWallpaperAdapter(requireContext(), wallpaperList, this)
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