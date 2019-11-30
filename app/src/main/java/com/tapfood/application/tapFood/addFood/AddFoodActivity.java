package com.tapfood.application.tapFood.addFood;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tapfood.application.constant.Constant;
import com.tapfood.application.kioskMode.PrefUtils;
import com.tapfood.application.tapFood.restaurantList.RestaurantDataItem;
import com.tapfood.application.tapFood.restaurantList.RestaurantListActivity;
import com.tapfood.application.tapFood.shoppingList.ShoppingListActivity;
import com.topwise.tapfood.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddFoodActivity extends AppCompatActivity {

    public ImageView backPage, imageRestaurant;
    public TextView ShowCountingFoodsBottom, nameRestaurant, totalPriceBottom;
    public LinearLayout goShoppingList;

    private List<AddFoodDataItem> addFoodDataItems = new ArrayList<>();


    static List<AddFoodDataItem> selectedFood = new ArrayList<>();

    private ArrayList<String> searchBy = new ArrayList<>();

    public static int countingFoodBottom = 0;
    public static int totalAllPriceAllFoods;
    int price, totalPriceEachFood;
    boolean openedLayoutBottom;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        if (savedInstanceState != null) {
            Log.i("sha","hhhhhh");
            int s = savedInstanceState.getInt("pp");
            Log.i("shaRRRRR", s + "");
        }


        nameRestaurant = findViewById(R.id.nameRestaurant);
        imageRestaurant = findViewById(R.id.imageFood);
        ShowCountingFoodsBottom = findViewById(R.id.count_food);
        goShoppingList = findViewById(R.id.goShoppingList);
        backPage = findViewById(R.id.backPage);
        totalPriceBottom = findViewById(R.id.totalprice);


        Intent intent = getIntent();
        RestaurantDataItem restaurantDataItem = (RestaurantDataItem) intent.getSerializableExtra("DataRestaurant");
        nameRestaurant.setText(restaurantDataItem.getNameRestaurant());
        Glide.with(getApplicationContext()).load(restaurantDataItem.urlImageRestaurant).into(imageRestaurant);
        addFoodDataItems = restaurantDataItem.getAddFoodDataItems();


        backPage();
        goShoppingList();
        category();
        initRecyclerViewAddFood();


    }



    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("sha", "Main activity onRestart.");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d("sha", "Main activity onStop.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("sha", "Main activity onDestroy.");
    }


    private void goShoppingList() {
        goShoppingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddFoodActivity.this, ShoppingListActivity.class);
                intent.putExtra("SelectedFood", (Serializable) selectedFood);
                intent.putExtra("TotalPrice", totalAllPriceAllFoods + "");
                startActivity(intent);
            }
        });

    }

    private void backPage() {
        backPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddFoodActivity.this, RestaurantListActivity.class);
                intent.putExtra("totalPrice", totalAllPriceAllFoods);
                intent.putExtra("countingFood", countingFoodBottom);
                finish();
            }
        });

    }

    private void category() {
        searchBy.add("همه غذاها");
        searchBy.add("دریایی");
        searchBy.add("برگر");
        searchBy.add("ملل");
        initRecyclerViewCategory();

    }

    private void initRecyclerViewCategory() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        layoutManager.setReverseLayout(true);
        RecyclerView recyclerViewCategory = findViewById(R.id.recyclerViewSearchByMenu);
        recyclerViewCategory.setLayoutManager(layoutManager);
        RecyclerViewAdapterCategoryFood adapter = new RecyclerViewAdapterCategoryFood(this, searchBy);
        recyclerViewCategory.setAdapter(adapter);

    }

    private void initRecyclerViewAddFood() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerViewAddFood = findViewById(R.id.menu);
        recyclerViewAddFood.setLayoutManager(layoutManager);
        final RecyclerViewAdapterAddFood adapter = new RecyclerViewAdapterAddFood(this, addFoodDataItems, new ClickHandler() {
            @Override
            public void handler(RecyclerViewAdapterAddFood.ViewHolder holder, View view, AddFoodDataItem dataItem) {
                countingFoodBottom++;
                price = Integer.parseInt(dataItem.getSalePriceFood());
                holder.numberFoodCalculatePrice++;
                holder.numberFoodShowOnRecycleItem++;
                dataItem.setNumberFood(holder.numberFoodShowOnRecycleItem);
                if (holder.numberFoodShowOnRecycleItem <= 0) {
                    holder.numberFoodShowOnRecycleItem = 1;
                }

                if (holder.numberFoodShowOnRecycleItem == 1) {
                    selectedFood.remove(dataItem);
                    selectedFood.add(dataItem);

                }
                if (holder.numberFoodCalculatePrice > 0) {
                    holder.countingFoodTextViewRecycleView.setText(Constant.convertEnToFa(holder.numberFoodShowOnRecycleItem + ""));
                    totalPriceEachFood = price * holder.numberFoodCalculatePrice;
                    holder.numberFoodCalculatePrice = 0;

                }
                totalAllPriceAllFoods += totalPriceEachFood;
                if (countingFoodBottom <= 0) {
                    countingFoodBottom = 1;
                }
                if (!openedLayoutBottom) {
                    goShoppingList.setVisibility(View.VISIBLE);
                    TranslateAnimation animate = new TranslateAnimation(
                            0,
                            0,
                            view.getHeight(),
                            0);
                    animate.setDuration(500);
                    animate.setFillAfter(true);
                    goShoppingList.startAnimation(animate);
                    ShowCountingFoodsBottom.setText("(" + Constant.convertEnToFa(String.valueOf(countingFoodBottom)) + ")");
                    totalPriceBottom.setText(Constant.convertEnToFa(Constant.formatPrice(totalAllPriceAllFoods)));
                }
                if (countingFoodBottom > 0) {
                    TranslateAnimation animate = new TranslateAnimation(
                            0,
                            0,
                            view.getHeight(),
                            0);
                    animate.setDuration(500);
                    animate.setFillAfter(true);
                    goShoppingList.startAnimation(animate);
                    ShowCountingFoodsBottom.setText("(" + Constant.convertEnToFa(String.valueOf(countingFoodBottom)) + ")");
                    totalPriceBottom.setText(Constant.convertEnToFa(Constant.formatPrice(totalAllPriceAllFoods)));
                    openedLayoutBottom = !openedLayoutBottom;
                }
                openedLayoutBottom = !openedLayoutBottom;
            }
        }, new ClickHandler() {
            @Override
            public void handler(RecyclerViewAdapterAddFood.ViewHolder holder, View view, AddFoodDataItem dataItem) {
                countingFoodBottom--;
                price = Integer.parseInt(dataItem.getSalePriceFood());
                holder.numberFoodCalculatePrice--;
                holder.numberFoodShowOnRecycleItem--;
                dataItem.setNumberFood(holder.numberFoodShowOnRecycleItem);

                if (holder.numberFoodShowOnRecycleItem <= 0) {
                    holder.numberFoodShowOnRecycleItem = 0;

                }
                holder.countingFoodTextViewRecycleView.setText(Constant.convertEnToFa(holder.numberFoodShowOnRecycleItem + ""));
                totalPriceEachFood = price * holder.numberFoodCalculatePrice;
                holder.numberFoodCalculatePrice = 0;
                totalAllPriceAllFoods += totalPriceEachFood;

                if (countingFoodBottom < 1) {
                    totalAllPriceAllFoods = 0;
                    goShoppingList.setVisibility(View.INVISIBLE);
                    TranslateAnimation animate = new TranslateAnimation(
                            0,
                            0,
                            0,
                            goShoppingList.getHeight());
                    animate.setDuration(500);
                    animate.setFillAfter(true);
                    goShoppingList.startAnimation(animate);
                    ShowCountingFoodsBottom.setText("(" + Constant.convertEnToFa("0" + ")"));
                    totalPriceBottom.setText(Constant.convertEnToFa(Constant.formatPrice(totalAllPriceAllFoods)));
                } else {
                    TranslateAnimation animate = new TranslateAnimation(
                            0,
                            0,
                            view.getHeight(),
                            0);
                    animate.setDuration(500);
                    animate.setFillAfter(true);
                    goShoppingList.startAnimation(animate);
                    ShowCountingFoodsBottom.setText("(" + Constant.convertEnToFa(String.valueOf(countingFoodBottom)) + ")");
                    totalPriceBottom.setText(Constant.convertEnToFa(Constant.formatPrice(totalAllPriceAllFoods)));
                    openedLayoutBottom = !openedLayoutBottom;
                }


            }
        });

        recyclerViewAddFood.setAdapter(adapter);

    }


    //Full screen and kiosk mode
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        hideSystemUI();
        if (!hasFocus) {

            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (PrefUtils.blockedKeys.contains(event.getKeyCode())) {
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        //Disable Button (backPage home active app button)

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.moveTaskToFront(getTaskId(), 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }


}
