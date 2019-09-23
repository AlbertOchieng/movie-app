package com.example.albertzkiki.movieapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.albertzkiki.movieapp.R;
import com.example.albertzkiki.movieapp.model.Order;

import java.util.List;

/**
 * Created by albertzkiki on 5/1/2018.
 */

class MyViewHolder extends RecyclerView.ViewHolder{

    public TextView pname,pquantity,pprice,pdiscount;

    public MyViewHolder(View itemView) {
        super(itemView);

        pname = (TextView)itemView.findViewById(R.id.Product_name);
        pquantity = (TextView)itemView.findViewById(R.id.product_quantity);
        pprice = (TextView)itemView.findViewById(R.id.product_price);
        pdiscount = (TextView)itemView.findViewById(R.id.product_dicount);

    }
}


public class OrderDetailAdapter extends  RecyclerView.Adapter<MyViewHolder>{

    List<Order> myOrders;

    public OrderDetailAdapter(List<Order> myOrders) {
        this.myOrders = myOrders;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_details_layout,  parent,false);




        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Order order = myOrders.get(position);
        holder.pname.setText(String.format("Name : %s",order.getProductName()));
        holder.pquantity.setText(String.format("Quantity : %s",order.getQuantity()));
        holder.pprice.setText(String.format("Price : %s",order.getPrice()));
        holder.pdiscount.setText(String.format("Discount : %s",order.getDiscount()));

    }

    @Override
    public int getItemCount() {
        return myOrders.size();
    }
}

