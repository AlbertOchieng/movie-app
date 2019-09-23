package com.example.albertzkiki.movieapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.albertzkiki.movieapp.Common.Common;
import com.example.albertzkiki.movieapp.Interface.ItemClickListener;
import com.example.albertzkiki.movieapp.ViewHolder.movieHubViewHolder;
import com.example.albertzkiki.movieapp.ViewHolder.orderViewHolder;
import com.example.albertzkiki.movieapp.model.Request;
import com.example.albertzkiki.movieapp.model.bookingModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieHub extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference Booking,SeatsBooked;

    String MovieName="";
    String Date="";
    String Time="";
    String Age="";
    String MoviePrice="";

    FirebaseRecyclerAdapter<bookingModel,movieHubViewHolder> adapter;


    public MovieHub() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_hub, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_movieHub);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance();
        SeatsBooked = database.getReference("BookedAndReady");



        loadAlreadyBookedMovies(Common.current_user.getPhone());




        return view;
    }

    private void loadAlreadyBookedMovies(String phone) {


        adapter = new FirebaseRecyclerAdapter<bookingModel, movieHubViewHolder>
                (
                        bookingModel.class,
                        R.layout.moviehub_item,
                        movieHubViewHolder.class,
                        SeatsBooked.orderByChild("phone").equalTo(phone)
                ) {
            @Override
            protected void populateViewHolder(movieHubViewHolder viewHolder, bookingModel model, int position) {

                viewHolder.userbooked.setText(model.getPhone());
                viewHolder.movietitle.setText(model.getName());
                viewHolder.datebooked.setText(model.getDate());
                viewHolder.timebooked.setText(model.getTime());
                viewHolder.totalPrice.setText(model.getPrice());
                viewHolder.seats_picked.setText(String.valueOf(model.getSeatsnumber()));


//                viewHolder.setItemClickListener(new ItemClickListener() {
//                    @Override
//                    public void onClick(View view, int position, boolean islongclick) {
//
//                        Intent orderDetails = new Intent(getActivity(), OrderDetails.class);
//                        Common.currentRequest = model;
//                        orderDetails.putExtra("OrderId",adapter.getRef(position).getKey());
//                        startActivity(orderDetails);
//
//                    }
//                });

            }


        };

//        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);





    }

}
