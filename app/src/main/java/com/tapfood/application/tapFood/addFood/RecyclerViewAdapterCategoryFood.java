package com.tapfood.application.tapFood.addFood;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sleepycat.je.tree.IN;
import com.topwise.tapfood.R;

import java.util.ArrayList;


public class RecyclerViewAdapterCategoryFood extends RecyclerView.Adapter<RecyclerViewAdapterCategoryFood.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapterCategory";

    //vars
    private ArrayList<String> name;

    private Context mContext;

    public RecyclerViewAdapterCategoryFood(Context context, ArrayList<String> names) {
        name = names;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_category, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(ViewHolder holder,  int position) {

        holder.line.setVisibility(View.GONE);
        holder.name.setText(name.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (holder.line.getVisibility()==View.INVISIBLE){
                    holder.line.setVisibility(View.VISIBLE);
                }
                else
                {
                    holder.line.setVisibility(View.INVISIBLE);
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        View line;


        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameRestaurant);
            line = itemView.findViewById(R.id.line);
        }
    }

}
