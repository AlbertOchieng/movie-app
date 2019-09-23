package com.example.albertzkiki.movieapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.albertzkiki.movieapp.Interface.ItemClickListener;
import com.example.albertzkiki.movieapp.R;


/**
 * Created by albertzkiki on 4/10/2018.
 */

public class faqViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView question, answer;




    public faqViewHolder(View itemView) {
        super(itemView);

        question = (TextView)itemView.findViewById(R.id.question);
        answer = (TextView)itemView.findViewById(R.id.answer);




    }

    @Override
    public void onClick(View v) {


    }
}
