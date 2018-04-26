package com.raju.emogi.data.service

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.raju.emogi.data.model.DataParser
import com.raju.emogi.data.model.EmogiContent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by Raju Yadav
 * Factory class for convenient creation of the Api Service interface
 */

object ApiFactory {
    private var emogiService: EmogiService? = null

    fun getEmogiWholeData(): EmogiService? {
        val gson = GsonBuilder()
                .registerTypeAdapter(EmogiContent::class.java, DataParser())
                .create()

        emogiService = Retrofit.Builder()
                .baseUrl(EmogiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(EmogiService::class.java)
        return emogiService
    }
}
