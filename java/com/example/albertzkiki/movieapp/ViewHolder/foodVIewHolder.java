package com.example.albertzkiki.movieapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albertzkiki.movieapp.Interface.ItemClickListener;
import com.example.albertzkiki.movieapp.R;
import com.ramotion.foldingcell.FoldingCell;

/**
 * Created by albertzkiki on 4/4/2018.
 */

public class foodVIewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView text_header;
    public ImageView image,fav_image;

    private ItemClickListener itemClickListener;

    public foodVIewHolder(View itemView) {
        super(itemView);



        text_header = (TextView)itemView.findViewById(R.id.food_name);
        image = (ImageView) itemView.findViewById(R.id.food_image);
        fav_image = (ImageView) itemView.findViewById(R.id.fav_food_border);


        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition(),false);

    }



}
