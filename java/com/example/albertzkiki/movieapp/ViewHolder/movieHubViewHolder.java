package com.example.albertzkiki.movieapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.albertzkiki.movieapp.Interface.ItemClickListener;
import com.example.albertzkiki.movieapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by albertzkiki on 5/14/2018.
 */

public class movieHubViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView movietitle,userbooked,totalPrice,seats_picked,timebooked,datebooked;

    List<Integer> seatsTaken = new ArrayList<Integer>();

    private ItemClickListener itemClickListener;

    public movieHubViewHolder(View itemView) {
        super(itemView);


        movietitle = (TextView)itemView.findViewById(R.id.movieNameBookedMH);
        userbooked = (TextView)itemView.findViewById(R.id.phoneNumberBookedMH);
        totalPrice = (TextView)itemView.findViewById(R.id.AmountBookedMH);
        seats_picked = (TextView)itemView.findViewById(R.id.seatsBookedMH);
        timebooked = (TextView)itemView.findViewById(R.id.TimeBookedMH);
        datebooked = (TextView)itemView.findViewById(R.id.DateBookedMH);

        itemView.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}
