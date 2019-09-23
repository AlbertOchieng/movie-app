package com.example.albertzkiki.movieapp.ViewHolder;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.albertzkiki.movieapp.Interface.ItemClickListener;
import com.example.albertzkiki.movieapp.R;

/**
 * Created by albertzkiki on 4/15/2018.
 */

public class FavMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    //public FloatingActionButton btnFav;
    private ItemClickListener itemClickListener;

    public RelativeLayout view_background,view_forward;


    public FavMovieViewHolder(View itemView) {
        super(itemView);

        //btnFav = (FloatingActionButton)itemView.findViewById(R.id.btnfloat);

        view_background = (RelativeLayout) itemView.findViewById(R.id.view_backgroundFav);
        view_forward = (RelativeLayout) itemView.findViewById(R.id.view_forwardFav);

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
