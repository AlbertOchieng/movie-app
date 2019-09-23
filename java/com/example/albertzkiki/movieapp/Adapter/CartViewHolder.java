package com.example.albertzkiki.movieapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.albertzkiki.movieapp.Common.Common;
import com.example.albertzkiki.movieapp.Interface.ItemClickListener;
import com.example.albertzkiki.movieapp.R;

/**
 * Created by albertzkiki on 5/6/2018.
 */

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{

    public TextView text_cartItem_name,text_cart_price;
  //  public ImageView cart_image_count;
    public ElegantNumberButton btn_quantity;

    public RelativeLayout view_background;
    public LinearLayout view_forwardCart;



    private ItemClickListener itemClickListener;

    public void setText_cartItem_name(TextView text_cartItem_name) {
        this.text_cartItem_name = text_cartItem_name;
    }

    public CartViewHolder(View itemView) {
        super(itemView);

        text_cartItem_name = (TextView)itemView.findViewById(R.id.cart_item_name);
        text_cart_price = (TextView)itemView.findViewById(R.id.cart_item_price);
        btn_quantity = (ElegantNumberButton)itemView.findViewById(R.id.btn_quantity);

        //cart_image_count = (ImageView)itemView.findViewById(R.id.cart_item_count);

        view_background = (RelativeLayout) itemView.findViewById(R.id.view_backgroundCart);
        view_forwardCart = (LinearLayout) itemView.findViewById(R.id.view_forwardCart);

        itemView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        menu.setHeaderTitle("Select Action");
        menu.add(0,0,getAdapterPosition(), Common.DELETE);

    }
}
