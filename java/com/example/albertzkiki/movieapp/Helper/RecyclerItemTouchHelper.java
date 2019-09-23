package com.example.albertzkiki.movieapp.Helper;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.albertzkiki.movieapp.Adapter.CartViewHolder;
import com.example.albertzkiki.movieapp.Interface.RecyclerItemTouchHelperListerner;
import com.example.albertzkiki.movieapp.ViewHolder.FavMovieViewHolder;

/**
 * Created by albertzkiki on 5/6/2018.
 */

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback{

    private RecyclerItemTouchHelperListerner listerner;

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListerner listerner) {
        super(dragDirs, swipeDirs);
        this.listerner = listerner;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        if(listerner != null)
            listerner.onSwipe(viewHolder,direction,viewHolder.getAdapterPosition());

    }


    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        if(viewHolder instanceof CartViewHolder){
            View foregroundView = ((CartViewHolder) viewHolder).view_forwardCart;
            getDefaultUIUtil().clearView(foregroundView);
        }
        else if(viewHolder instanceof FavMovieViewHolder){
            View foregroundView = ((FavMovieViewHolder) viewHolder).view_forward;
            getDefaultUIUtil().clearView(foregroundView);
        }

    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if(viewHolder instanceof CartViewHolder) {
            View foregroundView = ((CartViewHolder) viewHolder).view_forwardCart;
            getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
        }
        else if(viewHolder instanceof FavMovieViewHolder){
            View foregroundView = ((FavMovieViewHolder) viewHolder).view_forward;
            getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
        }

    }


    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {


        if(viewHolder != null) {

            if(viewHolder instanceof CartViewHolder){
                View foregroundView = ((CartViewHolder) viewHolder).view_forwardCart;
                getDefaultUIUtil().onSelected(foregroundView);
            }
            else if(viewHolder instanceof FavMovieViewHolder){
                View foregroundView = ((FavMovieViewHolder) viewHolder).view_forward;
                getDefaultUIUtil().onSelected(foregroundView);
            }

        }

    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if(viewHolder instanceof CartViewHolder){
            View foregroundView = ((CartViewHolder)viewHolder).view_forwardCart;
            getDefaultUIUtil().onDrawOver(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
        }
        else if(viewHolder instanceof FavMovieViewHolder){
            View foregroundView = ((FavMovieViewHolder)viewHolder).view_forward;
            getDefaultUIUtil().onDrawOver(c,recyclerView,foregroundView,dX,dY,actionState,isCurrentlyActive);
        }


    }


}
