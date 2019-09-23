package com.example.albertzkiki.movieapp.Remote;

import com.example.albertzkiki.movieapp.model.MyResponse;
import com.example.albertzkiki.movieapp.model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by albertzkiki on 5/3/2018.
 */

public interface APIService {

    @Headers({

            "Content-Type:application/json",
            "Authorization:key=AAAAhXG0DzE:APA91bE3iofR5CbRv6AhT1advUNwG0pe7GkVFeSu97RH44BDY5wp0a_p2QPUaGeFU4QBg4STfMuMt2olHaDkf0rUqakDB-zGexEdMEwXxytDX-q2l5DorpyYHpYXOLK78eSlUg334trH"
    })


    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
