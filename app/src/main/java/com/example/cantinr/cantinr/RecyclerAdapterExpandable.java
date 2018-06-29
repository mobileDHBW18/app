package com.example.cantinr.cantinr;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

/**
 * Created by D064040 on 14.06.2018.
 */

public class RecyclerAdapterExpandable extends RecyclerView.Adapter<RecyclerAdapterExpandable.ViewHolder> {

    private String[] titles = {"Hähnchenbrust mit"};

    private String[] details = {"Kartoffelstampf und Bratensoße, enthält Gluten und Schalenfrüchte"};

    private int[] images = { R.drawable.img1};

    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public ImageView itemImage;
        public TextView itemTitle;
        public TextView itemDetail;
        public FloatingActionButton fab;

        public Intent intentDetail, inPhoto, inGallery;
        //Intent inPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            intentDetail= new Intent(itemView.getContext(), cardDetail.class);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            inPhoto= new Intent(itemView.getContext(), PhotoActivity.class);
            inGallery= new Intent(itemView.getContext(), galleryActivity.class);
            itemImage = (ImageView)itemView.findViewById(R.id.item_image);
            itemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //cameraView.captureImage();
                    view.getContext().startActivity(inGallery);
                }
            });
            Button btChickenGrey, btCowGrey, btPigGrey, btFishGrey, btVeggieGrey;

            btChickenGrey = (Button) itemView.findViewById(R.id.btChickenGrey);
            btCowGrey = (Button) itemView.findViewById(R.id.btCowGrey);
            btPigGrey = (Button) itemView.findViewById(R.id.btPigGrey);
            btVeggieGrey = (Button) itemView.findViewById(R.id.btVeggieGrey);
            btFishGrey= (Button) itemView.findViewById(R.id.btFishGrey);

            btChickenGrey.setEnabled(false);
            btCowGrey.setEnabled(false);
            btPigGrey.setEnabled(false);
            btFishGrey.setEnabled(false);
            btVeggieGrey.setEnabled(false);

            //set to #40000000 for label no & #00ffffff for label yes
            btChickenGrey.setBackgroundColor(Color.parseColor("#00ffffff"));

            fab = (FloatingActionButton) itemView.findViewById(R.id.floatingActionButton);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //cameraView.captureImage();
                    view.getContext().startActivity(inPhoto);
                }
            });

            itemDetail =
                    (TextView)itemView.findViewById(R.id.item_detail);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout_expandable, viewGroup, false);
    ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterExpandable.ViewHolder viewHolder, int position) {
        viewHolder.itemTitle.setText(titles[position]);
        viewHolder.itemDetail.setText(details[position]);
        viewHolder.itemImage.setImageResource(images[position]);
        //viewHolder.fab2.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}