package com.mudassir.wallpaper.Models.AllWallpaperThumbnail

import android.os.Parcel
import android.os.Parcelable

data class Dir(
    val `file`: String
) : Parcelable{
    constructor(parcel: Parcel) : this(parcel.readString() ?: "") {
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Dir> {
        override fun createFromParcel(parcel: Parcel): Dir {
            return Dir(parcel)
        }

        override fun newArray(size: Int): Array<Dir?> {
            return arrayOfNulls(size)
        }
    }

}