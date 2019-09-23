package com.example.albertzkiki.movieapp;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.albertzkiki.movieapp.ViewHolder.faqViewHolder;
import com.example.albertzkiki.movieapp.model.FAQ_model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ramotion.foldingcell.FoldingCell;


public class FAQf extends Fragment {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference faqdb;

    public FoldingCell fc;

    FirebaseRecyclerAdapter<FAQ_model, faqViewHolder> adapter;

    public FAQf() {

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faqf, container, false);


        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_faq);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);




        database = FirebaseDatabase.getInstance();
        faqdb = database.getReference("FAQ");


        adapter = new FirebaseRecyclerAdapter<FAQ_model, faqViewHolder>(
                FAQ_model.class,
                R.layout.faq_layout,
                faqViewHolder.class,
                faqdb

        ) {
            @Override
            protected void populateViewHolder(faqViewHolder viewHolder, FAQ_model model, int position) {

                Typeface face = Typeface.createFromAsset(getActivity().getAssets(),"fonts/DJ2TRIAL.ttf");
                viewHolder.answer.setTypeface(face);
                viewHolder.question.setTypeface(face);

                viewHolder.question.setText(model.getQuestion());
                viewHolder.answer.setText(model.getAnswer());

            }
        };

        recyclerView.setAdapter(adapter);


        return view;
    }
}