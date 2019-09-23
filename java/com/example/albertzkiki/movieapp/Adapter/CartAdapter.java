package com.example.albertzkiki.movieapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.albertzkiki.movieapp.Cart;
import com.example.albertzkiki.movieapp.Common.Common;
import com.example.albertzkiki.movieapp.Database.Database;
import com.example.albertzkiki.movieapp.Interface.ItemClickListener;
import com.example.albertzkiki.movieapp.R;
import com.example.albertzkiki.movieapp.model.Order;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by albertzkiki on 4/5/2018.
 */




public class CartAdapter extends  RecyclerView.Adapter<CartViewHolder>{


    private List<Order> listData = new ArrayList<>();
    private Cart cart;

    public CartAdapter(List<Order> listData, Cart cart) {
        this.listData = listData;
        this.cart = cart;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(cart.getActivity());
        View  itemView = inflater.inflate(R.layout.cart_layout,parent,false);


        return new CartViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, final int position) {


//        TextDrawable drawable =  TextDrawable.builder()
//                .buildRound(""+listData.get(position).getQuantity(), Color.BLACK);
//        holder.cart_image_count.setImageDrawable(drawable);

        holder.btn_quantity.setNumber(listData.get(position).getQuantity());

        holder.btn_quantity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Order order = listData.get(position);
                order.setQuantity(String.valueOf(newValue));
                new Database(cart.getActivity()).UpdateCart(order);

                //update total
                int total = 0;
                List<Order>orders = new Database(cart.getActivity()).getCarts();
                for (Order item : orders) {
                    total += (Integer.parseInt(item.getPrice())) * (Integer.parseInt(item.getQuantity()));
                    Locale locale = new Locale("en", "US");
                    NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
                    cart.totalPrice.setText(fmt.format(total));

                }


            }
        });



        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price = (Integer.parseInt(listData.get(position).getPrice()))*(Integer.parseInt(listData.get(position).getQuantity()));

        holder.text_cart_price.setText(listData.get(position).getPrice());
        holder.text_cartItem_name.setText(listData.get(position).getProductName());


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public Order getItem(int position){

        return  listData.get(position);
    }

    public void removeItem(int position){

       listData.remove(position);
       notifyItemRemoved(position);

    }

    public void restore(Order item,int position){

        listData.add(position,item);
        notifyItemInserted(position);

    }



}
