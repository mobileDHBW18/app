package com.example.cantinr.cantinr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by D064040 on 14.06.2018.
 */

public class RecyclerAdapterExpandable extends RecyclerView.Adapter<RecyclerAdapterExpandable.ViewHolder> {

    private String[] titles = {"Hähnchenbrust mit"};
    private String[] details = {"Kartoffelstampf und Bratensoße, enthält Gluten und Schalenfrüchte"};

    private String apiUrl = "https://api.cantinr.de/v1";

    private int[] images = {R.drawable.img1};

    private JSONObject data;

    static ArrayList<Bitmap> networkImages = new ArrayList<>();
    private int pos;
    private Context context;
    private Float saved;


    class ViewHolder extends RecyclerView.ViewHolder {

        public int currentItem;
        public ImageView itemImage;
        public TextView itemTitle;
        public TextView itemDetail;
        public TextView itemPrice;
        public TextView itemRating;
        public TextView ratingsTotal;
        // public FloatingActionButton fab2;
        public FloatingActionButton fab;
        public RatingBar ratingBar;
        private SharedPreferences prefs;

        public Intent intentDetail, inPhoto, inGallery;
        //Intent inPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            intentDetail= new Intent(itemView.getContext(), cardDetail.class);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            inPhoto= new Intent(itemView.getContext(), PhotoActivity.class);
            inGallery= new Intent(itemView.getContext(), galleryActivity.class);
            itemImage = (ImageView)itemView.findViewById(R.id.item_image);
            ratingsTotal = (TextView) itemView.findViewById(R.id.txtTotalRatings);
            itemRating = (TextView) itemView.findViewById(R.id.txtRating);
            itemPrice = (TextView) itemView.findViewById(R.id.txtPreis);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar4);
            context = itemView.getContext();
            prefs = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE);

            try {
                saved = prefs.getFloat(data.getString("id"), -1.0f);
                Log.d("SAVED", saved.toString());
                if (saved != -1.0f) {
                    ratingBar.setRating(saved);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

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
                    try {
                        inPhoto.putExtra("id", data.getString("id"));
                        view.getContext().startActivity(inPhoto);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            itemDetail =
                    (TextView) itemView.findViewById(R.id.item_detail);

            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                                        // make Call to API and send rating
                    try {
                        saved = prefs.getFloat(data.getString("id"), -1);
                        if (saved == -1.0f) {
                            // not voted yet, send vote and save value
                            String uri = String.format(apiUrl + "/meals/%1$s", data.getString("id"));
                            JSONObject js = new JSONObject();
                            js.put("rating", v);
                            System.out.println(js.toString());
                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, uri, js,
                                    new Response.Listener<JSONObject>()
                                    {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            System.out.println(response.toString());
                                            data = response;
                                            notifyDataSetChanged();
                                        }
                                    },
                                    new Response.ErrorListener()
                                    {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                        }
                                    }
                            );
                            MainActivity.queue.add(jsonObjectRequest);
                            saved = v;
                            SharedPreferences.Editor prefEditor = prefs.edit();
                            prefEditor.putFloat(data.getString("id"), v);
                            prefEditor.commit();
                        } else {
                            // already voted, say nay
                            Toast.makeText(context, "Du hast dieses Gericht schon bewertet!",
                                    Toast.LENGTH_LONG).show();
                            ratingBar.setRating(saved);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
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
        try {
            viewHolder.itemTitle.setText(data.getString("name"));
            //viewHolder.itemTitle.setText(titles[position]);
            JSONArray arr = data.getJSONArray("ingrediences");
            String details = "";
            for (int i = 0; i < arr.length(); i++) {
                details += arr.getString(i);
                if (i != arr.length() - 1) {
                    details += ", ";
                }
            }
            viewHolder.itemDetail.setText(details);
            viewHolder.itemPrice.setText(String.format("%.2f", data.getDouble("price")) + "€"); // String.valueOf(data.getDouble("price")) + "€");
            //viewHolder.itemImage.setImageResource(images[position]);
            viewHolder.itemImage.setImageBitmap(networkImages.get(0));
            viewHolder.itemRating.setText(String.format("%.1f", data.getDouble("rating")));
            Double ratingsCount = data.getDouble("ratingsNum");
            viewHolder.ratingsTotal.setText(String.valueOf(ratingsCount.intValue()) + " Bewertungen");
            //viewHolder.fab2.setVisibility(View.VISIBLE);

            // show the icons depending on the values present
//            Iterator<?> keys = data.getJSONObject("categories").keys();
//
//            while (keys.hasNext()) {
//
//            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public void setData(JSONObject mData, ArrayList<Bitmap> mNetworkImages) {
        data = mData;
        networkImages = mNetworkImages;
    }

    public void setPosition(int mPos) {
        pos = mPos;
    }
}