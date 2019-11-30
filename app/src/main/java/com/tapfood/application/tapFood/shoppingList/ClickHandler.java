package com.tapfood.application.tapFood.shoppingList;

import android.view.View;

import com.tapfood.application.tapFood.addFood.AddFoodDataItem;


public interface ClickHandler {
    void handler(RecyclerViewAdapterShoppingList.ViewHolder holder, View view, AddFoodDataItem dataItem);
}
