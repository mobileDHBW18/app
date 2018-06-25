package com.example.cantinr.cantinr;

import android.content.Intent;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by D064040 on 14.06.2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private String apiUrl;
    private Context context;
    private String currentDate;

    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> details = new ArrayList<>(); //{"Kartoffelstampf und Bratensoße, enthält Gluten und Schalenfrüchte", "Röstgemüse und Bratensoße, enthält Erdnüsse", "American Style, vegan und laktosefrei"};
    private ArrayList<Integer> images = new ArrayList<>(); //{ R.drawable.img1,//R.drawable.ic_launcher_background, R.drawable.img2, R.drawable.ic_launcher_background};

    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public ImageView itemImage;
        public TextView itemTitle;
        public TextView itemDetail;

        Intent intentDetail;

        public ViewHolder(final View itemView) {
            super(itemView);

            intentDetail= new Intent(itemView.getContext(), cardDetail.class);
            itemImage = (ImageView)itemView.findViewById(R.id.item_image);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            itemDetail =
                    (TextView)itemView.findViewById(R.id.item_detail);

            context = itemView.getContext();
            currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String uri = null;
            uri = String.format(apiUrl + "/meals?date=%1$s&mensa=%2$s", "2018-06-20", "DHBW Mannheim Mensaria Metropol".replace(" ", "%20"));
            System.out.println(uri);

            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray array = new JSONArray(response);
                                System.out.println("RESPONSE IS: ");
                                System.out.println(response);
                                for (int i = 0; i < array.length(); i++){
                                    JSONObject obj = array.getJSONObject(i);
                                    JSONArray ingrediences = obj.getJSONArray("ingrediences");
                                    String name = ingrediences.get(0).toString();
                                    titles.add(name);
                                    System.out.println(name);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    /*
                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    */
                    intentDetail.putExtra("position",position);
                    itemView.getContext().startActivity(intentDetail);
                }
            });

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.itemTitle.setText(titles.get(i));
        viewHolder.itemDetail.setText(details.get(i));
        viewHolder.itemImage.setImageResource(images.get(i));
    }

    public void setDataSet(ArrayList<String> mTitles, ArrayList<String> mDetails, ArrayList<Integer> mImages) {
        titles = mTitles;
        details = mDetails;
        images = mImages;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }
}