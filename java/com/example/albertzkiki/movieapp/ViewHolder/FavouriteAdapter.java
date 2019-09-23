package com.example.albertzkiki.movieapp.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.albertzkiki.movieapp.Common.Common;
import com.example.albertzkiki.movieapp.FoodDetails;
import com.example.albertzkiki.movieapp.Interface.ItemClickListener;
import com.example.albertzkiki.movieapp.Movie_Details;
import com.example.albertzkiki.movieapp.R;
import com.example.albertzkiki.movieapp.model.Category;
import com.example.albertzkiki.movieapp.model.FavouriteMovie;
import com.example.albertzkiki.movieapp.model.Food_model;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by albertzkiki on 5/6/2018.
 */

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteViewHolder> {

    private Context context;
    private List<FavouriteMovie> favouriteList;


    public FavouriteAdapter(Context context, List<FavouriteMovie> favouriteList) {
        this.context = context;
        this.favouriteList = favouriteList;
    }

    @Override
    public FavouriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).
                inflate(R.layout.favourite_item,parent,false);

        return new FavouriteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FavouriteViewHolder viewHolder, int position) {


        viewHolder.text_header.setText(favouriteList.get(position).getName());

        Picasso.with(context).load(favouriteList.get(position).getImage())
                .into(viewHolder.image);


        final FavouriteMovie local = favouriteList.get(position);

        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean islongclick) {

                Intent moviedetails = new Intent(context, Movie_Details.class);
                moviedetails.putExtra("CategoryId", favouriteList.get(position).getId());
                moviedetails.putExtra("MovieName", favouriteList.get(position).getName());

                context.startActivity(moviedetails);

            }
        });


    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }


    public void removeItem(int postion){

        favouriteList.remove(postion);
        notifyItemRemoved(postion);

    }


    public void restoreItem(FavouriteMovie favmovie,int postion){

        favouriteList.add(postion,favmovie);
        notifyItemInserted(postion);

    }

    public FavouriteMovie getItem(int position){

        return favouriteList.get(position);
    }



}
