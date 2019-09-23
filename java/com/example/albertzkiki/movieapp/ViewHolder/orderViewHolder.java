package com.example.albertzkiki.movieapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.albertzkiki.movieapp.Interface.ItemClickListener;
import com.example.albertzkiki.movieapp.R;

/**
 * Created by albertzkiki on 4/8/2018.
 */

public class orderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView textOrderId, textOrderPhone, textOrderStatus;

        private ItemClickListener itemClickListener;

    public orderViewHolder(View itemView) {
        super(itemView);

        textOrderId = (TextView)itemView.findViewById(R.id.order_id);
        textOrderPhone = (TextView)itemView.findViewById(R.id.order_phone);
        textOrderStatus = (TextView)itemView.findViewById(R.id.order_status);

        itemView.setOnClickListener(this);


    }

        public void setItemClickListener(ItemClickListener itemClickListener) {

            this.itemClickListener = itemClickListener;

            }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}
