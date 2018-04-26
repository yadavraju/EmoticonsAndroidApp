package com.raju.emogi.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Raju Yadav
 */

@Parcelize
data class Assets (@SerializedName("asset_id") val assetId : String, val url : String,
                   @SerializedName("file_extension") val fileExtension: String,
                   val size : String) : Parcelable
