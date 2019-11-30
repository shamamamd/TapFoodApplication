package com.tapfood.application.tapFood.shoppingList;

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
import com.tapfood.application.tapFood.addFood.AddFoodDataItem;
import com.topwise.tapfood.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapterShoppingList extends RecyclerView.Adapter<RecyclerViewAdapterShoppingList.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapterShoppingList";
    private Context mContext;
    private List<AddFoodDataItem> selectedFoods;
    private ClickHandler minusButtonClickHandler;
    private ClickHandler plusButtonClickHandler;

    public RecyclerViewAdapterShoppingList(Context context, List<AddFoodDataItem> selectedFoods,
                                           ClickHandler plusButtonClickHandler, ClickHandler minusButtonClickHandler) {
        this.selectedFoods = selectedFoods;
        this.plusButtonClickHandler = plusButtonClickHandler;
        this.minusButtonClickHandler = minusButtonClickHandler;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_shopping_list, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        final AddFoodDataItem dataItem = selectedFoods.get(position);


        Glide.with(mContext)
                .asBitmap()
                .load(dataItem.getUrlImageFood())
                .into(holder.urlImageFood);
        holder.nameFood.setText(dataItem.getNameFood());
        holder.descriptionFood.setText(dataItem.getDescriptionFood());
        holder.realPriceFood.setText(dataItem.getRealPriceFood());
        holder.salePriceFood.setText(Constant.convertEnToFa(Constant.formatPrice(Integer.parseInt(dataItem.salePriceFood))));
        holder.plusButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                plusButtonClickHandler.handler(holder, v, dataItem);
            }
        });
        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.numberFoodShowOnRecycleItem<=0){
                    selectedFoods.remove(position);
                    notifyItemRemoved(position);
                    notifyItemChanged(position, selectedFoods.size());
                }

                minusButtonClickHandler.handler(holder, v, dataItem);
            }
        });
        holder.numberFoodShowOnRecycleItem = dataItem.getNumberFood();
        holder.numberFood.setText(Constant.convertEnToFa(holder.numberFoodShowOnRecycleItem + ""));


    }


    @Override
    public int getItemCount() {
        return selectedFoods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView urlImageFood;
        TextView nameFood, descriptionFood, realPriceFood, salePriceFood, numberFood;
        ImageView plusButton, minusButton;
        int numberFoodCalculatePrice = 0;
        int numberFoodShowOnRecycleItem;

        public ViewHolder(View itemView) {
            super(itemView);
            urlImageFood = itemView.findViewById(R.id.imageFood);
            nameFood = itemView.findViewById(R.id.nameRestaurant);
            descriptionFood = itemView.findViewById(R.id.descriptionFood);
            realPriceFood = itemView.findViewById(R.id.realPrice);
            salePriceFood = itemView.findViewById(R.id.salePrice);
            numberFood = itemView.findViewById(R.id.countt);
            plusButton = itemView.findViewById(R.id.plus_button);
            minusButton = itemView.findViewById(R.id.mines_button);
        }
    }

}

