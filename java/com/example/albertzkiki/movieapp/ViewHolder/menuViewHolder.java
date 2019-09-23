package com.example.albertzkiki.movieapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albertzkiki.movieapp.Interface.ItemClickListener;
import com.example.albertzkiki.movieapp.R;

/**
 * Created by albertzkiki on 3/24/2018.
 */

public class menuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView text_header;
    public ImageView image,fav_image;

    private ItemClickListener itemClickListener;

    public menuViewHolder(View itemView) {
        super(itemView);

        text_header = (TextView)itemView.findViewById(R.id.movie_name);
        image = (ImageView) itemView.findViewById(R.id.movie_image);
        fav_image = (ImageView) itemView.findViewById(R.id.fav_movie_border);

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
