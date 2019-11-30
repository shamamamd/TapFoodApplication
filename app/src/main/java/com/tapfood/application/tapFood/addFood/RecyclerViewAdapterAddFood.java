package com.tapfood.application.tapFood.addFood;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tapfood.application.constant.Constant;
import com.topwise.tapfood.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapterAddFood extends RecyclerView.Adapter<RecyclerViewAdapterAddFood.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapterAddFood";
    private ClickHandler minusButtonClickHandler;
    private ClickHandler plusButtonClickHandler;
    private List<AddFoodDataItem> addFoodDataItems;
    private Context mContext;

    public RecyclerViewAdapterAddFood(Context context, List<AddFoodDataItem> addFoodDataItems,
                                      ClickHandler plusButtonClickHandler, ClickHandler minusButtonClickHandler) {
        this.addFoodDataItems = addFoodDataItems;
        this.mContext = context;
        this.plusButtonClickHandler = plusButtonClickHandler;
        this.minusButtonClickHandler = minusButtonClickHandler;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_menu_food, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        final AddFoodDataItem dataItem = addFoodDataItems.get(position);


            Glide.with(mContext)
                    .asBitmap()
                    .load(dataItem.getUrlImageFood())
                    .into(holder.imageFood);
            holder.nameFood.setText(dataItem.getNameFood());
            holder.descriptionFood.setText(dataItem.getDescriptionFood());
            holder.realPriceFood.setText(dataItem.getRealPriceFood());
            holder.salePriceFood.setText(Constant.convertEnToFa(Constant.formatPrice(Integer.parseInt(dataItem.salePriceFood))));
            holder.saleFood.setText(dataItem.getSaleFood());
            holder.minusButton.setVisibility(View.GONE);
            holder.countingFoodTextViewRecycleView.setVisibility(View.GONE);
            holder.plusButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    holder.minusButton.setVisibility(View.VISIBLE);
                    holder.countingFoodTextViewRecycleView.setVisibility(View.VISIBLE);
                    plusButtonClickHandler.handler(holder, v, dataItem);

                }
            });
            holder.minusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.numberFoodShowOnRecycleItem<1){
                        holder.minusButton.setVisibility(View.GONE);
                        holder.countingFoodTextViewRecycleView.setVisibility(View.GONE);
                    }
                    else {
                        minusButtonClickHandler.handler(holder, v, dataItem);
                    }



                }
            });
        }




    @Override
    public int getItemCount() {

        return addFoodDataItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imageFood;
        TextView nameFood, descriptionFood, realPriceFood, countingFoodTextViewRecycleView, salePriceFood, saleFood;
        ImageView plusButton, minusButton;
        int numberFoodCalculatePrice = 0;
        int numberFoodShowOnRecycleItem = 0;


        public ViewHolder(View itemView) {
            super(itemView);
            imageFood = itemView.findViewById(R.id.imageFood);
            nameFood = itemView.findViewById(R.id.nameFood);
            descriptionFood = itemView.findViewById(R.id.descriptionFood);
            realPriceFood = itemView.findViewById(R.id.realPriceFood);
            salePriceFood = itemView.findViewById(R.id.salePriceFood);
            saleFood = itemView.findViewById(R.id.saleFood);
            countingFoodTextViewRecycleView = itemView.findViewById(R.id.countt);
            plusButton = itemView.findViewById(R.id.plus_button);
            minusButton = itemView.findViewById(R.id.mines_button);
        }
    }

}

