package com.example.cantinr.cantinr;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
       // public FloatingActionButton fab2;

        public Intent intentDetail, inPhoto;
        //Intent inPhoto;

        public ViewHolder(View itemView) {
            super(itemView);

            intentDetail= new Intent(itemView.getContext(), cardDetail.class);
            itemImage = (ImageView)itemView.findViewById(R.id.item_image);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);

            inPhoto= new Intent(itemView.getContext(), PhotoActivity.class);

            /*
            fab2 = (FloatingActionButton) itemView.findViewById(R.id.fab2);
            fab2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //cameraView.captureImage();
                    view.getContext().startActivity(inPhoto);
                }
            });
            */
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