package com.mudassir.wallpaper.Fragments

import android.Manifest
import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mudassir.wallpaper.R
import com.mudassir.wallpaper.Repository.RoomDbFactory
import com.mudassir.wallpaper.Repository.RoomDbRepository
import com.mudassir.wallpaper.RoomDb.WallpaperDatabase
import com.mudassir.wallpaper.RoomDb.WallpaperEntity
import com.mudassir.wallpaper.Utils.Constants
import com.mudassir.wallpaper.ViewModels.RoomDbViewModel
import com.mudassir.wallpaper.databinding.FragmentDisplayWallpaperBinding
import com.mudassir.wallpaper.databinding.ItemBottomSheetBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.Objects
import java.util.Random


class DisplayWallpaperFragment : Fragment() {

//    private val args by navArgs<DisplayWallpaperFragmentArgs>()

    private var binding: FragmentDisplayWallpaperBinding? = null
    private var isFullScreen = false
    private var imageUrl: String? = null
    // Constants for permission request
    private val WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Hide the action bar
        (activity as AppCompatActivity).supportActionBar?.hide()

        val wallpaperDB = WallpaperDatabase.getDatabase(requireContext()).getWallpapersDao()
        val roomDbRepo = RoomDbRepository(wallpaperDB)
        val roomDbViewModel = ViewModelProvider(this, RoomDbFactory(roomDbRepo)).get(RoomDbViewModel::class.java)

         imageUrl = arguments?.getString("wallpaperUrl")

        binding?.setASWallpaperBtn?.setOnClickListener {
            showBottumSheet()
        }

        Glide.with(requireContext())
            .load(imageUrl)
            /* .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(false)*/
            .centerCrop()
            .into(binding!!.displayWallpaperImageView)

        // Toggle fullscreen and system UI visibility when the image view is clicked
        binding?.displayWallpaperImageView?.setOnClickListener {
            toggleFullScreen()
        }



        binding?.downloadWallpaperBtn?.setOnClickListener {

//            val result : Deferred<Bitmap?> = GlobalScope.async {
//                imageDownloadUrl.toBitmap()
//            }
//
//            GlobalScope.launch(Dispatchers.Main) {
//                saveImage(result.await())
//            }

//                downloadWallpaperImage(imageUrl)
            downloadImageFromWeb(imageUrl)



        }
binding?.removeFromFavouriteBtn?.imageTintList = ColorStateList.valueOf(Color.rgb(255,50, 50))

            val wallpaperExists = roomDbViewModel.checkIfWallpaperExists(imageUrl!!)
            if (wallpaperExists) {
                binding?.addToFavouriteBtn?.visibility = View.VISIBLE
            }
            else binding?.removeFromFavouriteBtn?.visibility = View.GONE


        binding?.addToFavouriteBtn?.setOnClickListener {

            GlobalScope.launch {
                roomDbViewModel.insertWallpaper(imageUrl!!)
                roomDbViewModel.updateFavoriteStatus(imageUrl!!, true)
            }

            binding?.addToFavouriteBtn?.visibility = View.GONE
            binding?.removeFromFavouriteBtn?.visibility = View.VISIBLE

            Toast.makeText(requireContext(), "Added to Favourite", Toast.LENGTH_SHORT).show()


        }

        binding?.removeFromFavouriteBtn?.setOnClickListener {
            val removeWallpaper = WallpaperEntity(6, imageUrl!!, false)
            roomDbViewModel.removeWallpaperFromFavourite(removeWallpaper)

            binding?.removeFromFavouriteBtn?.visibility = View.GONE
            binding?.addToFavouriteBtn?.visibility = View.VISIBLE

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDisplayWallpaperBinding.inflate(layoutInflater, container, false)

        return binding?.root

    }


    override fun onDestroyView() {
        super.onDestroyView()
        // Show the action bar when the fragment is destroyed
        (activity as AppCompatActivity).supportActionBar?.show()
        binding = null
    }

    private fun toggleFullScreen() {
        isFullScreen = !isFullScreen

        // Hide or show the status bar and system buttons
        if (isFullScreen) {
            activity?.window?.decorView?.systemUiVisibility =
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            binding?.apply {
                downloadWallpaperBtn.visibility = View.GONE
                addToFavouriteBtn.visibility = View.GONE
                removeFromFavouriteBtn.visibility = View.GONE
                setASWallpaperBtn.visibility = View.GONE
            }
        } else {
            activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            binding?.apply {
                downloadWallpaperBtn.visibility = View.VISIBLE
                addToFavouriteBtn.visibility = View.VISIBLE
                removeFromFavouriteBtn.visibility = View.VISIBLE
                setASWallpaperBtn.visibility = View.VISIBLE
            }
        }
    }

    private fun showBottumSheet() {
        val binding: ItemBottomSheetBinding = ItemBottomSheetBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(binding.root)
        dialog.show()

        binding.closeButtonSheet.setOnClickListener {
            dialog.dismiss()
        }


//        val radioGroup = view.findViewById<RadioGroup>(R.id.radioButtonsGroup)
        binding.radioButtonsGroup.findViewById<RadioButton>(R.id.setAsLockAndHomeRadioBtn).isChecked =
            true

        binding.wallpaperApplyBtn.setOnClickListener {
            val checkedRadioButtonId = binding.radioButtonsGroup.checkedRadioButtonId

            when (checkedRadioButtonId) {
                R.id.setAsLockAndHomeRadioBtn -> {

                    setAsBackground(Constants.BackGroundState.lockScreen)
                    setAsBackground(Constants.BackGroundState.homeScreen)
                    dialog.dismiss()
                }

                R.id.setASLockScreenRadioBtn -> {
                    setAsBackground(Constants.BackGroundState.lockScreen)
                    dialog.dismiss()
                }

                R.id.setAsHomeScreenRadioBtn -> {
                    setAsBackground(Constants.BackGroundState.homeScreen)
                    dialog.dismiss()
                }
            }
        }

    }

