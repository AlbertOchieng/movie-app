package com.example.albertzkiki.movieapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.albertzkiki.movieapp.Common.Common;
import com.example.albertzkiki.movieapp.ViewHolder.FavMovieViewHolder;
import com.example.albertzkiki.movieapp.ViewHolder.menuViewHolder;
import com.example.albertzkiki.movieapp.model.Category;
import com.example.albertzkiki.movieapp.model.Rating;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.util.Arrays;

import mehdi.sakout.fancybuttons.FancyButton;

public class Movie_Details extends AppCompatActivity implements RatingDialogListener {


    TextView movie_name, movie_price, movie_description;
    ImageView movie_img;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FancyButton btnBooking,confirm;

    MaterialSpinner spinnerTime,spinnerDate,spinnerIsAdult;
    MaterialEditText amount;


    String CategoryId = "";
    String MoviePrice="";
    String MovieName="";

    FirebaseDatabase database;
    DatabaseReference movies;
    DatabaseReference ratingTable;

    FirebaseRecyclerAdapter<Category, menuViewHolder> adapter;


    FloatingActionButton fab,FABRating;
    RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie__details);

        //initialize firebase
        database = FirebaseDatabase.getInstance();
        movies = database.getReference("Cartegory");
        ratingTable = database.getReference("Rating");

        //initialize all the resources

        movie_name = (TextView) findViewById(R.id.movie_nameCV);
        movie_price = (TextView) findViewById(R.id.movie_priceMD);
        movie_description = (TextView) findViewById(R.id.plotsynopsis);
        movie_img = (ImageView) findViewById(R.id.movie_imgMD);
//        fab = (FloatingActionButton) findViewById(R.id.btnfloat);
        FABRating = (FloatingActionButton)findViewById(R.id.btnRating);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);

        btnBooking = (FancyButton)findViewById(R.id.btnbook);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_MD);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapseAppBar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);


        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBookingDialog();

            }
        });


        FABRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog();
            }
        });

        if (getIntent() != null)

            CategoryId = getIntent().getStringExtra("CategoryId");
            MovieName = getIntent().getStringExtra("MovieName");
            MoviePrice = getIntent().getStringExtra("MoviePrice");

            if (!CategoryId.isEmpty()) {

                if(Common.IsConnectedToInternet(getBaseContext())){

                    getDetailMovie(CategoryId);
                    getRatingMovie(CategoryId,MovieName);

                }else{

                    TastyToast.makeText(Movie_Details.this,"Please check your internet Connection",TastyToast.LENGTH_SHORT,TastyToast.INFO).show();
                }
            }
    }


    public void getDetailMovie(String CategoryId) {

        movies.child(CategoryId).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot){

                Category category = dataSnapshot.getValue(Category.class);

                //set Image
                Picasso.with(getBaseContext()).load(category.getImage())
                        .into(movie_img);

                collapsingToolbarLayout.setTitle(category.getName());

                movie_name.setText(category.getName());

                movie_price.setText(category.getPrice());

                movie_description.setText(category.getPlotsynopsis());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    public void getRatingMovie(String CategoryId, String MovieName){


        Query movieRating = ratingTable.orderByChild("CategoryId").equalTo(CategoryId);

        movieRating.addValueEventListener(new ValueEventListener() {

            int count = 0; int sum =0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Rating item = postSnapshot.getValue(Rating.class);
                    sum+=Integer.parseInt(item.getRating());
                    count++;

                    if(count != 0){

                        float average = sum/count;
                        ratingBar.setRating(average);
                        finish();
                    }
                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private  void showRatingDialog(){

        new AppRatingDialog.Builder()
                .setNegativeButtonText("Cancel")
                .setPositiveButtonText("Submit")
                .setNoteDescriptions(Arrays.asList("Very Bad","Not Good","Quite Ok","Good","Excellent"))
                .setDefaultRating(1)
                .setTitle("Rate this Movie")
                .setDescription("Please select some stars and rate this movie")
                .setHint("please write some comments in here....")
                .setHintTextColor(R.color.colorAccent)
                .setCommentTextColor(android.R.color.white)
                .setCommentBackgroundColor(R.color.colorPrimaryDark)
                .setWindowAnimation(R.style.RatingDialogFadeAnim)
                .create(Movie_Details.this).show();





    }

    @Override
    public void onPositiveButtonClicked(int value, String comments) {

//        MovieName = Category.class.getName();

        final Rating rating = new Rating(
                Common.current_user.getPhone(),
                CategoryId,
                String.valueOf(value),
                comments,
                MovieName

        );

        ratingTable.child(Common.current_user.getPhone()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(Common.current_user.getPhone()).exists()) {
                    //remove old value
                    ratingTable.child(Common.current_user.getPhone()).removeValue();
                    //update new value
                    ratingTable.child(Common.current_user.getPhone()).setValue(rating);

                }else{


                    //update new value
                    ratingTable.child(Common.current_user.getPhone()).setValue(rating);

                }

                TastyToast.makeText(Movie_Details.this, "Thank you for submitting your Rating",TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }

    private void showBookingDialog() {

        final android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(Movie_Details.this);
        alert.setTitle("Booking");
        alert.setMessage("Please fill in all the information");


        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.booking_details, null);

        spinnerDate = (MaterialSpinner)view.findViewById(R.id.spinnerDate);
        spinnerTime = (MaterialSpinner)view.findViewById(R.id.spinnerTime);
        spinnerIsAdult = (MaterialSpinner)view.findViewById(R.id.spinnerIsAdult);


        spinnerDate.setItems("--Select Date--","6th-05-2018","7th-05-2018","8th-05-2018");
        spinnerTime.setItems("--Select Time--","0900-1130","1400-1630","2000-2230");
        spinnerIsAdult.setItems("--Choose one--","Adult","Child");


        alert.setIcon(R.drawable.ic_movie_creator);
        alert.setView(view);


        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                if(spinnerDate.getSelectedIndex() == 0){
                    TastyToast.makeText(getApplicationContext(),"You have not selected the date",TastyToast.LENGTH_SHORT,TastyToast.ERROR).show();
                    if(spinnerTime.getSelectedIndex() == 0){
                        TastyToast.makeText(getApplicationContext(),"You have not selected the Time",TastyToast.LENGTH_SHORT,TastyToast.ERROR).show();
                        if(spinnerIsAdult.getSelectedIndex() == 0){
                            TastyToast.makeText(getApplicationContext(),"Please indicate if it adult or child",TastyToast.LENGTH_SHORT,TastyToast.ERROR).show();

                        }

                    }

                }

                else{
                    Category category = new Category();
                    Intent seats = new Intent(getBaseContext(), Seat.class);
                    seats.putExtra("MovieName", MovieName);
                    seats.putExtra("MoviePrice", MoviePrice);
                    seats.putExtra("Spinner_Date", spinnerDate.getText().toString());
                    seats.putExtra("Spinner_Time", spinnerTime.getText().toString());
                    seats.putExtra("Spinner_Age", spinnerIsAdult.getText().toString());
                    startActivity(seats);
                    dialog.dismiss();
                }
            }
        });

        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();

    }




}

