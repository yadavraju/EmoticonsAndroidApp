package com.raju.emogi.activity;

import com.raju.emogi.data.model.Assets;

import java.util.ArrayList;

/**
 * Created by Raju Yadav
 */

public interface MainView {

    void initializeView();

    void showProgress();

    void hideProgress();

    void showThumbSizeAssetsList(ArrayList<Assets> assets);

    void showFullSizeAssetsList(ArrayList<Assets> assets); // not used for now

    void enableSearchView(boolean enable);
}
