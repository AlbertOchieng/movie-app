package com.example.albertzkiki.movieapp;


import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.albertzkiki.movieapp.Common.Common;
import com.example.albertzkiki.movieapp.Database.Database;
import com.example.albertzkiki.movieapp.Database.DatabaseMovie;
import com.example.albertzkiki.movieapp.Interface.ItemClickListener;
import com.example.albertzkiki.movieapp.ViewHolder.menuViewHolder;
import com.example.albertzkiki.movieapp.model.Category;
import com.example.albertzkiki.movieapp.model.FavouriteMovie;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFrament extends Fragment {

    String CategoryId="";
    Button btn;

    FirebaseDatabase database;
    DatabaseReference cartegory;

    DatabaseMovie localDB;

    RecyclerView recyclerView_menu;
    RecyclerView.LayoutManager layoutManager;

    SwipeRefreshLayout swipeRefreshLayout;


    FirebaseRecyclerAdapter<Category, menuViewHolder> adapter;

    //search functionality
    FirebaseRecyclerAdapter<Category, menuViewHolder> searchAdapter;
    MaterialSearchBar materialSearchBar;
    List<String> suggestList = new ArrayList<>();

    public HomeFrament() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        database = FirebaseDatabase.getInstance();
        cartegory = database.getReference("Cartegory");

        final View view = inflater.inflate(R.layout.fragment_home_frament, container, false);

        recyclerView_menu = (RecyclerView)view.findViewById(R.id.recyclerview_menu);
        recyclerView_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView_menu.setLayoutManager(layoutManager);

        localDB = new DatabaseMovie(getContext());

        //search inti
        materialSearchBar = (MaterialSearchBar)view.findViewById(R.id.searchBar);

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_layoutx);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(Common.IsConnectedToInternet(getContext())) {

                    //function to get suggestion list from firebase

                    loadSuggest();
                    materialSearchBar.setCardViewElevation(10);
                    materialSearchBar.setLastSuggestions(suggestList);
                    materialSearchBar.addTextChangeListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                            List<String> suggest = new ArrayList<>();

                            for(String search:suggestList){

                                if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase())){

                                    suggest.add(search);

                                }

                                materialSearchBar.setLastSuggestions(suggest);

                            }

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                    materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
                        @Override
                        public void onSearchStateChanged(boolean enabled) {

                            //when search is closed restore original list

                            if(!enabled){
                                recyclerView_menu.setAdapter(adapter);
                            }

                        }

                        @Override
                        public void onSearchConfirmed(CharSequence text) {

                            //when seach is finished ....show results of searchadapter
                            startSearch(text);

                        }

                        @Override
                        public void onButtonClicked(int buttonCode) {

                        }
                    });

                    adapter = new FirebaseRecyclerAdapter<Category, menuViewHolder>(
                            Category.class,
                            R.layout.movie_item,
                            menuViewHolder.class,
                            cartegory) {
                        @Override
                        protected void populateViewHolder(final menuViewHolder viewHolder, final Category model, final int position) {

                            viewHolder.text_header.setText(model.getName());

                            Picasso.with(view.getContext()).load(model.getImage())
                                    .into(viewHolder.image);



                            //click to change the state of favourite

                            viewHolder.fav_image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    FavouriteMovie favouriteMovie = new FavouriteMovie();
                                    favouriteMovie.setId(adapter.getRef(position).getKey());
                                    favouriteMovie.setName(model.getName());
                                    favouriteMovie.setImage(model.getImage());
                                    favouriteMovie.setPlotsynopsis(model.getPlotsynopsis());
                                    favouriteMovie.setPrice(model.getPrice());
                                    favouriteMovie.setUserPhone(Common.current_user.getPhone());

                                    if(!localDB.isFavourite(adapter.getRef(position).getKey(),Common.current_user.getPhone())){

                                        localDB.addTofavourires(favouriteMovie);
                                        viewHolder.fav_image.setImageResource(R.drawable.ic_favorite_black);
                                        TastyToast.makeText(getContext(),""+model.getName()+ " was added to Favourite",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);


                                    }else{

                                        //localDB.removeFromfavourires(favouriteMovie);
                                        localDB.removeFromfavourires(adapter.getRef(position).getKey(),Common.current_user.getPhone());
                                        viewHolder.fav_image.setImageResource(R.drawable.ic_favorite);
                                        TastyToast.makeText(getContext(),""+model.getName() + " was removed from Favourite",TastyToast.LENGTH_SHORT,TastyToast.CONFUSING);

                                    }
                                }
                            });

                            final Category clickitem = model;

                            viewHolder.setItemClickListener(new ItemClickListener() {
                                @Override
                                public void onClick(View view, int position, boolean islongclick) {

                                    Intent moviedetails = new Intent(getActivity(), Movie_Details.class);
                                    moviedetails.putExtra("CategoryId", adapter.getRef(position).getKey());
                                    moviedetails.putExtra("MovieName", adapter.getItem(position).getName());

                                    startActivity(moviedetails);

                                }
                            });
                        }
                    };

                    recyclerView_menu.setAdapter(adapter);

                }else{

                    TastyToast.makeText(getContext(),"Please Check your Internet Connection",TastyToast.LENGTH_SHORT,TastyToast.INFO).show();
                }



                TastyToast.makeText(getContext(), "movies have been refreshed",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS).show();
            }
        });




        if(Common.IsConnectedToInternet(getContext())) {

            //function to get suggestion list from firebase

            loadSuggest();
            materialSearchBar.setCardViewElevation(10);
            materialSearchBar.setLastSuggestions(suggestList);
            materialSearchBar.addTextChangeListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    List<String> suggest = new ArrayList<>();

                    for(String search:suggestList){

                        if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase())){

                            suggest.add(search);

                        }

                        materialSearchBar.setLastSuggestions(suggest);

                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
                @Override
                public void onSearchStateChanged(boolean enabled) {

                    //when search is closed restore original list

                    if(!enabled){
                        recyclerView_menu.setAdapter(adapter);
                    }

                }

                @Override
                public void onSearchConfirmed(CharSequence text) {

                    //when seach is finished ....show results of searchadapter
                    startSearch(text);

                }

                @Override
                public void onButtonClicked(int buttonCode) {

                }
            });

            adapter = new FirebaseRecyclerAdapter<Category, menuViewHolder>(
                    Category.class,
                    R.layout.movie_item,
                    menuViewHolder.class,
                    cartegory) {
                @Override
                protected void populateViewHolder(final menuViewHolder viewHolder, final Category model, final int position) {

                    viewHolder.text_header.setText(model.getName());

                    Picasso.with(view.getContext()).load(model.getImage())
                            .into(viewHolder.image);


                    if(localDB.isFavourite(adapter.getRef(position).getKey(),Common.current_user.getPhone()))

                        viewHolder.fav_image.setImageResource(R.drawable.ic_favorite_black);

                        //click to change the state of favourite
                         viewHolder.fav_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            final FavouriteMovie favouriteMovie = new FavouriteMovie();
                            favouriteMovie.setId(adapter.getRef(position).getKey());
                            favouriteMovie.setName(model.getName());
                            favouriteMovie.setImage(model.getImage());
                            favouriteMovie.setPlotsynopsis(model.getPlotsynopsis());
                            favouriteMovie.setPrice(model.getPrice());
                            favouriteMovie.setUserPhone(Common.current_user.getPhone());



                            if(!localDB.isFavourite(adapter.getRef(position).getKey(),Common.current_user.getPhone())){

                                localDB.addTofavourires(favouriteMovie);
                               // localDB.addTofavourires(adapter.getRef(position).getKey(),Common.current_user.getPhone());
                                viewHolder.fav_image.setImageResource(R.drawable.ic_favorite_black);
                                TastyToast.makeText(getContext(),""+model.getName()+ " was added to Favourite",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);


                            }else{

                                //localDB.removeFromfavourires(favouriteMovie);
                                localDB.removeFromfavourires(adapter.getRef(position).getKey(),Common.current_user.getPhone());
                                viewHolder.fav_image.setImageResource(R.drawable.ic_favorite);
                                TastyToast.makeText(getContext(),""+model.getName() + " was removed from Favourite",TastyToast.LENGTH_SHORT,TastyToast.CONFUSING);


                            }

                        }
                    });


                    final Category clickitem = model;

                    viewHolder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean islongclick) {

                            Intent moviedetails = new Intent(getActivity(), Movie_Details.class);
                            moviedetails.putExtra("CategoryId", adapter.getRef(position).getKey());
                            moviedetails.putExtra("MovieName", adapter.getItem(position).getName());
                            moviedetails.putExtra("MoviePrice", adapter.getItem(position).getPrice());

                            startActivity(moviedetails);

                        }
                    });
                }
            };

            recyclerView_menu.setAdapter(adapter);

        }else{

            TastyToast.makeText(getContext(),"Please Check your Internet Connection",TastyToast.LENGTH_SHORT,TastyToast.INFO).show();
        }


        return  view;

    }

    private void startSearch(CharSequence text) {

        searchAdapter = new FirebaseRecyclerAdapter<Category, menuViewHolder>(
                Category.class,
                R.layout.movie_item,
                menuViewHolder.class,
                cartegory.orderByChild("name").equalTo(text.toString()) //compare list form firebase and the adapter
        ){

            @Override
            protected void populateViewHolder(menuViewHolder viewHolder, Category model, int position) {



                viewHolder.text_header.setText(model.getName());

                Picasso.with(getContext()).load(model.getImage())
                        .into(viewHolder.image);
                final Category clickitem = model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean islongclick) {

                        Intent moviedetails = new Intent(getActivity(), Movie_Details.class);
                        moviedetails.putExtra("CategoryId", searchAdapter.getRef(position).getKey());
                        moviedetails.putExtra("MovieName", searchAdapter.getItem(position).getName());

                        startActivity(moviedetails);

                    }
                });

            }
        };

        recyclerView_menu.setAdapter(searchAdapter);
    }

    private void loadSuggest(){

        cartegory.orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Category item = postSnapshot.getValue(Category.class);
                    suggestList.add(item.getName());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


}
