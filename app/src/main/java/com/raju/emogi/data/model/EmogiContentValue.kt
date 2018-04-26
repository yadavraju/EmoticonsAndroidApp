package com.raju.emogi.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Raju Yadav
 */

@Parcelize
data class EmogiContentValue(@SerializedName("content_id") val contentId: String,
                             val assets: ArrayList<Assets>, val tags: ArrayList<String>) : Parcelable
