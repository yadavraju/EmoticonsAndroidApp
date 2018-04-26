package com.raju.emogi.activity;

import android.util.Log;

import com.raju.emogi.Utils.CollectionUtils;
import com.raju.emogi.data.model.Assets;
import com.raju.emogi.data.model.EmogiContentValue;
import com.raju.emogi.data.service.ApiFactory;
import com.raju.emogi.data.service.EmogiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Todo Used Null Object pattern for MainView ,
 * A null object replaces check of NULL object instance.
 * Instead of putting if check for a null value,
 * Null Object reflects a do nothing relationship.
 * Such Null object can also be used to provide default behaviour
 * in case data is not available.
 * Todo https://en.wikipedia.org/wiki/Null_object_pattern
 * Created by Raju Yadav
 */

public class MainActivityPresenter {
    private static final String TAG = MainActivityPresenter.class.getSimpleName();

    // This full fill null object pattern
    private MainView nullMainView = new MainView() {
        @Override
        public void initializeView() {/*ignore*/}

        @Override
        public void showProgress() {/*ignore*/}

        @Override
        public void hideProgress() {/*ignore*/}

        @Override
        public void showThumbSizeAssetsList(ArrayList<Assets> assets) {/*ignore*/}

        @Override
        public void showFullSizeAssetsList(ArrayList<Assets> assets) {/*ignore*/}

        @Override
        public void enableSearchView(boolean enable) {/*ignore*/}
    };
    private MainView mainView;
    HashMap<String, ArrayList<Assets>> tagWithAssetFilteredData = new HashMap<>();
    ArrayList<EmogiContentValue> emogiContentValues = new ArrayList<>();
    private Disposable emogiDisposable, filterDataDisposable;

    public MainActivityPresenter(MainView mainView) {
        this.mainView = mainView != null ? mainView : nullMainView;
        initializeView();
    }

    private void initializeView() {
        mainView.initializeView();
        loadEmogiData();
    }

    private void loadEmogiData() {
        mainView.showProgress();
        EmogiService emogiService = ApiFactory.INSTANCE.getEmogiWholeData();
        if (emogiService != null) {
            emogiDisposable = emogiService.getEmogiBaseContent()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(schoolList -> {
                        Log.e(TAG, "" + schoolList.getEmogiContent().dataValues.size());
                        getEmogiComtentListData(schoolList.getEmogiContent().dataValues);
                        mainView.hideProgress();
                    }, error -> {
                        Log.e(TAG, "Failed to load emogi content");
                        mainView.hideProgress();
                    });
        }
    }

    private void getEmogiComtentListData(HashMap<String, EmogiContentValue> emogiContent) {
        filterDataDisposable = Observable.just(emogiContent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mainView -> {
                    for (Map.Entry<String, EmogiContentValue> entry : emogiContent.entrySet()) {
                        EmogiContentValue value = entry.getValue();
                        emogiContentValues.add(value);
                    }
                    filterOutTagAndAssets(emogiContentValues);
                });

    }

    /*
    * This method is used for putting tag and Asset array in map
    * so that while searching content from key word i can directly
    * loop to that map get that value as list of Assets
    */
    private void filterOutTagAndAssets(ArrayList<EmogiContentValue> emogiContentValues) {
        for (EmogiContentValue tagAndAssets : emogiContentValues) {
            for (String tag : tagAndAssets.getTags()) {
                tagWithAssetFilteredData.put(tag, tagAndAssets.getAssets());
            }
        }

        // passed boolean value to make search view enable if tagWithAssetFilteredData map
        // is not null && size greater than 0 means there is some content in map
        // or disable if both condition didn't match
        mainView.enableSearchView(tagWithAssetFilteredData != null && tagWithAssetFilteredData.size() > 0);
    }

    /*
    * This method used filter Asset on the basis of tag searched
    * Data is passed to Activity through interface to populate adapter
    */
    public void getFilteredData(String keyword) {
        ArrayList<Assets> finalAsset = new ArrayList<>();
        for (Map.Entry<String, ArrayList<Assets>> entry : tagWithAssetFilteredData.entrySet()) {
            for (String trimmedKeyword : CollectionUtils.getWords(keyword)) {
                if (entry.getKey().startsWith(trimmedKeyword)) {
                    for (Assets thumbSize : entry.getValue()) {
                        // Currently i am just adding thumb size image to list showing that to adapter
                        if (thumbSize.getSize().equalsIgnoreCase(CollectionUtils.THUMB)) {
                            finalAsset.add(thumbSize);
                        }
                    }
                    mainView.showThumbSizeAssetsList(finalAsset);
                }
            }
        }

    }

    /*
    * Disposed observable listener to prevent from
    * being memory leaks
    */
    public void Destroy() {
        mainView = null;
        if (emogiDisposable != null) {
            emogiDisposable.dispose();
        }

        if (filterDataDisposable != null) {
            filterDataDisposable.dispose();
        }
    }
}

