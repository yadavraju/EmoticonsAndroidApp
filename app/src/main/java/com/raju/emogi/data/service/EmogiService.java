package com.raju.emogi.data.service;

import com.raju.emogi.data.model.BaseContent;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Raju Yadav
 * I have hosted that contents.json file to my github
 * used Retrofit to fetch that json. I can do directly
 * putting inside assets folder then do parsing all these
 */

public interface EmogiService {

    @NotNull
    String BASE_URL = "https://raw.githubusercontent.com/yadavraju/";

    @GET("emoticon/master/contents.json")
    Observable<BaseContent> getEmogiBaseContent();
}
