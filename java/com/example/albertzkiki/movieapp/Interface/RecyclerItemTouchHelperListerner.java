package com.example.albertzkiki.movieapp.Interface;

import android.support.v7.widget.RecyclerView;

/**
 * Created by albertzkiki on 5/6/2018.
 */

public interface RecyclerItemTouchHelperListerner {

    void onSwipe(RecyclerView.ViewHolder viewHolder,int direction,int postion);
}
