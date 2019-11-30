package com.tapfood.application.tapFood.restaurantList;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;

import com.tapfood.application.constant.Constant;
import com.tapfood.application.kioskMode.PrefUtils;
import com.tapfood.application.tapFood.addFood.AddFoodDataItem;
import com.topwise.tapfood.R;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListActivity extends AppCompatActivity  {


    public List<RestaurantDataItem> restaurantDataItems =new ArrayList<>();
    public RestaurantDataItem restaurantDataItem;
    public List<AddFoodDataItem> foodDataItem,foodDataItem2,foodDataItem3;
    public AddFoodDataItem addFoodDataItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_restaurant);
        getDataRestaurant();
    }

    private void getDataAddFood(){

        /////////////////////////////////////////
        foodDataItem=new ArrayList<>();
        addFoodDataItem =new AddFoodDataItem();
        addFoodDataItem.setNameFood("همبرگر");
        addFoodDataItem.setDescriptionFood("راسته گوساله، نان فرانسوی، خیارشور، گوجه فرنگی، پنیر چدار");
        addFoodDataItem.setUrlImageFood("http://hamdore.com/wp-content/uploads/2017/12/hamberger.jpg");
        addFoodDataItem.setRealPriceFood(Constant.convertEnToFa(Constant.formatPrice(350000)));
        addFoodDataItem.setSalePriceFood(String.valueOf(15000));
        addFoodDataItem.setSaleFood(Constant.convertEnToFa("22%"));
        foodDataItem.add(addFoodDataItem);

        addFoodDataItem =new AddFoodDataItem();
        addFoodDataItem.setNameFood("زغال برگر");
        addFoodDataItem.setDescriptionFood("همه چی داره");
        addFoodDataItem.setUrlImageFood("http://www.partfastfood.com/photos/part-coal-burger-l.jpg");
        addFoodDataItem.setRealPriceFood(Constant.convertEnToFa(Constant.formatPrice(70000)));
        addFoodDataItem.setSalePriceFood(String.valueOf(7000));
        addFoodDataItem.setSaleFood(Constant.convertEnToFa("85%"));
        foodDataItem.add(addFoodDataItem);
        addFoodDataItem =new AddFoodDataItem();
        addFoodDataItem.setNameFood("کباب");
        addFoodDataItem.setDescriptionFood("المانی");
        addFoodDataItem.setUrlImageFood("http://hamdore.com/wp-content/uploads/2017/12/hamberger.jpg");
        addFoodDataItem.setRealPriceFood(Constant.convertEnToFa(Constant.formatPrice(580000)));
        addFoodDataItem.setSalePriceFood(String.valueOf(40000));
        addFoodDataItem.setSaleFood(Constant.convertEnToFa("12%"));
        foodDataItem.add(addFoodDataItem);


/////////////////////////////////////////////////////////
        foodDataItem2=new ArrayList<>();
        addFoodDataItem =new AddFoodDataItem();
        addFoodDataItem.setNameFood("زغال برگر");
        addFoodDataItem.setDescriptionFood("همه چی داره");
        addFoodDataItem.setUrlImageFood("http://www.partfastfood.com/photos/part-coal-burger-l.jpg");
        addFoodDataItem.setRealPriceFood(Constant.convertEnToFa(Constant.formatPrice(70000)));
        addFoodDataItem.setSalePriceFood(String.valueOf(50000));

        addFoodDataItem.setSaleFood(Constant.convertEnToFa("85%"));
        foodDataItem2.add(addFoodDataItem);

        addFoodDataItem =new AddFoodDataItem();
        addFoodDataItem.setNameFood("کباب");
        addFoodDataItem.setDescriptionFood("المانی");
        addFoodDataItem.setUrlImageFood("http://hamdore.com/wp-content/uploads/2017/12/hamberger.jpg");
        addFoodDataItem.setRealPriceFood(Constant.convertEnToFa(Constant.formatPrice(580000)));
        addFoodDataItem.setSalePriceFood(String.valueOf(32000));
        addFoodDataItem.setSaleFood(Constant.convertEnToFa("12%"));
        foodDataItem2.add(addFoodDataItem);

//////////////////////////////////////////////////////
        foodDataItem3=new ArrayList<>();
        addFoodDataItem =new AddFoodDataItem();
        addFoodDataItem.setNameFood("کباب");
        addFoodDataItem.setDescriptionFood("المانی");
        addFoodDataItem.setUrlImageFood("http://hamdore.com/wp-content/uploads/2017/12/hamberger.jpg");
        addFoodDataItem.setRealPriceFood(Constant.convertEnToFa(Constant.formatPrice(580000)));
        addFoodDataItem.setSalePriceFood(String.valueOf(45000));
        addFoodDataItem.setSaleFood(Constant.convertEnToFa("12%"));
        foodDataItem3.add(addFoodDataItem);

    }
    private void getDataRestaurant(){
        getDataAddFood();

        restaurantDataItem =new RestaurantDataItem();
        restaurantDataItem.setNameRestaurant("بامیکا");
        restaurantDataItem.setDescriptionRestaurant("بامیکا");
        restaurantDataItem.setUrlImageRestaurant("https://images.unsplash.com/photo-1528137871618-79d2761e3fd5?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=750&q=80");
        restaurantDataItem.setRateRestaurant(Constant.convertEnToFa("3.2"));
        restaurantDataItem.setSaleRestaurant(Constant.convertEnToFa("78%"));
        restaurantDataItems.add(restaurantDataItem);
        restaurantDataItem.setAddFoodDataItems(foodDataItem);

        restaurantDataItem =new RestaurantDataItem();
        restaurantDataItem.setNameRestaurant("کافه ویونا");
        restaurantDataItem.setDescriptionRestaurant("قزوینی");
        restaurantDataItem.setUrlImageRestaurant("http://hamdore.com/wp-content/uploads/2017/12/hamberger.jpg");
        restaurantDataItem.setRateRestaurant(Constant.convertEnToFa("4.2"));
        restaurantDataItem.setSaleRestaurant(Constant.convertEnToFa("35%"));
        restaurantDataItems.add(restaurantDataItem);
        restaurantDataItem.setAddFoodDataItems(foodDataItem2);

        restaurantDataItem =new RestaurantDataItem();
        restaurantDataItem.setNameRestaurant("نایب");
        restaurantDataItem.setDescriptionRestaurant("رشتی");
        restaurantDataItem.setUrlImageRestaurant("http://www.partfastfood.com/photos/part-coal-burger-l.jpg");
        restaurantDataItem.setRateRestaurant(Constant.convertEnToFa("5"));
        restaurantDataItem.setSaleRestaurant(Constant.convertEnToFa("5%"));
        restaurantDataItems.add(restaurantDataItem);
        restaurantDataItem.setAddFoodDataItems(foodDataItem3);

        initRecyclerViewRestaurant();

    }
    private void initRecyclerViewRestaurant(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerViewBest = findViewById(R.id.recyclerViewShoppingList);
        recyclerViewBest.setLayoutManager(layoutManager);
        RecyclerViewAdapterRestaurant adapter = new RecyclerViewAdapterRestaurant(this, restaurantDataItems);
        recyclerViewBest.setAdapter(adapter);


    }



    //kioskMode and FullScreen
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        hideSystemUI();
        if(!hasFocus) {

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
