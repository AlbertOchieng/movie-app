package com.example.albertzkiki.movieapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.albertzkiki.movieapp.Adapter.MovieAdapter;
import com.example.albertzkiki.movieapp.Common.Common;
import com.example.albertzkiki.movieapp.api.Server;
import com.example.albertzkiki.movieapp.api.client;
import com.example.albertzkiki.movieapp.model.movie;
import com.example.albertzkiki.movieapp.model.movieResponse;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by albertzkiki on 3/23/2018.
 */

public class MainActivity1 extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<movie> movieList;
    ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;
    private static final String LOG_TAG = MovieAdapter.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        if(Common.IsConnectedToInternet(this)) {

            initView();

        }
        else {

            TastyToast.makeText(getActivity(),"Please Check your Internet Connection",TastyToast.LENGTH_SHORT,TastyToast.INFO).show();
            return;
        }

        swipeContainer = (SwipeRefreshLayout)findViewById(R.id.main_content);
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initView();
                TastyToast.makeText(getApplicationContext(), "movies have been refreshed",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS).show();
            }
        });

    }

    public Activity getActivity(){

        Context context = this;
        while (context instanceof ContextWrapper){
            if(context instanceof Activity){
                return(Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    public void initView(){

        pd = new ProgressDialog(this);
        pd.setMessage("Fetching Movies");
        pd.setCancelable(false);
        pd.show();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        movieList = new ArrayList<movie>();
        adapter = new MovieAdapter(this, movieList);

        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){

            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        }else{

            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        checkSortOrder();

    }

    public void loadJSON(){

        try {

            if(BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){

                Toast.makeText(getApplicationContext(), "please obtain API_KEY from themoviedb.org",Toast.LENGTH_SHORT).show();
                pd.dismiss();
                return;

            }

            client client = new client();
            Server apiServer = com.example.albertzkiki.movieapp.api.client.getClient().create(Server.class);
            Call<movieResponse> call = apiServer.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<movieResponse>() {
                @Override
                public void onResponse(Call<movieResponse> call, Response<movieResponse> response) {

                    List<movie>movies = response.body().getResults();
                    recyclerView.setAdapter(new MovieAdapter(getApplicationContext(), movies));
                    recyclerView.smoothScrollToPosition(0);

                    if(swipeContainer.isRefreshing()){

                        swipeContainer.setRefreshing(false);

                    }

                    pd.dismiss();

                }

                @Override
                public void onFailure(Call<movieResponse> call, Throwable t) {

                    Log.d("Error", t.getMessage());
                    Toast.makeText(MainActivity1.this, "Error fetching data, Make sure your Internet Connection is On", Toast.LENGTH_SHORT).show();

                }
            });

        }catch (Exception e){

            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

        }



    }

    public void loadJSON1(){

        try {

            if(BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){

                Toast.makeText(getApplicationContext(), "please obtain API_KEY from themoviedb.org",Toast.LENGTH_SHORT).show();
                pd.dismiss();
                return;

            }

            client client = new client();
            Server apiServer = com.example.albertzkiki.movieapp.api.client.getClient().create(Server.class);
            Call<movieResponse>call = apiServer.getTopRatedMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<movieResponse>() {
                @Override
                public void onResponse(Call<movieResponse> call, Response<movieResponse> response) {

                    List<movie>movies = response.body().getResults();
                    recyclerView.setAdapter(new MovieAdapter(getApplicationContext(), movies));
                    recyclerView.smoothScrollToPosition(0);

                    if(swipeContainer.isRefreshing()){

                        swipeContainer.setRefreshing(false);

                    }

                    pd.dismiss();

                }

                @Override
                public void onFailure(Call<movieResponse> call, Throwable t) {

//                    Log.d("Error", t.getMessage());
                    Toast.makeText(MainActivity1.this, "Error fetching data", Toast.LENGTH_SHORT).show();

                }
            });

        }catch (Exception e){

            Log.d("Error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

        }



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){

            case R.id.menu_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s){

        Log.d(LOG_TAG, "Preference updated");
        checkSortOrder();

    }

    private void checkSortOrder(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrder = preferences.getString(

                this.getString(R.string.pref_sort_order_key),
                this.getString(R.string.pref_most_popular)

        );

        if(sortOrder.equals(this.getString(R.string.pref_most_popular))){
            Log.d(LOG_TAG,"Sorting by most popular");
            loadJSON();
        }else{
            Log.d(LOG_TAG,"Sorting by vote average");
            loadJSON1();
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        if(movieList.isEmpty()){
            checkSortOrder();
        }else{

        }
    }

}