    private fun setAsBackground(LockOrHomeScreen: Int) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                val wallpaperManager = WallpaperManager.getInstance(context)
                val image = binding?.displayWallpaperImageView

                if (image?.drawable == null) {
                    Toast.makeText(context, "Wait to loading", Toast.LENGTH_LONG).show()
                } else {
                    val bitmap = (image.drawable as BitmapDrawable).bitmap
                    wallpaperManager.setBitmap(bitmap, null, true, LockOrHomeScreen)
                    Toast.makeText(context, "DONE", Toast.LENGTH_LONG).show()
                }

            } catch (e: IOException) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }


    fun URL.toBitmap(): Bitmap? {
        return try {

            BitmapFactory.decodeStream(openStream())

        } catch (e: IOException) {
            null
        }
    }


    private fun saveImage(image: Bitmap?) {
        val random1 = Random().nextInt(520985)
        val random2 = Random().nextInt(520985)
        val name = "AMOLED-${random1 + random2}"
        val data: OutputStream
        try {
            val resolver = requireActivity().contentResolver
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "$name.jpg")
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            contentValues.put(
                MediaStore.MediaColumns.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + "/" + "Wallpapers"
            )
            val imageUri =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            data = resolver.openOutputStream(Objects.requireNonNull(imageUri)!!)!!
            image?.compress(Bitmap.CompressFormat.JPEG, 100, data)
            Objects.requireNonNull<OutputStream?>(data)
            Toast.makeText(requireContext(), "Image Save", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Image Not Save due to ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }

    }


    // Function to download and save the wallpaper image
    private fun downloadWallpaperImage(imageUrl: String?) {
        // Check if the WRITE_EXTERNAL_STORAGE permission is granted
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission already granted, proceed with the download
//            downloadImageFromWeb(imageUrl)
        } else {
            // Permission not granted, request the permission
            requestStoragePermission()
        }
    }

    // Request the WRITE_EXTERNAL_STORAGE permission at runtime
    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            WRITE_EXTERNAL_STORAGE_PERMISSION_CODE
        )
    }

    // Handle the permission request result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == WRITE_EXTERNAL_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with the download
//                downloadImage()
//                downloadImageFromWeb(imageUrl)
            } else {
                // Permission denied, handle accordingly (e.g., show a message or disable functionality)
                Toast.makeText(
                    requireContext(),
                    "Write external storage permission denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }






    // Function to save the bitmap to the external storage directory
    private fun saveImageToExternalStorage(bitmap: Bitmap) {
        val displayName = "wallpaper_${System.currentTimeMillis()}.jpg"

        // Get the directory path where the image will be saved
        val directory = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // For Android Q (API level 29) and above, use MediaStore
            val contentResolver = requireContext().contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/*")
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/Wallpaper")
            }
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        } else {
            // For devices below Android Q, use the legacy method
            val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            File(directory, "Wallpaper").apply { mkdirs() }
        }

        // Save the image to the directory
        val outputStream = directory?.let {
            requireContext().contentResolver.openOutputStream(it as Uri)
        }

        outputStream?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(requireContext(), "Image saved successfully", Toast.LENGTH_SHORT).show()
        } ?: run {
            Toast.makeText(requireContext(), "Failed to save the image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun downloadImageNew(filename: String, downloadUrlOfImage: String) {
        try {
            val dm = requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
            val downloadUri = Uri.parse(downloadUrlOfImage)
            val request = DownloadManager.Request(downloadUri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(filename)
                .setMimeType("image/jpeg") // Your file type. You can use this code to download other file types also.
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_PICTURES,
                    File.separator + filename + ".jpg"
                )
            dm!!.enqueue(request)
            Toast.makeText(requireContext(), "Image download started.", Toast.LENGTH_SHORT).show()
        } catch (e: java.lang.Exception) {
            Toast.makeText(requireContext(), "Image download failed.", Toast.LENGTH_SHORT).show()
        }
    }

 fun downloadImageFromWeb(url: String?) {
        try {
            val downloadManager =
                context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

            val downloadUri = Uri.parse(url)

            val request = DownloadManager.Request(downloadUri).apply {
                setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                    .setMimeType("image/*")
                    .setAllowedOverRoaming(false)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setTitle("wallpaper")
                    .setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_PICTURES,
                        File.separator + "wallpaper" + ".png",
                    )

            }
            downloadManager.enqueue(request)
      //      Toast.makeText(context, "Downloading...", Toast.LENGTH_LONG).show()
            Toast.makeText(requireContext(),"Downloading...",Toast.LENGTH_SHORT).show()
            Log.i("isWallpaperDownloaded","$url")


        } catch (e: java.lang.Exception) {
            Toast.makeText(context, "Image Download Failed ${e.message}", Toast.LENGTH_LONG).show()
        }

    }


}