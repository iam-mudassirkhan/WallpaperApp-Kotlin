package com.mudassir.wallpaper.RoomDb

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallpapers")
data class WallpaperEntity(
    @PrimaryKey (autoGenerate = true)
    val id : Int?,
    val wallpaperUrl : String,
    val isFavorite: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(wallpaperUrl)
        parcel.writeByte(if (isFavorite) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WallpaperEntity> {
        override fun createFromParcel(parcel: Parcel): WallpaperEntity {
            return WallpaperEntity(parcel)
        }

        override fun newArray(size: Int): Array<WallpaperEntity?> {
            return arrayOfNulls(size)
        }
    }
}
