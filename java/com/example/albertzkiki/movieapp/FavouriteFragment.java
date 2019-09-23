package com.example.albertzkiki.movieapp;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.RelativeLayout;

import com.example.albertzkiki.movieapp.Adapter.CartViewHolder;
import com.example.albertzkiki.movieapp.Common.Common;
import com.example.albertzkiki.movieapp.Database.DatabaseMovie;
import com.example.albertzkiki.movieapp.Helper.RecyclerItemTouchHelper;
import com.example.albertzkiki.movieapp.Interface.RecyclerItemTouchHelperListerner;
import com.example.albertzkiki.movieapp.ViewHolder.FavMovieViewHolder;
import com.example.albertzkiki.movieapp.ViewHolder.FavouriteAdapter;
import com.example.albertzkiki.movieapp.model.FavouriteMovie;
import com.sdsmdg.tastytoast.TastyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment implements RecyclerItemTouchHelperListerner {

    RecyclerView recyclerViewFav;
    RecyclerView.LayoutManager layoutManager;

    FavouriteAdapter adapter;
    RelativeLayout rootLayout;

    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        rootLayout = (RelativeLayout)view.findViewById(R.id.root_layoutFav);

        recyclerViewFav = (RecyclerView)view.findViewById(R.id.recyclerFavourite);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewFav.setLayoutManager(layoutManager);

//        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(recyclerViewFav.getContext(),
//                R.anim.slide_in);
//        recyclerViewFav.setLayoutAnimation(controller);

        //swipe to delete

        ItemTouchHelper.SimpleCallback itemTouchHelper = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerViewFav);

        loadFavourites();


        return  view;
    }
    private void loadFavourites() {

        adapter = new FavouriteAdapter(getActivity(), new DatabaseMovie(getActivity()).getAllFavourites(Common.current_user.getPhone()));
        recyclerViewFav.setAdapter(adapter);
    }


    @Override
    public void onSwipe(RecyclerView.ViewHolder v, int direction, int postion) {


            String name = ((FavouriteAdapter) recyclerViewFav.getAdapter()).getItem(postion).getName();

            final FavouriteMovie deleteItem = ((FavouriteAdapter) recyclerViewFav.getAdapter()).getItem(v.getAdapterPosition());

            final int deleteIndex = v.getAdapterPosition();

            adapter.removeItem(v.getAdapterPosition());

            new DatabaseMovie(getActivity().getBaseContext()).removeFromfavourires(deleteItem.getId(), Common.current_user.getPhone());

            Snackbar snackbar = Snackbar.make(rootLayout, name + " has been removed from Favourites", Snackbar.LENGTH_LONG);


            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    adapter.restoreItem(deleteItem, deleteIndex);
                    new DatabaseMovie(getActivity().getBaseContext()).addTofavourires(deleteItem);
                    //TastyToast.makeText(getContext(),"item deleted!",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS).show();

                }
            });

            snackbar.setActionTextColor(Color.RED);
            snackbar.show();


    }

}

