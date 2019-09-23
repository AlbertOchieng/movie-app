package com.example.albertzkiki.movieapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.albertzkiki.movieapp.Common.Common;
import com.example.albertzkiki.movieapp.Database.Database;
import com.example.albertzkiki.movieapp.Interface.ItemClickListener;
import com.example.albertzkiki.movieapp.ViewHolder.foodVIewHolder;
import com.example.albertzkiki.movieapp.ViewHolder.menuViewHolder;
import com.example.albertzkiki.movieapp.model.Category;
import com.example.albertzkiki.movieapp.model.Food_model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class Food extends Fragment {


    FirebaseDatabase database;
    DatabaseReference food;

    RecyclerView recyclerView_food;
    RecyclerView.LayoutManager layoutManager;

    FloatingActionButton checkOut;


    FirebaseRecyclerAdapter<Food_model, foodVIewHolder> adapter;

    Database localDB;


    public Food() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_food, container, false);

        //initialize firebase

        database = FirebaseDatabase.getInstance();
        food = database.getReference("Food");

        localDB = new Database(getContext());


        recyclerView_food = (RecyclerView)view.findViewById(R.id.recyclerview_food);
        recyclerView_food.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView_food.setLayoutManager(layoutManager);

        checkOut = (FloatingActionButton)view.findViewById(R.id.FAB_CheckOut);


        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cart cart = new Cart();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.food_frament, cart);
                fragmentTransaction.commit();

            }
        }



        );


        adapter = new FirebaseRecyclerAdapter<Food_model, foodVIewHolder>(
                Food_model.class,
                R.layout.food_item,
                foodVIewHolder.class,
                food) {
            @Override
            protected void populateViewHolder(final foodVIewHolder viewHolder, final Food_model model, final int position) {

                viewHolder.text_header.setText(model.getName());

                Picasso.with(view.getContext()).load(model.getImage())
                        .into(viewHolder.image);


                if(localDB.isFavourite(adapter.getRef(position).getKey(),Common.current_user.getPhone()))

                    viewHolder.fav_image.setImageResource(R.drawable.ic_favorite_black);

                    //click to change the state of favourite
                    viewHolder.fav_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        if(!localDB.isFavourite(adapter.getRef(position).getKey(),Common.current_user.getPhone())){

                            localDB.addTofavourires(adapter.getRef(position).getKey(),Common.current_user.getPhone());
                            viewHolder.fav_image.setImageResource(R.drawable.ic_favorite_black);
                            TastyToast.makeText(getContext(),""+model.getName()+ " was added to Favourite",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);


                        }else{

                            localDB.removeFromfavourires(adapter.getRef(position).getKey());
                            viewHolder.fav_image.setImageResource(R.drawable.ic_favorite);
                            TastyToast.makeText(getContext(),""+model.getName() + " was removed from Favourite",TastyToast.LENGTH_SHORT,TastyToast.CONFUSING);


                        }

                        }
                    });



                final Food_model clickitem = model;


                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean islongclick) {

                        Intent foodDetails = new Intent(getActivity(), FoodDetails.class);
                        foodDetails.putExtra("FoodId",adapter.getRef(position).getKey());
                        startActivity(foodDetails);

                    }
                });
            }
        };

        recyclerView_food.setAdapter(adapter);


        return view;
    }

}
