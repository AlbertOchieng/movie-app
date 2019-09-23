package com.example.albertzkiki.movieapp.Service;

import com.example.albertzkiki.movieapp.Common.Common;
import com.example.albertzkiki.movieapp.model.Token;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by albertzkiki on 5/2/2018.
 */

public class MyFirebaseIdService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String tokenRefreshed = FirebaseInstanceId.getInstance().getToken();

        if(Common.current_user != null){
            UpdateTokenToFirebase(tokenRefreshed);
        }

    }

    private void UpdateTokenToFirebase(String tokenRefreshed) {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference tokens = db.getReference("Tokens");

        Token token = new Token(tokenRefreshed,false);//false because this token is sent from client side
        tokens.child(Common.current_user.getPhone()).setValue(token);




    }


}
