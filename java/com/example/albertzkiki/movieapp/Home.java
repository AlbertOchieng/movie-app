package com.example.albertzkiki.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albertzkiki.movieapp.Common.Common;
import com.example.albertzkiki.movieapp.Interface.ItemClickListener;
import com.example.albertzkiki.movieapp.Service.ListenOrder;
import com.example.albertzkiki.movieapp.ViewHolder.menuViewHolder;
import com.example.albertzkiki.movieapp.model.Category;
import com.example.albertzkiki.movieapp.model.Token;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference cartegory;

    RecyclerView recyclerView_menu;
    RecyclerView.LayoutManager layoutManager;

    SwipeRefreshLayout swipeRefreshLayout;

    TextView textFullName;

    FirebaseRecyclerAdapter<Category, menuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        HomeFrament homeFrament = new HomeFrament();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_main, homeFrament);
        fragmentTransaction.commit();



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        //initialize firebase

        database = FirebaseDatabase.getInstance();
        cartegory = database.getReference("Cartegory");


        Paper.init(getApplicationContext());


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


//        set name for current user

        View headerview = navigationView.getHeaderView(0);
        textFullName = (TextView)headerview.findViewById(R.id.tv_fullname);
        textFullName.setText(Common.current_user.getName());


        //register service
//
//        Intent service = new Intent(Home.this, ListenOrder.class);
//        startService(service);


        updateToken(FirebaseInstanceId.getInstance().getToken());

    }

    private void updateToken(String token) {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference tokens = db.getReference("Tokens");

        Token data = new Token(token,false);//false because this token is sent from client side
        tokens.child(Common.current_user.getPhone()).setValue(data);


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.refresh){

            HomeFrament homeFrament = new HomeFrament();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_main, homeFrament);
            fragmentTransaction.commit();

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {

            HomeFrament homeFrament = new HomeFrament();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_main, homeFrament);
            fragmentTransaction.commit();

        } else if (id == R.id.profile) {


            Profile1 pr = new Profile1();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_main, pr);
            fragmentTransaction.commit();


        }else if (id == R.id.movieHub) {


            MovieHub hb = new MovieHub();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_main, hb);
            fragmentTransaction.commit();


        }


        else if (id == R.id.food) {

            Food food = new Food();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_main, food);
            fragmentTransaction.commit();


        }  else if (id == R.id.Status_Order) {

            Cart cart = new Cart();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_main, cart);
            fragmentTransaction.commit();


        }else if (id == R.id.my_order) {

            MyOrder myOrder = new MyOrder();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_main, myOrder);
            fragmentTransaction.commit();


        } else if (id == R.id.favorite) {

//            Intent fav = new Intent(Home.this,Favourites.class);
//            startActivity(fav);
            FavouriteFragment fav = new FavouriteFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_main, fav);
            fragmentTransaction.commit();


        }
        else if (id == R.id.FAQ) {

            FAQf faq = new FAQf();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_main, faq);
            fragmentTransaction.commit();



        }

        else if (id == R.id.log_out) {

            //delete remember user and password

            Paper.book().destroy();


            //log out
            Intent SignIn = new Intent(Home.this,Signin.class);
            SignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(SignIn);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
