package com.raju.emogi.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Raju Yadav
 * RecyclerView view item decoration
 */

public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private final int mHeight;
    private final int mWidth;

    public GridSpaceItemDecoration(int spacing) {
        this(spacing, spacing);
    }

    public GridSpaceItemDecoration(int height, int width) {
        this.mHeight = height;
        this.mWidth = width;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = mHeight / 4;
        outRect.bottom = mHeight / 4;
        outRect.left = mWidth / 4;
        outRect.right = mWidth / 4;
    }
}
