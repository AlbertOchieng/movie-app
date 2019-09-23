package com.example.albertzkiki.movieapp;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.albertzkiki.movieapp.Database.Database;
import com.example.albertzkiki.movieapp.model.Category;
import com.example.albertzkiki.movieapp.model.Food_model;
import com.example.albertzkiki.movieapp.model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.util.Arrays;

/**
 * Created by albertzkiki on 4/4/2018.
 */

public class FoodDetails extends AppCompatActivity{

    TextView food_name, food_price, food_fanfact;
    ImageView food_img;
    ElegantNumberButton numberbutton;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton FABCart;


    String FoodId= "";

    FirebaseDatabase database;
    DatabaseReference foods;


    Food_model currentFood;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_details);


        food_name = (TextView)findViewById(R.id.nameofFood);
        food_price = (TextView)findViewById(R.id.priceofFood);
        food_fanfact = (TextView)findViewById(R.id.food_funFact);
        food_img = (ImageView) findViewById(R.id.imageofFood);

        numberbutton = (ElegantNumberButton)findViewById(R.id.number_button);
        FABCart = (FloatingActionButton)findViewById(R.id.btnfloatFD);


        collapsingToolbarLayout =(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar_FD);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapseAppBar);

        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Food");







        FABCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nub = numberbutton.getNumber();
                int a = Integer.parseInt(nub);

                if(a == 0){

                    TastyToast.makeText(getBaseContext(),"Choose the  number of" + currentFood.getName() +  "you want to buy" ,TastyToast.LENGTH_SHORT, TastyToast.INFO);

                }else {

                    new Database(getBaseContext()).addtoCart(new Order(
                            FoodId,
                            currentFood.getName(),
                            numberbutton.getNumber(),
                            currentFood.getPrice(),
                            currentFood.getDiscount()
                    ));

                    TastyToast.makeText(getBaseContext(),"Added to cart" ,TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);

                }



            }
        });




        if (getIntent() != null)

            FoodId = getIntent().getStringExtra("FoodId");

        if (!FoodId.isEmpty()) {

            getFoodDetails(FoodId);
        }

    }

    public void getFoodDetails(String FoodId) {


        foods.child(FoodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                currentFood = dataSnapshot.getValue(Food_model.class);

                Glide.with(getBaseContext()).load(currentFood.getImage())
                        .into(food_img);


                collapsingToolbarLayout.setTitle(currentFood.getName());

                food_name.setText(currentFood.getName());

                food_price.setText(currentFood.getPrice());

                food_fanfact.setText(currentFood.getFunfact());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


}
