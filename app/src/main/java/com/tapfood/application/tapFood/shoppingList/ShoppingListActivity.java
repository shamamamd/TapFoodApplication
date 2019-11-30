package com.tapfood.application.tapFood.shoppingList;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tapfood.application.constant.Constant;
import com.tapfood.application.device.card.SwipeCard;
import com.tapfood.application.kioskMode.PrefUtils;
import com.tapfood.application.tapFood.addFood.AddFoodDataItem;
import com.topwise.tapfood.R;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {


    public ImageView backPage, plusButton, minusButton, trash, sheet;
    public TextView count, totalPriceFood, totalPriceFoodBottom, revenue, sale;
    public LinearLayout payment;
    public BottomSheetBehavior mBottomSheetBehavior;
    public View bottomSheet;

    public static List<AddFoodDataItem> selectedFood = new ArrayList<>();

    public String totalPrice;

    int price, totalPriceEachFood;
    public static int totalPriceAllFoods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shaping_list);

        getDataFromPreviousActivity();
        initialize();
        showItemOnBottomSheet(totalPriceAllFoods);
        bottomSheetClick();
        paymentClick();
        trashClick();
        backClick();
        initRecyclerViewShoppingList();

    }

    private void getDataFromPreviousActivity() {
        Intent intent = getIntent();
        selectedFood = (List<AddFoodDataItem>) intent.getSerializableExtra("SelectedFood");
        totalPrice = intent.getStringExtra("TotalPrice");
        totalPriceAllFoods = Integer.parseInt(totalPrice);

    }

    private String revenue(String s) {

        s = String.valueOf(Integer.parseInt(String.valueOf(totalPriceAllFoods)) / 10);
        return s;
    }

    private String sale(String s) {
        s = String.valueOf((Integer.parseInt(String.valueOf(totalPriceAllFoods)) / 100) * 9);
        return s;
    }

    private void bottomSheet() {
        bottomSheet = findViewById(R.id.bottom_sheet);
        sheet = bottomSheet.findViewById(R.id.sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mBottomSheetBehavior.setPeekHeight(65);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:

                        break;
                    case BottomSheetBehavior.STATE_SETTLING:

                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

    private void showItemOnBottomSheet(int total) {
        totalPriceFood.setText(Constant.convertEnToFa(Constant.formatPrice(total)));
        totalPriceFoodBottom.setText(Constant.convertEnToFa(Constant.formatPrice(total)));
        revenue.setText(Constant.convertEnToFa(Constant.formatPrice(Integer.parseInt(revenue(String.valueOf(total))))));
        sale.setText(Constant.convertEnToFa(Constant.formatPrice(Integer.parseInt(sale(String.valueOf(total))))));
    }

    private void initialize() {
        bottomSheet();
        trash = findViewById(R.id.trash);
        payment = findViewById(R.id.buy);
        totalPriceFood = bottomSheet.findViewById(R.id.totalPriceFood);
        totalPriceFoodBottom = bottomSheet.findViewById(R.id.totalPriceFoodBottom);
        revenue = bottomSheet.findViewById(R.id.revenue);
        sale = bottomSheet.findViewById(R.id.sale);
        backPage = findViewById(R.id.backPage);
        count = findViewById(R.id.countt);
        plusButton = findViewById(R.id.plus_button);
        minusButton = findViewById(R.id.mines_button);
    }

    private void backClick() {
        backPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void trashClick() {
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingListActivity.this);
                ViewGroup viewGroup = findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_delete_food, viewGroup, false);
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                TextView cancel = dialogView.findViewById(R.id.cancel);
                TextView confirm = dialogView.findViewById(R.id.confirm);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ShoppingListActivity.this, "لغو", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                });
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShoppingListActivity.selectedFood=null;
                        ShoppingListActivity.totalPriceAllFoods=0;
                        finish();
                    }
                });
                alertDialog.show();


            }
        });

    }

    private void paymentClick() {
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SwipeCard.class);
                startActivity(intent);
            }
        });

    }

    private void bottomSheetClick() {

        sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    sheet.setImageDrawable(getResources().getDrawable(R.drawable.group_111));

                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    //else if state is expanded collapse it
                    sheet.setImageDrawable(getResources().getDrawable(R.drawable.group_11));
                }
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            }
        });
    }

    private void initRecyclerViewShoppingList() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewShoppingList);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapterShoppingList adapter = new RecyclerViewAdapterShoppingList(this, selectedFood, new ClickHandler() {
            @Override
            public void handler(RecyclerViewAdapterShoppingList.ViewHolder holder, View view, AddFoodDataItem dataItem) {

                price = Integer.parseInt(dataItem.getSalePriceFood());
                holder.numberFoodCalculatePrice++;
                holder.numberFoodShowOnRecycleItem++;
                holder.numberFood.setText(Constant.convertEnToFa(holder.numberFoodShowOnRecycleItem + ""));
                if (holder.numberFoodCalculatePrice > 0) {
                    totalPriceEachFood = price * holder.numberFoodCalculatePrice;
                    holder.numberFoodCalculatePrice = 0;
                }

                totalPriceAllFoods += totalPriceEachFood;
                showItemOnBottomSheet(totalPriceAllFoods);
            }
        }, new com.tapfood.application.tapFood.shoppingList.ClickHandler() {
            @Override
            public void handler(RecyclerViewAdapterShoppingList.ViewHolder holder, View view, AddFoodDataItem dataItem) {

                price = Integer.parseInt(dataItem.getSalePriceFood());
                holder.numberFoodCalculatePrice--;
                holder.numberFoodShowOnRecycleItem--;
                if (holder.numberFoodShowOnRecycleItem < 0) {
                    holder.numberFoodShowOnRecycleItem = 0;
                    holder.numberFoodCalculatePrice = 0;
                }

                holder.numberFood.setText(Constant.convertEnToFa(holder.numberFoodShowOnRecycleItem + ""));
                totalPriceEachFood = price * holder.numberFoodCalculatePrice;
                holder.numberFoodCalculatePrice = 0;
                totalPriceAllFoods += totalPriceEachFood;
                showItemOnBottomSheet(totalPriceAllFoods);

            }
        });

        recyclerView.setAdapter(adapter);


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
