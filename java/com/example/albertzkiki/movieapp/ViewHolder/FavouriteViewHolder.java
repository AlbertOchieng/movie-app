package com.example.albertzkiki.movieapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.albertzkiki.movieapp.Interface.ItemClickListener;
import com.example.albertzkiki.movieapp.R;

/**
 * Created by albertzkiki on 5/6/2018.
 */

public class FavouriteViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView text_header;
    public ImageView image;
    private ItemClickListener itemClickListener;

    public RelativeLayout view_background,view_forward;


    public FavouriteViewHolder(View itemView) {
        super(itemView);


        text_header = (TextView)itemView.findViewById(R.id.movie_nameFAV);
        image = (ImageView) itemView.findViewById(R.id.movie_imageFAV);

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
