package com.example.cantinr.cantinr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by D064040 on 14.06.2018.
 */

public class RecyclerAdapterGallery extends RecyclerView.Adapter<RecyclerAdapterGallery.ViewHolder> {

    private ArrayList<Bitmap> networkImages;

    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public ImageView itemImage;
        //public TextView itemTitle;

        public Intent intentDetail, inPhoto;
        //Intent inPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
           // itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemImage = (ImageView)itemView.findViewById(R.id.item_image);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout_gallery, viewGroup, false);
    ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterGallery.ViewHolder viewHolder, int position) {
        //viewHolder.itemTitle.setText(titles[position]);
        //viewHolder.itemImage.setImageResource(images[position]);
        //viewHolder.fab2.setVisibility(View.VISIBLE);
        viewHolder.itemImage.setImageBitmap(networkImages.get(position));
    }

    @Override
    public int getItemCount() {
        return networkImages.size();
    }

    public void setImages(ArrayList<Bitmap> mImages) {
        networkImages = mImages;
        Log.d("LENGTH", String.valueOf(networkImages.size()));
        notifyDataSetChanged();
    }

}