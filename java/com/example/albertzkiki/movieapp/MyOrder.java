package com.example.albertzkiki.movieapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.albertzkiki.movieapp.Common.Common;
import com.example.albertzkiki.movieapp.Interface.ItemClickListener;
import com.example.albertzkiki.movieapp.ViewHolder.orderViewHolder;
import com.example.albertzkiki.movieapp.model.Request;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsmdg.tastytoast.TastyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrder extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    FirebaseRecyclerAdapter<Request,orderViewHolder> adapter;


    public MyOrder() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Request");

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_foodlist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        loadOrders(Common.current_user.getPhone());

//        if(getActivity().getIntent() == null)
//
//           loadOrders(Common.current_user.getPhone());
//            TastyToast.makeText(getContext(), "Bla bla bla",TastyToast.LENGTH_SHORT,TastyToast.DEFAULT).show();
//
//        else
//            loadOrders(getActivity().getIntent().getStringExtra("UserPhone"));

        return view;


    }

    private void loadOrders(String phone) {

   //TastyToast.makeText(getContext(), "yah man",TastyToast.LENGTH_SHORT,TastyToast.DEFAULT).show();

        adapter = new FirebaseRecyclerAdapter<Request, orderViewHolder>
                (
                        Request.class,
                        R.layout.order_status,
                        orderViewHolder.class,
                        requests.orderByChild("phone").equalTo(phone)
                ) {
            @Override
            protected void populateViewHolder(orderViewHolder viewHolder, final Request model, int position) {

                viewHolder.textOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.textOrderStatus.setText(Common.convertCode(model.getStatus()));
                viewHolder.textOrderPhone.setText(model.getPhone());

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean islongclick) {

                        Intent orderDetails = new Intent(getActivity(), OrderDetails.class);
                        Common.currentRequest = model;
                        orderDetails.putExtra("OrderId",adapter.getRef(position).getKey());
                        startActivity(orderDetails);

                    }
                });



            }

        };

//        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }


}
