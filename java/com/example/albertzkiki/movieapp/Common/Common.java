package com.example.albertzkiki.movieapp.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import com.example.albertzkiki.movieapp.Remote.APIService;
import com.example.albertzkiki.movieapp.Remote.RetrofitClient;
import com.example.albertzkiki.movieapp.model.Category;
import com.example.albertzkiki.movieapp.model.Request;
import com.example.albertzkiki.movieapp.model.Upload;
import com.example.albertzkiki.movieapp.model.User;

/**
 * Created by albertzkiki on 3/24/2018.
 */

public class Common {

    public static User current_user;

    public static final String BASE_URL="https://fcm.googleapis.com";

    public static APIService getFCMSevice(){
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

    public static Request currentRequest;

    public static Category currenMovie;

    public static Upload current_profile;

    public static final String DELETE = "Delete";

    public static final String USER_KEY = "User";

    public static final String PWD_KEY = "Passord";

    public static final String convertCode(String status) {

        if (status.equals("0")) {
            return "PLACED";
        } else {
            return "READY";
        }

    }

    public static boolean IsConnectedToInternet(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager != null){

            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();

            if(info != null){

                for(int i=0; i<info.length;i++){

                    if(info[i].getState() == NetworkInfo.State.CONNECTED){

                        return  true;

                    }


                }

            }


        }




        return false;
    }

}
