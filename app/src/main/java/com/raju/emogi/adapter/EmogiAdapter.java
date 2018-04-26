package com.raju.emogi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.raju.emogi.R;
import com.raju.emogi.data.model.Assets;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Raju Yadav
 */

public class EmogiAdapter extends RecyclerView.Adapter<EmogiAdapter.ViewHolder> {

    private ArrayList<Assets> assets;
    private Context context;

    public EmogiAdapter(ArrayList<Assets> assets) {
        this.assets = assets;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.emogi_adapter, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Assets a = assets.get(position);
        if (!TextUtils.isEmpty(a.getUrl())) {
            Glide.with(context)
                    .load(a.getUrl())
                    .into(holder.image);
        }

    }

    @Override
    public int getItemCount() {
        if (assets == null) {
            return 0;
        }
        return assets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}


