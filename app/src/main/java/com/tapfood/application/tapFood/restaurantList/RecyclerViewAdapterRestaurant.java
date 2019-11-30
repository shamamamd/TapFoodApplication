package com.tapfood.application.tapFood.restaurantList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.tapfood.application.tapFood.addFood.AddFoodActivity;

import com.topwise.tapfood.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapterRestaurant extends RecyclerView.Adapter<RecyclerViewAdapterRestaurant.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapterRestaurant";

    private List<RestaurantDataItem> restaurantDataItems;
    private Context mContext;

    public RecyclerViewAdapterRestaurant(Context context, List<RestaurantDataItem> restaurantDataItems) {
        this.restaurantDataItems = restaurantDataItems;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_restaurant, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        final RestaurantDataItem dataItem = restaurantDataItems.get(position);

        Glide.with(mContext)
                .asBitmap()
                .load(dataItem.getUrlImageRestaurant())
                .into(holder.urlImageRestaurant);

        holder.nameRestaurant.setText(dataItem.getNameRestaurant());
        holder.descriptionRestaurant.setText(dataItem.getDescriptionRestaurant());
        holder.rateRestaurant.setText(dataItem.getRateRestaurant());
        holder.saleRestaurant.setText(dataItem.getSaleRestaurant());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.getApplicationContext(), AddFoodActivity.class);
                intent.putExtra("DataRestaurant", dataItem);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantDataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView urlImageRestaurant;
        TextView nameRestaurant, descriptionRestaurant, saleRestaurant, rateRestaurant;


        public ViewHolder(View itemView) {
            super(itemView);
            urlImageRestaurant = itemView.findViewById(R.id.imageFood);
            nameRestaurant = itemView.findViewById(R.id.nameRestaurant);
            descriptionRestaurant = itemView.findViewById(R.id.descriptionFood);
            saleRestaurant = itemView.findViewById(R.id.saleRestaurant);
            rateRestaurant = itemView.findViewById(R.id.rateRestaurant);


        }
    }

}

